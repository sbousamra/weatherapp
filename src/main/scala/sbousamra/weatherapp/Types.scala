package sbousamra.weatherapp

import argonaut._, Argonaut._

object Types {

  case class WeatherForecastRequest(days: Int, location: String)

  case class WeatherForecastResponse(
    location: String,
    time: Int,
    temperature: Int,
    precipitation: Int,
    humidity: Int,
    wind: Int,
    forecast: String
  )

  def encodeJson(response: WeatherForecastResponse): Json = {
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
}
