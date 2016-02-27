/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

/**
 *
 * @author jonathaneidelman
 */
public class Player {
    private final int id;
    private final String name;
    private String status;
    
    
    private ArrayList<Notification> notification;

    public Player(int id, String name, String status,
            ArrayList<Notification> notification) {
        
        this.id = id;
        this.name = name;
        this.status = status;
        this.notification = notification;
    }

    public Player(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.notification = new ArrayList<>();
    }

    public Player() {
        id = -1;
        name = "";
    }
    
    

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    @JsonIgnore
    public ArrayList<Notification> getNotifications(){
        return notification;
    }
    
    public void addNotification(Notification notification){
        this.notification.add(notification);
    }
}
