package SARL.agents.utils.geolocation.mapbox;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import SARL.agents.utils.geolocation.GeoLocationService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import  javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RoutingService {
    private static final String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiYWJkZXJyYWhtYW5lbGgiLCJhIjoiY2xqYWhvcDlmMDIzeTN0cnl3Z2tsaGpkcCJ9.IDqKDyBcsFSqbNwMa9abvg";


	public static List<Node> getRoute(Node startNode, Node destinationNode) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = String.format("https://api.mapbox.com/directions/v5/mapbox/driving/%f,%f;%f,%f?geometries=geojson&access_token=%s",
                startNode.getLongitude(), startNode.getLatitude(),
                destinationNode.getLongitude(), destinationNode.getLatitude(),
                MAPBOX_ACCESS_TOKEN);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JsonNode root = objectMapper.readTree(responseBody);
        System.out.println(responseBody);
        JsonNode coordinates = root.path("routes").get(0).path("geometry").path("coordinates");

        List<Node> route = new ArrayList<>();
        route.add(startNode);
        for (JsonNode coordinate : coordinates) {
            double longitude = coordinate.get(0).asDouble();
            double latitude = coordinate.get(1).asDouble();
            Node node = new Node(latitude, longitude);
            route.add(node);
        }
        route.add(destinationNode);
        return route;
    }
	
	public static List<Node> getRoute(Node startNode, Node viaNode, Node destinationNode) throws IOException {		
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String url = String.format("https://api.mapbox.com/directions/v5/mapbox/driving/%f,%f;%f,%f;%f,%f?geometries=geojson&access_token=%s",
                startNode.getLongitude(), startNode.getLatitude(),
                viaNode.getLongitude(), viaNode.getLatitude(),
                destinationNode.getLongitude(), destinationNode.getLatitude(),
                MAPBOX_ACCESS_TOKEN);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode coordinates = null;
        try {
        	coordinates = root.path("routes").get(0).path("geometry").path("coordinates");
		} catch (Exception e) {
			throw new NullPointerException("there is no route to destination");
		}
         

        List<Node> route = new ArrayList<>();
        route.add(startNode);
        for (JsonNode coordinate : coordinates) {
            double longitude = coordinate.get(0).asDouble();
            double latitude = coordinate.get(1).asDouble();
            Node node = new Node(latitude, longitude);
            route.add(node);
        }
        route.add(destinationNode);
        return route;
    }
	
	
	public static Pair<List<Node>, List<Node>> getRouteAsPair(Node startNode, Node viaNode, Node destinationNode) throws IOException {		
	    List<Node> fromSourceToStation  = getRoute(startNode, viaNode);
        List<Node> fromStationToDestination   = getRoute(viaNode, destinationNode);
        
        return new Pair(fromSourceToStation, fromStationToDestination);
    }
	
	
	
    public static void main(String[] args) throws IOException {
    	Pair<Double,Double> pair = GeoLocationService.getCurrentLocationAsPair().get();
    	SARL.agents.utils.geolocation.overpass.Node destination = GeoLocationService.getRandomNodeFromWayWithinRadius(1000);
        Node startNode = new Node(pair.getKey(), pair.getValue()); // Starting Node
        Node endNode = new Node(destination.getLat(), destination.getLon() ); // Ending Node
        List<Node> route = getRoute(startNode, endNode);

        for (Node node : route) {
            System.out.println(node);
        }
    }
}


