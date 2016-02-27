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
    private int game;

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

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

    public Player getSender() {
        return sender;
    }

    public Player getTo() {
        return to;
    }

    public Type getType() {
        return type;
    }

    public boolean isAccepted() {
        return accepted;
    }
    
    
}
