import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class Fetch {
    private final String key = "843b5b1093b07ff0e1d9f681a3a3cb0c"; // API token
    private final String google_key = "AIzaSyCVMgcmhkqfQKxbeUnJPGx6V5twYGMq-9k"; // google api token
    private String query; // API URL
    private String google_query; // google API URL
    JSONObject json = new JSONObject(); // the JSON object that contains weather data
    JSONObject google_json = new JSONObject(); // the JSON object that contains city co ordinates
    ImageIcon map = new ImageIcon();
    String static_map_query;
    Geolocator geolocator;



    // constructor in case user inputs string
    public Fetch(String city) throws IOException {
        this.google_query = "https://maps.googleapis.com/maps/api/geocode/json?address="+ city + "&key=" + google_key;
        geolocator = new Geolocator(google_query);
        this.query = "https://api.openweathermap.org/data/2.5/onecall?lat="+ geolocator.lat + "&lon=" + geolocator.lon + "&units=imperial&appid=" + key;
        set_json(query);
        static_map_query = "https://maps.googleapis.com/maps/api/staticmap?center=" + geolocator.lat + "," + geolocator.lon + "&zoom=13&size=300x300&maptype=roadmap&key=" + google_key;
        URL url = new URL(static_map_query);
        BufferedImage img = ImageIO.read(url);       
        map = new ImageIcon(img);
    }


    //creates JSON object based on user input
    public void set_json(String query){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(query)).build();
        this.json = new JSONObject(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .join());              
    }






}