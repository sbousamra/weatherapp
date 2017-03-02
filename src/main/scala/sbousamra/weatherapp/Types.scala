package sbousamra.weatherapp

import argonaut._
import Argonaut._
import org.http4s.EntityDecoder
import org.http4s.argonaut._

import scalaz.concurrent.Task

object Types {

  case class WeatherForecastRequest(location: String)

  case class Weather(
    location: Location,
    atmosphere: Atmosphere,
    astronomy: Astronomy,
    wind: Wind,
    forecast: List[Forecast]
  )

  case class Location(city: String, country: String, region: String)
  case class Atmosphere(humidity: String, pressure: String, visibility: String)
  case class Astronomy(sunrise: String, sunset: String)
  case class Wind(chill: String, direction: String, speed: String)
  case class Forecast(typeOfWeather: String, temperatureLow: String, temperatureHigh: String, day: String, date: String)

  implicit def LocationCodecJson: CodecJson[Location] =
    casecodec3(Location.apply, Location.unapply)("city", "country", "region")

  implicit def AtmosphereCodecJson: CodecJson[Atmosphere] =
    casecodec3(Atmosphere.apply, Atmosphere.unapply)("humidity", "pressure", "visibility")

  implicit def AstronomyCodecJson: CodecJson[Astronomy] =
    casecodec2(Astronomy.apply, Astronomy.unapply)("sunrise", "sunset")

  implicit def WindCodecJson: CodecJson[Wind] =
    casecodec3(Wind.apply, Wind.unapply)("chill", "direction", "speed")

  implicit def ForecastCodecJson: CodecJson[Forecast] =
    casecodec5(Forecast.apply, Forecast.unapply)("text", "low", "high", "day", "date")

  implicit def WeatherEntityDecoder: EntityDecoder[Weather] = {
    jsonOf(decodeApiWeatherResponse)
  }

  implicit def decodeApiWeatherResponse: DecodeJson[Weather] = {
    DecodeJson {
      h  => {
        val channel = (h --\ "query" --\ "results" --\ "channel")
        for {
          location <- channel.get[Location]("location")
          atmosphere <- channel.get[Atmosphere]("atmosphere")
          astronomy <- channel.get[Astronomy]("astronomy")
          wind <- channel.get[Wind]("wind")
          forecast <- (channel --\ "item").get[List[Forecast]]("forecast")
        } yield Weather(location, atmosphere, astronomy, wind, forecast)
      }
    }
  }

  def encodeWeatherJson(response: Weather): Json = {
    Json(
      "location" := response.location,
      "atmosphere" := response.atmosphere,
      "astronomy" := response.astronomy,
      "wind" := response.wind,
      "forecast" := response.forecast
    )
  }
}
