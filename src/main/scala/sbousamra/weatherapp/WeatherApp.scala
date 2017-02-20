package sbousamra.weatherapp

object WeatherApp {
  def main(args: Array[String]) {
    println("test")
  }
}

//Pseudocode
//
//1. Create method that issues a GET request to a server containing the weather information we want back as JSON.
//      Will look something like:
//        request: Request => serverRequestOutput: Response[JSON]
//
//2. Now create a method that will take this JSON and filter the data we want, returning our own JSON.
//      Will look something like:
//        serverRequestOutput: Response[JSON] => ourWantedJson: JSON
//
//3. Create a method that will turn this JSON into a format that is nicely readable for the user.
//    Will look something like:
//      ourWantedJson: JSON => niceFormat: NotSureOnType
//
//def getDataFromSource(request: Task[Response]): Response[JSON] = {
//  ???
//}
//
//def ourJson(sourceData: Response[JSON]): JSON = {
//  ???
//}
//
//def formatForUser(json: JSON) = {
//  ???
//}


