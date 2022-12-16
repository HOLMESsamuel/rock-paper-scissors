# RestAPI for Rock Paper Scissor in Java with Quarkus
A backend REST-API for Rock Paper Scissors built in Java using Gradle and Spring

## Installation
To run the server, you need Java JDK or JRE and Gradle

Follow the guide on https://gradle.org/install/ on how to install

## Starting the server
Run the server from the application folder with 

`./gradlew bootRun`

The server starts and runs on http://localhost:8080

Or, build the jar file with `./gradlew build` and run it with

`java -jar build/libs/java-rest-api-1.jar`

## Testing
A few unit tests are implemented, testing may be run with `./gradlew clean test`

Because of time constraints, only some unit tests are implemented, and no integration tests are implemented

Some nice best practices may be found at https://phauer.com/2016/testing-restful-services-java-best-practices/

Blog post about SpringBoot integration testing https://blog.jayway.com/2014/07/04/integration-testing-a-spring-boot-application/

## Usage
The API supports the following REST calls
```
POST /games

GET /games/{uuid}

POST /games/{uuid}/join

POST /games/{uuid}/move
```

### The post API demand the request body as JSON

#### CREATE GAME
`POST /games` 
requires a name in JSON format as request body
creates a new game and returns a string object with the UUID of the created game

Example request body for creating a game with `POST /games`:

`playername` 

#### GET GAME
`GET /games/{uuid}`
returns the UUID for the new game as well as the players in the game and their status.

If both players have made their move, it returns the game status instead of player status. Response format is String.

Example URL for getting a game with UUID e27cf7fa-592f-4c7b-9413-54a0c5660c97 with `GET /games/{uuid}`:

`http://localhost:8080/games/e27cf7fa-592f-4c7b-9413-54a0c5660c97` 
#### JOIN GAME
`POST /games/{uuid}/join`
requires a name in JSON format, 
returns a message saying if player joined the game, maximum 2 players per game, names has to be distinct

#### MAKE MOVE
`POST /games/{uuid}/move`
requires a name and a move in JSON format, the move has to be rock, paper or scissor, not caps sensitive.

Can add a move to an already existing player or add a move with a new player if there is room

If it is the last move, calculates the winner, which may be retrieved with `GET /games/{uuid}`

Example request body for joining and making a move with `POST /games{uuid}/move`:

`{"name":"playername", "move":"rock"}` 

## PLAYING
You may for example use Postman to access the API

To give a JSON request body, open the Body tab, choose raw from the radio buttons, and use the dropdown to the right to change from Text to JSON

### TODO
- More tests
- Try-catches
- Return JSON 

