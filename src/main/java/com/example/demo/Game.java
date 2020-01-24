package com.example.demo;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

class Game {
    private final UUID uuid;
    private boolean gameIsFull;
    private List<Player> playerList = Collections.synchronizedList(new ArrayList<>());
    private String resultOfGame;

    Game() {
        this.uuid = UUID.randomUUID();
        this.gameIsFull = false;
    }

    Game(UUID uuid) {
        this.uuid = uuid;
        this.gameIsFull = false;
    }

    UUID getUUID() {
        return this.uuid;
    }

    Boolean gameIsFull() {
        return gameIsFull;
    }

    Boolean gameHasRoom(){
        return playerList.size() < 2;
    }
    String getResultOfGame() {
        return resultOfGame;
    }

    private String decideWinner(Player player1, Player player2) {
        Move p1Move = player1.getPlayerMove();
        Move p2Move = player2.getPlayerMove();
        if (p1Move.beats(p2Move))
            return String.format("%s wins, with %s over %s's %s!",
                    player1.getPlayerName(),
                    p1Move,
                    player2.getPlayerName(),
                    p2Move);
        if (p2Move.beats(p1Move))
            return String.format("%s wins, with %s over %s's %s!",
                    player2.getPlayerName(),
                    p2Move,
                    player1.getPlayerName(),
                    p1Move);
        return "Draw!";
    }

    void setResultOfGameIfPossible(){
        if (getResultOfGame() == null &&
                !getPlayerMoves().contains(null) &&
                getPlayerMoves().size() >= 2)
            setResultOfGame();
    }

    private void setResultOfGame() {
        Player player1 = playerList.get(0);
        Player player2 = playerList.get(1);
        resultOfGame = decideWinner(player1, player2);
    }

    void addPlayer(String name) {
        if (gameHasRoom() && name != null) {
            Player player = new Player(name);
            playerList.add(player);
            if(!gameHasRoom())
                gameIsFull = true;
        }
    }

    Player getPlayer(String name) {
        List<Player> foundPlayer = playerList.stream().filter(player -> player.getPlayerName().equals(name)).collect(Collectors.toList());
        if (foundPlayer.size() == 1)
            return foundPlayer.get(0);
        return null;
    }

    List<Player> getPlayers() {
        return playerList;
    }

    List<String> getPlayerNames() {
        return getPlayers().stream().map(Player::getPlayerName).collect(Collectors.toList());
    }

    Boolean playerIsInGame(String playerName){
        return getPlayerNames().contains(playerName);
    }

    void addPlayerMove(String name, String move) {
        Player player = getPlayer(name);
        if(player != null) {
            int playerIndex = playerList.indexOf(player);
            player.setPlayerMove(move);
            playerList.set(playerIndex, player);
        }
    }

    private List<Move> getPlayerMoves() {
        return getPlayers().stream().map(Player::getPlayerMove).collect(Collectors.toList());
    }



}
