package sbousamra.weatherapp

import Types._
import argonaut._
import argonaut.Json
import org.http4s.{HttpService, Uri}
import org.http4s.client.blaze.PooledHttp1Client
import org.http4s.dsl._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.argonaut._
import org.http4s.client.Client

import scalaz.concurrent.Task

object WeatherApp extends ServerApp {

  def getWeatherApi(request: WeatherForecastRequest, httpClient: Client): Task[Json] = {
    val uri = Uri
      .unsafeFromString("https://query.yahooapis.com/v1/public/yql")
      .withQueryParam("q", "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=" + """"""" + request.location + """"""" + ")")
      .withQueryParam("format", "json")
    println(uri)
    val weatherRequest = httpClient.expect[Json](uri)
    weatherRequest
  }

  def getRoute: HttpService = {
    HttpService {
      case GET -> Root / location => {
        val weatherApiTask = getWeatherApi(WeatherForecastRequest(1, "gold coast"), PooledHttp1Client()).attempt.run
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
