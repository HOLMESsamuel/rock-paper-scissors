package com.example.demo;


import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.*;


@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class GameController {

    private static final List<Game> games = new ArrayList<>();

    /**
     * Uses stream, more expressive and terse than the old school for loop
     * More functional, stronger affinity with functions, encourage less mutability, more scalable
     * Worse performance than looping through an array though
     * @param uuid Unique Identifier of the game being searched for
     * @return The first found game if it exists, else null
     */
    private Game getGame(UUID uuid) {
        List<Game> gameList = games.stream()
                .filter(game -> game.getUUID().equals(uuid))
                .collect(Collectors.toList());
        return gameList.size() == 1 ? gameList.get(0) : null;
    }

    @GET
    @Path("/{uuid}")
     // Return game with given uuid
    public String getGameResult(@PathParam("uuid") UUID uuid) {
        Game game = getGame(uuid);
        if (game != null) {
            return ResponseUtil.gameFound(game);
        } else {
            return ResponseUtil.gameNotFound();
        }
    }

    @POST
    // Create new game, return uuid
    public String createGame(String playerName) {
        Game newGame = new Game();
        UUID uuid = newGame.getUUID();

        if (playerName != null && !playerName.isEmpty()) {
            newGame.addPlayer(playerName);
            games.add(newGame);
            return ResponseUtil.gameCreated(uuid);
        }
        return ResponseUtil.missingName();
    }

    @POST
    @Path("/{uuid}/join")
    //Add player to game
    public String addPlayer(@PathParam("uuid") UUID uuid, String playerName) {
        Game game = getGame(uuid);

        if (playerName == null)
            return ResponseUtil.missingName();
        if (game == null)
            return ResponseUtil.gameNotFound();
        if (Boolean.TRUE.equals(game.playerIsInGame(playerName)))
            return ResponseUtil.playerAlreadyInGame();
        if (Boolean.TRUE.equals(game.gameIsFull()))
            return ResponseUtil.gameIsFull();

        game.addPlayer(playerName);

        return ResponseUtil.gameJoined(uuid, game);
    }

    @POST
    @Path("/{uuid}/move")
    //Add move to game
    public String addPlayerMove(@PathParam("uuid") UUID uuid, Instruction instruction) {
        Game game = getGame(uuid);
        if (game == null)
            return ResponseUtil.gameNotFound();

        String playerName = instruction.getName();
        if (playerName == null)
            return ResponseUtil.missingName();
        if (Boolean.TRUE.equals(!game.playerIsInGame(playerName)) && Boolean.TRUE.equals(game.gameIsFull()))
            return ResponseUtil.gameIsFull();

        String playerMove = instruction.getMove();
        if (Boolean.FALSE.equals(Move.validMove(playerMove)))
            return ResponseUtil.notValidMove(playerMove);
        if (game.playerIsInGame(playerName) && game.getPlayer(playerName).playerHasMadeMove())
            return ResponseUtil.moveAlreadyMade(playerName);

        StringBuilder response = new StringBuilder();

        if (Boolean.TRUE.equals(game.gameHasRoom()) && Boolean.TRUE.equals(!game.playerIsInGame(playerName))) {
            game.addPlayer(playerName);
            response.append(ResponseUtil.gameJoined(uuid, game));
        }

        game.addPlayerMove(playerName, playerMove);
        game.setResultOfGameIfPossible();
        response.append(ResponseUtil.moveAdded(game.getPlayer(playerName).getPlayerMove().toString()));
        return response.toString();
    }
}