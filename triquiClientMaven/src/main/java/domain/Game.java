/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;


/**
 *
 * @author luisponcedeleon
 */
public class Game {
    public enum Tile {
        X, O, NONE;
        
        
        @Override
        public String toString() {
          switch(this) {
            case X: return "X";
            case O: return "O";
            case NONE: return "-";
            default: throw new IllegalArgumentException();
          }
        }
    }
    
    public static Tile fromStringToEnum(String move){
        switch(move){
            case "-": return Tile.NONE;
            case "X": return Tile.X;
            case "O": return Tile.O;
            default: throw new IllegalArgumentException();
        }
    }
    
    private int id;
    private int playerInTurn;
    
    private Tile[][] board;
    private Player player1;
    private Player player2;

    public Game(int id, int playerInTurn, Tile[][] board, Player player1, Player player2) {
        this.id = id;
        this.playerInTurn = playerInTurn;
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Game() {
    }

    public int getId() {
        return id;
    }

    public int getPlayerInTurnInt() {
        return playerInTurn;
    }
    public Player getPlayerInTurn() {
        if(playerInTurn == 1){
            return player1;
        } else {
            return player2;
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    
    
    public void makePlay(int pos){
        if(playerInTurn == 1){
            setBoardTile(pos, Tile.X);
            playerInTurn = 2;
        } else {
            setBoardTile(pos, Tile.O);
            playerInTurn = 1;
        }
    }
    
    /*pos:
    /   1 2 3
    /   4 5 6
    /   7 8 9
    */
    public Tile getTile(int pos){
        int i = (pos-1) / 3;
        int j = (pos-1) % 3;
        
        return board[i][j];
    }
    
    private void setBoardTile(int pos, Tile value){
        int i = (pos-1) / 3;
        int j = (pos-1) % 3;
        
        board[i][j] = value;
    }
    
    public Player getWinner(){
        
        for(int i = 0; i<3; i++){
            Tile cur = board[i][0];
            
            boolean exit = false;
            for(int j = 1; j<3 && !exit; j++){
                if(board[i][j] != cur){
                    exit = true;
                }
            }
            
            if(!exit){//all 3 of same
                if(cur == Tile.X){
                    return player1;
                } else if(cur == Tile.O) {
                    return player2;
                }
            }
        }
        
        for(int i = 0; i<3; i++){
            Tile cur = board[0][i];
            
            boolean exit = false;
            for(int j = 1; j<3 && !exit; j++){
                if(board[j][i] != cur){
                    exit = true;
                }
            }
            
            if(!exit){//all 3 of same
                if(cur == Tile.X){
                    return player1;
                } else if(cur == Tile.O) {
                    return player2;
                }
            }
        }
        
        Tile cur = board[0][0];
        boolean exit = false;
        for(int j = 1; j<3 && !exit; j++){
            if(board[j][j] != cur){
                exit = true;
            }
        }
        if(!exit){//all 3 of same
            if(cur == Tile.X){
                return player1;
            } else if(cur == Tile.O) {
                return player2;
            }
        }
        
        cur = board[2][0];
        exit = false;
        for(int j = 1; j<3 && !exit; j++){
            if(board[2-j][j] != cur){
                exit = true;
            }
        }
        if(!exit){//all 3 of same
            if(cur == Tile.X){
                return player1;
            } else if(cur == Tile.O) {
                return player2;
            }
        }
        
        Tile[] row1 = new Tile[3];
        boolean fullRow1 = true;
         for(int i = 0; i < 3; i++){
             row1[i] = board[0][i];
             if(row1[i] == Tile.NONE){
                 fullRow1 = false;
             }
         }
         
        Tile[] row2 = new Tile[3];
        boolean fullRow2 = true;
         for(int i = 0; i < 3; i++){
             row2[i] = board[1][i];
             if(row2[i] == Tile.NONE){
                 fullRow2 = false;
             }
         }
         
        Tile[] row3 = new Tile[3];
        boolean fullRow3 = true;
         for(int i = 0; i < 3; i++){
             row3[i] = board[2][i];
             if(row3[i] == Tile.NONE){
                 fullRow3 = false;
             }
         } 
         
         if(fullRow1 && fullRow2 && fullRow3){
             //si las tres estan llenas y no ha retornado ganador hay empate
             Player tie = new Player(-1, "Empate", null);
             return tie;
         }
        
        return null;
    }
}
