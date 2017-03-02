package sbousamra.weatherapp

import argonaut._
import Argonaut._


import scalaz.concurrent.Task

object Types {

  case class WeatherForecastRequest(location: String)

  case class Weather(
    location: List[String],
    atmosphere: List[String],
    astronomy: List[String],
    wind: List[String],
    forecast: List[String]
  )

  def decodeApiWeatherResponse: DecodeJson[Weather] = {
    DecodeJson {
      h  => for {
        location <- h.get[List[String]]("results")
        atmosphere <- h.get[List[String]]("atmosphere")
        astronomy <- h.get[List[String]]("astronomy")
        wind <- h.get[List[String]]("wind")
        forecast <- h.get[List[String]]("forecast")
      } yield Weather(location, atmosphere, astronomy, wind, forecast)
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
