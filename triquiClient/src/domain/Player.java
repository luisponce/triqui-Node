/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author jonathaneidelman
 */
public class Player {
    public enum Status {
        WAITING, PLAYING, AWAY
    }
    
    private final int id;
    private final String name;
    private Status status;
    
    private ArrayList<Notification> notifications;

    public Player(int id, String name, Status status,
            ArrayList<Notification> notifications) {
        
        this.id = id;
        this.name = name;
        this.status = status;
        this.notifications = new ArrayList<>();
    }

    public Player() {
        id = -1;
        name = "";
    }
    
    

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    public ArrayList<Notification> getNotifications(){
        return notifications;
    }
    
    public void addNotification(Notification notification){
        notifications.add(notification);
    }
}
