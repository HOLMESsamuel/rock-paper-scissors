# RestAPI for Rock Paper Scissor in Java
A backend REST-API for Rock Paper Scissors built in Java using Gradle and Spring

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

##Usage
The API supports the following REST calls
```
POST /games

GET /games/{uuid}

POST /games/{uuid}/join

POST /games/{uuid}/move
```

###The post API demand the request body as JSON

####CREATE GAME
`POST /games` 
requires a name in JSON format as request body
creates a new game and returns a string object with the UUID of the created game

Example request body for creating a game with `POST /games`:

`{"name":"playername"}` 

####GET GAME
`GET /games/{uuid}`
returns the UUID for the new game as well as the players in the game and their status.

If both players have made their move, it returns the game status instead of player status. Response format is String.

Example URL for getting a game with UUID e27cf7fa-592f-4c7b-9413-54a0c5660c97 with `GET /games/{uuid}`:

`http://localhost:8080/games/e27cf7fa-592f-4c7b-9413-54a0c5660c97` 
####JOIN GAME
`POST /games/{uuid}/join`
requires a name in JSON format, 
returns a message saying if player joined the game, maximum 2 players per game, names has to be distinct

####MAKE MOVE
`POST /games/{uuid}/move`
requires a name and a move in JSON format, the move has to be rock, paper or scissor, not caps sensitive.

Can add a move to an already existing player or add a move with a new player if there is room

If it is the last move, calculates the winner, which may be retrieved with `GET /games/{uuid}`

Example request body for joining and making a move with `POST /games{uuid}/move`:

`{"name":"playername", "move":"rock"}` 

##PLAYING
You may for example use Postman to access the API

To give a JSON request body, open the Body tab, choose raw from the radio buttons, and use the dropdown to the right to change from Text to JSON

###TODO
- More tests
- Try-catches
- Return JSON 

## Problembeskrivning
Skapa ett enkelt HTTP-API som låter utvecklare lösa sina
meningsskiljaktigheter med hjälp av spelet Sten, sax, påse.
Reglerna är enkla, bäst av 1 match vinner. 
Vi rekommenderar
er att följa principerna för REST men avsteg kan göras om
man kan motivera det på ett bra sätt.

## Krav
- Ett oavgjort resultat räknas som ett resultat, det innebär
att spelet inte behöver startas om vid oavgjort.
- Ingen persisteringsmekanism är tillåten. Hela tillståndet
(statet) ska hållas i minnet.
- README ska finnas och innehålla exempel på hur
applikationen kan köras via något lämpligt verktyg, tex:
-- curl
-- wget
-- Postman
-- HTTPie

- Implementationen ska vara skriven på någon av de angivna plattformarna.

I övrigt förväntar vi oss att implementationen är
förvaltningsbar och följer "best practices".

## Begränsningar
För att begränsa arbetsinsatsen som krävs så införs följande
begränsningar:
- Applikationen behöver inte stödja returmatcher.
- Det behöver inte finnas någon klient-implementation. 
(Typ CLI, GUI).

## Exempel på ett spelflöde
1. Spelare 1 skickar ett request för att skapa ett nytt spel
och får tillbaka ett spel-ID från servern.
2. Spelare 1 skickar ID till spelare 2 via valfri
kommunikationskanal. (t.ex., mail, slack eller telefax)
3. Spelare 2 ansluter sig till spelet med hjälp av ID.
4. Spelare 1 gör sitt drag (Sten).
5. Spelare 2 gör sitt drag (Sax).
6. Spelare 1 kollar tillståndet för spelet och upptäcker att
hen vann.
7. Spelare 2 kollar tillståndet för spelet och upptäcker att
hen förlorade.

## Exempel API-design
Nedan visas ett exempel på vilka endpoints som API:et skulle
kunna exponera för en klient:
```
POST	/api/games

POST	/api/games/{id}/join

POST	/api/games/{id}/move

GET	/api/games/{id} där id kan vara ett UUID.
```

#### GET	/api/games/{id}
Returnerar ett givet spels nuvarande tillstånd med ingående
attribut. Tänk på vilka attribut som ska visas för vem och när.

####POST	/api/games
Skapar ett nytt spel. Ange spelarnamn i request-body:
```
{
"name":	"Lisa"
}
```
#### POST	/api/games/{id}/join
Ansluter till ett spel med givet ID. Ange spelarnamn i requestbody:
```
{
"name":	"Pelle"
}
```
#### POST	api/games/{id}/move
Gör ett drag. Ange namn och drag i request-body:
```
{
"name":	"Lisa",
"move":	"Rock"
}
```
##Plattformar
### Java
Om du bygger en Java-lösning ska Apache
Maven eller Gradle användas för att bygga och paketera
API:et.

- API:et ska kunna startas direkt från Maven/Gradle
(exempelvis genom att Jetty och mvn	jetty:run) eller som
en executable jar via exempelvis Spring Boot.

- Exempel på möjliga ramverk: SpringMVC (Spring
Boot), Dropwizard, Jersey

### .NET
- Om du bygger en .NET-lösning ska antingen .NET Core
CLI 2.x eller en Visual Studio 2017-solution användas.
- Använder du en Visual Studio 2017-solution ska
lösningen kunna startas direkt från Visual Studio.
- Använd gärna ASP.NET Core.

### JavaScript - Node.js
- Om du bygger en Node.js-lösning skall den gå att starta
via npm eller yarn genom exempelvis yarn	start eller npm	
run	start.
- Kika gärna på ramverk som Hapi, Koa eller Express