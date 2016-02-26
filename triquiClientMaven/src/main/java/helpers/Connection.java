/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *
 * @author jonathaneidelman
 */
public class Connection {
    
    public static String serverURL = "10.131.137.212";
    public String makeGETRequest(String path, String serverURL){
        String res = "";
        try {

            URL url = new URL("http://" + serverURL + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
		(conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                res += output;
            }
            System.out.println(output);

            conn.disconnect();

	  } catch (MalformedURLException e) {

		System.out.println(e.getMessage());

	  } catch (IOException e) {

		System.out.println(e.getMessage());

	  }
        return res;
    }
    
    public String makePOSTRequest(String path, String body, String serverURL){
       String res = "";
       try {

            URL url = new URL("http://" + serverURL + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "application/json");

            String input = body;

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
		(conn.getInputStream()))); 

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
		System.out.println(output);
                res += output;
            }
            
            System.out.println(res);

            conn.disconnect();

	  } catch (MalformedURLException e) {

		System.out.println(e.getMessage());

	  } catch (IOException e) {

		System.out.println(e.getMessage());

	 }
       return res;
    }
    
    public String makeDELETERequest(String path, String serverURL){
        String res = "";
        try {

            URL url = new URL("http://" + serverURL + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            //conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
		(conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                res += output;
            }
            System.out.println(output);

            conn.disconnect();

	  } catch (MalformedURLException e) {

		System.out.println(e.getMessage());

	  } catch (IOException e) {

		System.out.println(e.getMessage());

	  }
        return res;
    }
}
