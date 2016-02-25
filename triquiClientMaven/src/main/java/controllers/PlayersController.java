/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import domain.Player;
import helpers.Connection;
import java.util.ArrayList;
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
        int id = js.getInt("id");
        String playersStatus = js.getString("status");
        
        
        p1 = new Player(id, playersName, playersStatus, null);
        
        return p1;
    }
    
    public ArrayList listAllConnectedPlayers(){
       //Player[] players = new Player[100];
        ArrayList<Player> players = new ArrayList();
       Connection c = new Connection();
       String response = c.makeGETRequest("/player", Connection.serverURL);
       System.out.println("Respuesta!!! \n \n" + response);
       JSONArray js  = new JSONArray(response);
       
       for (Object o : js) {
            JSONObject player = (JSONObject) o;
            String name = player.getString("name");
            String status = player.getString("status");
            //int id = player.getInt("id");
            Player p1 = new Player(-1, name, status, null);
            players.add(p1);
        }
       
       for(int i = 0; i < players.size(); i++){
           System.out.println("Player's " + i + " name: " + players.get(i).getName());
       }
       
       return players;
    }
    
    
}
