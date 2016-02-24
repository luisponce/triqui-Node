/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import domain.Player;
import helpers.Connection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.json.JSONWriter;
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
        
        //JSONParser parser = new JSONParser();
        JSONObject js  = new JSONObject(res);
        
        String playersName = js.getString("name");
        String playersId = js.getString("id");
        int id = Integer.parseInt(playersId);
        String playersStatus = js.getString("status");
        
        
        p1 = new Player(id, playersName, playersStatus, null);
        
        return p1;
    }
    
    public Player[] listAllConnectedPlayers(){
       Player[] players = new Player[100];
       
       return players;
    }
    
    
}
