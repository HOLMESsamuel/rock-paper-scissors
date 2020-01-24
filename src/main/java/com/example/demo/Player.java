package com.example.demo;

class Player {
    private final String playerName;
    private Move playerMove;

    Player(String name){
        this.playerName = name;
    }

    String getPlayerName(){
        return playerName;
    }

    Move getPlayerMove(){
        return playerMove;
    }

    void setPlayerMove(String move){
        if(getPlayerMove() == null)
            playerMove = Move.parseMove(move);
    }

    Boolean playerHasMadeMove(){
        return getPlayerMove() != null;
    }

}
