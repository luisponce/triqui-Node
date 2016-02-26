/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import domain.Notification;
import domain.Player;
import helpers.Connection;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author jonathaneidelman
 */
public class PlayersController {
    
    private static PlayersController instance;
    
    private PlayersController(){
        
    }
    
    public static PlayersController GetInstance(){
        if(instance == null){
            instance = new PlayersController();
        }
        
        return instance;
    }
    
    public Player fetchPlayer(int id){
        Connection c = new Connection();
        
        String res = c.makeGETRequest("/player/"+id, Connection.serverURL);
        return jsonToPlayer(new JSONObject(res));
    }
    
    public Player createPlayer(String name){
        Player p1 = new Player();
        Connection c = new Connection();
        JSONObject reqBody = new JSONObject();
        reqBody.put("name", name);
        
        String res = c.makePOSTRequest("/player",
                reqBody.toString(), Connection.serverURL);
        
        return jsonToPlayer(new JSONObject(res));
    }
    
    public ArrayList<Player> listAllConnectedPlayers(){
       //Player[] players = new Player[100];
       ArrayList<Player> players = new ArrayList();
       Connection c = new Connection();
       String response = c.makeGETRequest("/player", Connection.serverURL);
       
       JSONArray playerArray  = new JSONArray(response);
       
       for (int i = 0; i<playerArray.length(); i++) {
           Player p = jsonToPlayer(playerArray.getJSONObject(i));
           players.add(p);
        }
       
       return players;
    }
    
    public ArrayList<Notification> getPlayersNotifications(Player player){
        ArrayList <Notification> notifications = new ArrayList();
        Connection c = new Connection();
        String response = 
                c.makeGETRequest("/player/" + player.getId() + "/notification",
                        Connection.serverURL);
        JSONArray notificationArray = new JSONArray(response);
       
       for (int i = 0; i<notificationArray.length(); i++) {
           Notification n = NotificationController.GetInstance().
                   jsonToNotification(notificationArray.getJSONObject(i));
           
           notifications.add(n);
        }
        return notifications;
    }
    
    public Notification sendNotificationToPlayer(Notification n){
        Connection c = new Connection();
        
        JSONObject body = new JSONObject();
        //TODO: check for the notification type...
        body.put("type", "gameinvite");
        body.put("sender", n.getSender().getId());
        body.put("to", n.getTo().getId());
        
        String res = c.makePOSTRequest("/player/"+n.getTo().getId()+"/notification", 
                body.toString(), Connection.serverURL);
        
        JSONArray notificationQueue = 
                new JSONObject(res).getJSONArray("notification");
        JSONObject notification = 
                notificationQueue.getJSONObject(notificationQueue.length()-1);
        
        return NotificationController.GetInstance()
                .jsonToNotification(notification);
    }
    
    public Player jsonToPlayer(JSONObject json){
        int id = json.getInt("id");
        String name = json.getString("name");
        String status = json.getString("status");
        
        return new Player(id, name, status);
    }
}
