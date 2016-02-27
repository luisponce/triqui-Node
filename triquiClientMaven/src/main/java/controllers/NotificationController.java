/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import domain.Notification;
import domain.Player;
import helpers.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author luisponcedeleon
 */
public class NotificationController {
    private static NotificationController instance;
    
    private NotificationController(){
        
    }
    
    public static NotificationController GetInstance(){
        if(instance == null){
            instance = new NotificationController();
        }
        
        return instance;
    }
    
    public Notification jsonToNotification(JSONObject json){
        int id = json.getInt("id");
        Player sender = null;
        Player to = null;
        try {
            sender = PlayersController.GetInstance().
                    fetchPlayer(json.getInt("sender"));
            to = PlayersController.GetInstance().
                    fetchPlayer(json.getInt("to"));
        } catch (JSONException jSONException) {
            sender = PlayersController.GetInstance().
                    jsonToPlayer(json.getJSONObject("sender"));
            to = PlayersController.GetInstance().
                    jsonToPlayer(json.getJSONObject("to"));
        }
        
        
        //TODO get the actual type in case we add more notifications
        Notification.Type type = Notification.Type.GAMEINVITE;
        boolean accepted = json.getBoolean("accepted");
        
        Notification n = new Notification(id, sender, to, type, accepted);
        try {
            int game = json.getInt("game");
            n.setGame(game);
            return n;
        } catch (JSONException jSONException) {
            return n;
        }
    }
    
    public Notification fetchNotification(int id){
        Connection c = new Connection();
        
        String resp;
        try {
            resp = c.makeGETRequest("/notification/"+id, Connection.serverURL);
        } catch (Connection.HTTPError ex) {
            return null;
        }
        
        return jsonToNotification(new JSONObject(resp));
    }
    
    public Notification acceptNotification(int id, int gameId) {
        Connection c = new Connection();
        
        JSONObject body = new JSONObject();
        body.put("accepted", true);
        body.put("game", gameId);
        
        JSONObject resp = 
                new JSONObject(c.makePOSTRequest("/notification/"+id, 
                        body.toString(), Connection.serverURL));
        
        return jsonToNotification(resp);
    }
    
    public JSONObject deleteNotification(int id) {
        Connection c = new Connection();
                
        JSONObject resp = 
                new JSONObject(c.makeDELETERequest("/notification/"+id, 
                        Connection.serverURL));
        
        return resp;
    }
}
