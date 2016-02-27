/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domain.Game;
import domain.Game.Tile;
import domain.Player;
import helpers.Connection;
import java.util.ArrayList;
import javax.management.remote.JMXConnectorFactory;
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
public class GameController {
    private static GameController instance;
    
    private GameController(){
        
    }
    
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }
    
    
    public ArrayList<Game> listAllActiveGames(){
        ArrayList<Game> games = new ArrayList();
        
        Connection c = new Connection();
        
        String response = c.makeGETRequest("/game", Connection.serverURL);
        
        JSONArray js = new JSONArray(response);
        
        for(Object o: js){
            JSONObject game = (JSONObject) o;
            int gId = game.getInt("id");
            int p1 = game.getInt("player1");
            int p2 = game.getInt("player2");
            Player player1 = new Player(p1, null, null, null);
            Player player2 = new Player(p2, null, null, null);
            Game g = new Game(gId, -1, null, player1, player2);
            
            games.add(g);
        }
        
        for(Game game : games){
            System.out.println("Game: " + game.getId() + " p1: "  
                    + game.getPlayer1().getId() + " p2: " 
                    + game.getPlayer2().getId());
        }
        return games;
    }
    
    public Game createGame(Player p1, Player p2){
        Game game = new Game();
        
        Connection c = new Connection();
        String body = "{ \"player1:" + p1.getId() + ", player2:" + p2.getId() 
                + " }";
        String response = c.makePOSTRequest("/game", body, Connection.serverURL);
        
        JSONObject js = new JSONObject(response);
        int id = js.getInt("id");
        int playerInTurn = js.getInt("playerTurn");
        //System.out.println(js.getJSONArray("board").toString());
        //TODO: get board and send it in a tile[][] to return the created game that the response sent.
        
        JSONArray board = js.getJSONArray("board");
        ArrayList<JSONArray> rows = new ArrayList<JSONArray>();
            
        for(int i = 0; i < 3; ++i) {
            JSONArray row = board.getJSONArray(i);
            rows.add(row);
        }
            
        Tile[][] boardTable = new Tile[3][3];
            
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                Game.Tile t = Game.fromStringToEnum(rows.get(i).getString(j));
                boardTable[i][j] = t;
            }    
        }
            //game = new Game(id, playerInTurn, board, p1, p2);
        return game;
    }
    
    public Game fetchGame(int id){
        Connection c = new Connection();
        
        JSONObject resp = 
                new JSONObject(c.makeGETRequest("/game/"+id,
                        Connection.serverURL));
        
        return jsonToGame(resp);
    }
    
    public Game jsonToGame(JSONObject json){
        int id = json.getInt("id");
        int playerInTurn = json.getInt("playerTurn");
        Tile[][] board = jsonToBoard(json.getJSONObject("board"));
        Player p1 = PlayersController.GetInstance().
                jsonToPlayer(json.getJSONObject("player1"));
        Player p2 = PlayersController.GetInstance().
                jsonToPlayer(json.getJSONObject("player2"));
        
        return new Game(id, playerInTurn, board, p1, p2);
    }
    
    public Tile[][] jsonToBoard(JSONObject json){
        Tile[][] board = new Tile[3][3];
        
        //TODO: populate board from json
        
        return board;
    }
}
