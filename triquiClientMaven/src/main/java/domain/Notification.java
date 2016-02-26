/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 * @author luisponcedeleon
 */
public class Notification {
    
    public enum Type {
        GAMEINVITE
    }
    
    private final int id;
    private final Player sender;
    private final Player to;
    private final Type type;
    private boolean accepted;

    public Notification(int id, Player sender, Player to, Type type,
            boolean accepted) {
        this.id = id;
        this.sender = sender;
        this.to = to;
        this.type = type;
        this.accepted = accepted;
    }
    
    public void Accept(){
        this.accepted = true;
    }

    public int getId() {
        return id;
    }
    
    
}
