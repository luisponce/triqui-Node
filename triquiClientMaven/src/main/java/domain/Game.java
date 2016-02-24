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
        X, O, NONE
    }
    
    private int id;
    private int playerInTurn;
    
    private Tile[][] board;
    private Player player1;
    private Player player2;
    
    
    
}
