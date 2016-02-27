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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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
        try {
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
        } catch (Connection.HTTPError ex) {
            return null;
        }
    }
    
    public Game createGame(Player p1, Player p2){
        Game game = new Game();
        
        Connection c = new Connection();
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("player1", ""+p1.getId());
        bodyJson.put("player2", ""+p2.getId());
        
        String response = c.makePOSTRequest("/game", bodyJson.toString(), Connection.serverURL);
        
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
        game = new Game(id, playerInTurn, boardTable, p1, p2);
        return game;
    }
    
    public Game fetchGame(int id){
        try {
            Connection c = new Connection();
            
            JSONObject resp =
                    new JSONObject(c.makeGETRequest("/game/"+id,
                            Connection.serverURL));
            
            return jsonToGame(resp);
        } catch (Connection.HTTPError ex) {
            return null;
        }
    }
    
    public JSONObject deleteGame(int id){
        Connection c = new Connection();
        
        JSONObject resp = 
                new JSONObject(c.makeDELETERequest("/game/"+id, Connection.serverURL));
        
        return resp;
    }
    
    public Game updateGame(int id, Tile[][] board, int playerTurn){
        Connection c = new Connection();
        JSONObject body = new JSONObject();
        
        body.put("id", id);
        body.put("board", boardToJson(board));
        body.put("playerTurn", playerTurn);
        
        JSONObject resp = 
                new JSONObject(c.makePOSTRequest("/game/"+id, body.toString() ,Connection.serverURL));
        
        return jsonToGame(resp);
    }
    
    public Game jsonToGame(JSONObject json){
        int id = json.getInt("id");
        int playerInTurn = json.getInt("playerTurn");
        Tile[][] board = jsonToBoard(json.getJSONArray("board"));
        Player p1 = PlayersController.GetInstance().
                jsonToPlayer(json.getJSONObject("player1"));
        Player p2 = PlayersController.GetInstance().
                jsonToPlayer(json.getJSONObject("player2"));
        
        return new Game(id, playerInTurn, board, p1, p2);
    }
    
    public Tile[][] jsonToBoard(JSONArray json){
        Tile[][] board = new Tile[3][3];
        
        JSONArray boardJs = new JSONArray(json.toString());
        ArrayList<JSONArray> rows = new ArrayList<JSONArray>();
            
        for(int i = 0; i < 3; ++i) {
            JSONArray row = boardJs.getJSONArray(i);
            rows.add(row);
        }
            
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                Game.Tile t = Game.fromStringToEnum(rows.get(i).getString(j));
                board[i][j] = t;
                System.out.println(board[i][j].toString());
            }    
        }
        //TODO: populate board from json
        
        return board;
    }
    
    public JSONArray boardToJson (Tile[][] board){
        
        JSONArray boardJSON = new JSONArray();
        for(int i = 0; i < 3; ++i) {
            JSONArray row = new JSONArray();
            for(int j = 0; j < 3; ++j) {
                row.put(board[i][j].toString());
            }
            boardJSON.put(row);
        }
                
        System.out.println(boardJSON.toString());
        
        return boardJSON;
    }
}
