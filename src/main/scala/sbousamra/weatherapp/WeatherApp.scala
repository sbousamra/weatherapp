package sbousamra.weatherapp

import Types._
import argonaut._
import org.http4s.HttpService
import org.http4s.client.blaze.PooledHttp1Client
import org.http4s.dsl._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder

import scalaz.concurrent.Task

object WeatherApp extends ServerApp {

  def getWeatherApi(request: WeatherForecastRequest): Task[String] = {
    val httpClient = PooledHttp1Client()
    val weatherRequest = httpClient.expect[String]("https://httpbin.org/")
    weatherRequest
  }

  def getRoute: HttpService = {
    HttpService {
      case GET -> Root / location => {
        val weatherApiTask = getWeatherApi(WeatherForecastRequest(1, "test")).attempt.run
        val weatherApiTest = weatherApiTask.toString
        Ok(weatherApiTest)
      }
    }
  }

  override def server(args: List[String]): Task[Server] =
    BlazeBuilder
      .mountService(getRoute, "/")
      .start
}

//Pseudocode
//
//1. Create method that issues a GET request to a server containing the weather information we want back as JSON.
//      Will look something like:
//        request: (City, Days) => serverRequestOutput: Response[JSON]
//
//2. Now create a method that will take this JSON and filter the data we want Format this for user.
//      Will look something like:
//        serverRequestOutput: Response[JSON] => ourResponse: WeatherForecastResponse
//
//def getDataFromSource(request: Task[Response]): Response[JSON] = {
//  ???
//}
//
//def ourResponse(sourceData: Response[JSON]): WeatherForecastResponse = {
//  ???
//}
