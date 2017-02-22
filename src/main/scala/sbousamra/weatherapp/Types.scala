package sbousamra.weatherapp

import argonaut._
import Argonaut._
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder

import scalaz.concurrent.Task

object Types extends ServerApp {

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

  def getUrl(location: String, days: Int): String = {
    "weather/" + location + "/" + days.toString
  }

  def getRoute(url: String): HttpService = {
    HttpService {
      case GET -> Root / url =>
        Ok("55 degrees")
    }
  }
  override def server(args: List[String]): Task[Server] =
    BlazeBuilder
    .mountService(getRoute(getUrl("africa", 5)), "/")
    .start
}
