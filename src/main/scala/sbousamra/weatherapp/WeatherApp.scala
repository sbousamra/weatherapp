package sbousamra.weatherapp

import java.io.File

import Types._
import argonaut._
import Argonaut._
import org.http4s._
import org.http4s.client.blaze.PooledHttp1Client
import org.http4s.dsl._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.argonaut._
import org.http4s.client.Client

import scala.io.Source
import scalaz.{-\/, \/-}
import scalaz.concurrent.Task



object WeatherApp extends ServerApp {
  val httpClient = PooledHttp1Client()

  def getWeatherApi(request: WeatherForecastRequest, client: Client): Task[Weather] = {
    val uri = Uri
      .unsafeFromString("https://query.yahooapis.com/v1/public/yql")
      .withQueryParam("q", s"""select * from weather.forecast where woeid in (select woeid from geo.places(1) where text= "${request.location}")""")
      .withQueryParam("format", "json")
    httpClient.expect[Weather](uri)
  }

  val weatherService: HttpService = {
    HttpService {
      case GET -> Root / "weather" / location => {
        getWeatherApi(WeatherForecastRequest(s"$location"), httpClient).attempt.flatMap {
          case \/-(weather) =>
            val encodedJsonForUser = encodeWeatherJson(weather)
            Ok(encodedJsonForUser)
          case -\/(err) => InternalServerError(err.toString)
        }
      }

      case GET -> Root => {
        val file = new File("src/main/resources/WeatherAppHtml.html")
        val html = Source.fromFile(file).mkString
        Ok(html).map (response => response.putHeaders(Header("Content-Type", "text/html; charset=UTF-8")))
      }
    }
  }

  def getPort: Int = {
    Option(System.getenv("PORT")).map(v => v.toInt).getOrElse(8080)
  }

  override def server(args: List[String]): Task[Server] = {
    println(s"Im running on $getPort dyno ${System.getenv("DYNO")}")
    BlazeBuilder
      .mountService(weatherService, "/")
      .bindHttp(getPort, "0.0.0.0")
      .start
  }
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
