/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import domain.Notification;
import domain.Player;
import helpers.Connection;
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
        Player sender = PlayersController.GetInstance().
                jsonToPlayer(json.getJSONObject("sender"));
        Player to = PlayersController.GetInstance().
                jsonToPlayer(json.getJSONObject("to"));
        //TODO get the actual type in case we add more notifications
        Notification.Type type = Notification.Type.GAMEINVITE;
        boolean accepted = json.getBoolean("accepted");
        
        return new Notification(id, sender, to, type, accepted);
    }
    
    public Notification acceptNotification(int id) {
        Connection c = new Connection();
        
        JSONObject body = new JSONObject();
        body.put("accepted", true);
        
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
