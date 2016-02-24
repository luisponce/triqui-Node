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
    
    public Player createPlayer(String body){
        Player p1 = new Player();
        Connection c = new Connection();
        String res;
        
        res = c.makePOSTRequest("/player", body, "private-33fd22-triquirest.apiary-mock.com");
        
        System.out.println("Respuesta: " + res);
        
        return p1;
    }
    
    
}
