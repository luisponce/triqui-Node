/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triquiclient;
import controllers.GameController;
import controllers.PlayersController;
import domain.Player;
/**
 *
 * @author jonathaneidelman
 */
public class TriquiClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PlayersController pc = new PlayersController();
        
        Player p1 = pc.createPlayer("Jonathan");
        
        pc.listAllConnectedPlayers();
        
        pc.getPlayersNotifications(p1);
        
        GameController g = GameController.getInstance();
        
        g.listAllActiveGames();
    }
    
}
