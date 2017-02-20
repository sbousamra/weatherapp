#Weather Application Project
This project retrieves data about the weather at a location input by the user.

#Goals of Project
The project aims to give the following data in a easily readable format:

Daily temperatures spanning over the week.
Current weather such as sunny, overcast, heavy rain or storming.

Humidity, precipitation, wind

Time of request and location of which it was requested for

#Personal Goals
Learn how to use HTTP to pull data from another server.

Learn how to recieve the data pulled into JSON, and then take the wanted data and put it into a usable format.

Learn how to use Trello for project management, treating the project like a real-life working project managed by a lead developer.

Learn about REST, HTTP (routes, verbs, query parameters).

#Api Specification

```yaml
Weather:
    get:
        route: /weather/area?days=number

        parameters:
            days: number of days we are returning weather for
            area: location of where we want the weather for

        responses:
            location: location name
            time: current time of request
            temperature: temperature value
            precipitation: precipitation value
            humidity: humidity value
            wind: wind value
            forecast: e.g. sunny, mostly sunny, cloudy, storming etc.
```
