/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import domain.Player;
import helpers.Connection;
/**
 *
 * @author jonathaneidelman
 */
public class PlayersController {
    
    public Player createPlayer(String name){
        Player p1 = new Player();
        Connection c = new Connection();
        String res;
        
        String body = "{ name: \"" + name + "\" }";
        
        res = c.makePOSTRequest("/player", body, Connection.serverURL);
        
        System.out.println("Respuesta: " + res);
        return p1;
    }
    
    public Player[] listAllConnectedPlayers(){
       Player[] players = new Player[100];
       
       return players;
    }
    
    
}
