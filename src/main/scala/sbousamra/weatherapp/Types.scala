package sbousamra.weatherapp

import argonaut._
import Argonaut._


import scalaz.concurrent.Task

object Types {

  case class WeatherForecastRequest(days: Int, location: String)

  case class Weather(
    location: String,
    time: Int,
    temperature: Int,
    precipitation: Int,
    humidity: Int,
    wind: Int,
    forecast: String
  )

  case class WeatherForecast(day: String, forecast: Weather)

  case class WeatherForecastResponse(days: List[WeatherForecast])

  def encodeWeatherJson(response: Weather): Json = {
    Json(
      "location" := response.location,
      "time" := response.time,
      "temperature" := response.temperature,
      "precipitation" := response.precipitation,
      "humidity" := response.humidity,
      "wind" := response.wind,
      "forecast" := response.forecast
    )
  }

//  def getUrl(location: String, days: Int): String = {
//    "weather/" + location + "/" + days.toString
//  }

  def getWeatherApi(request: WeatherForecastRequest): WeatherForecastResponse = {
    val dummyWeather = Weather(request.location, 2400, 35, 10, 20, 30, "sunny")
    val dummyWeatherForecast = WeatherForecast("monday", dummyWeather)
    WeatherForecastResponse(List.fill(request.days)(dummyWeatherForecast))
  }


  def encodeWeatherForecastResponseJson(responseFromApi: WeatherForecastResponse): Json = {
    Json(
      "days" := responseFromApi.days.map { day =>
        Json(
          "day" := day.day,
          "forecast" := encodeWeatherJson(day.forecast)
        )
      }
    )
  }
}
