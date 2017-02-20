package sbousamra.weatherapp

import Types._

object WeatherApp {
  def main(args: Array[String]) {
    val testClass = WeatherForecastResponse("africa", 2400, 25, 30, 50, 100, "cloudy")
    val test = encodeJson(testClass).spaces2
    println(test)
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
