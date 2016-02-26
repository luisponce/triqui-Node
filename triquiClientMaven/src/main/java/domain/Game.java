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

    public int getPlayerInTurn() {
        return playerInTurn;
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
    
    
    
}
