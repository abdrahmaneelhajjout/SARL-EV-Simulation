package SARL.agents.geolocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.*;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import javafx.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.fasterxml.jackson.databind.ObjectMapper;

import SARL.agents.geolocation.overpass.Element;
import SARL.agents.geolocation.overpass.HaversineDistance;
import SARL.agents.geolocation.overpass.Node;
import SARL.agents.geolocation.overpass.NodesResponseData;
import SARL.agents.geolocation.overpass.Way;
import SARL.agents.geolocation.overpass.WaysResponseData;
import static SARL.agents.geolocation.overpass.HaversineDistance.haversine;;

public class GeoLocationService {

	private RawDBDemoGeoIPLocationService locationService = new RawDBDemoGeoIPLocationService();

	public GeoLocationService() throws Exception {

	}

	public static GeoIP getLCurrentLocationAsGeoIP() throws Exception {
		String ip = getIpAddress();
		RawDBDemoGeoIPLocationService locationService = new RawDBDemoGeoIPLocationService();
		return locationService.getLocation(ip);
	}

	public static String getIpAddress() throws IOException {
		String urlString = "http://checkip.amazonaws.com/";
		URL url = new URL(urlString);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			return br.readLine();
		}
	}

	public static Optional<Pair<Double, Double>> getCurrentLocationAsPair() {

		GeoIP current_geolocation;
		Pair<Double, Double> latitude_longtitude_pair = null;
		try {
			current_geolocation = getLCurrentLocationAsGeoIP();
			Double longitude = Double.parseDouble(current_geolocation.getLongitude());
			Double latitude = Double.parseDouble(current_geolocation.getLatitude());
			latitude_longtitude_pair = new Pair<Double, Double>(latitude, longitude);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Optional.of(latitude_longtitude_pair);
	}

	public static Node getNearestNode(Pair<Double, Double> location_pair) throws IOException {
		// Create the Jackson ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		OkHttpClient client = new OkHttpClient();
		Double current_latitude = location_pair.getKey();
		Double current_longitude = location_pair.getValue();
		int radius = 10;
		List<Node> nodes = null;

		while (nodes == null || nodes.size() < 1) {
			String overpassQuery = "[out:json][timeout:3600];" + "node(around:" + radius + ", " + current_latitude + ", "
					+ current_longitude + ");" + "out body;";
			Request request = new Request.Builder()
					.url("https://overpass-api.de/api/interpreter?data=" + URLEncoder.encode(overpassQuery, "UTF-8"))
					.build();

			try (Response response = client.newCall(request).execute()) {
				ResponseBody jsonData = response.body();
				if (jsonData != null) {
					String jsonString = jsonData.string();
					// Use the Jackson ObjectMapper to deserialize the JSON
					NodesResponseData responseData = mapper.readValue(jsonString, NodesResponseData.class);
					nodes = responseData.elements;
				}
			}

			radius += 10;
		}

		return nodes.get(0);
	}

	public static Node getNearestNode() throws IOException {
		// Create the Jackson ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		Pair<Double, Double> location_pair = getCurrentLocationAsPair().get();
		OkHttpClient client = new OkHttpClient();
		Double current_latitude = location_pair.getKey();
		Double current_longitude = location_pair.getValue();
		int radius = 10;
		List<Node> nodes = null;

		while (nodes == null || nodes.size() < 1) {
			String overpassQuery = "[out:json];" + "node(around:" + radius + ", " + current_latitude + ", "
					+ current_longitude + ");" + "out body;";
			Request request = new Request.Builder()
					.url("https://overpass-api.de/api/interpreter?data=" + URLEncoder.encode(overpassQuery, "UTF-8"))
					.build();

			try (Response response = client.newCall(request).execute()) {
				ResponseBody jsonData = response.body();
				if (jsonData != null) {
					String jsonString = jsonData.string();
					// Use the Jackson ObjectMapper to deserialize the JSON
					NodesResponseData responseData = mapper.readValue(jsonString, NodesResponseData.class);
					nodes = responseData.elements;
				}
			}

			radius += 10;
		}

		return nodes.get(0);
	}

	public Node getRandomNodeFromRandomWayWithinRadius(Map<Way, List<Node>> wayToNodesMap, double radius)
			throws IOException {
		Node currentLocation = getNearestNode();

		// Filter Ways where at least one Node is within the radius
		List<Way> waysWithinRadius = wayToNodesMap.keySet().stream().filter(
				way -> wayToNodesMap.get(way).stream().anyMatch(node -> haversine(currentLocation, node) <= radius))
				.collect(Collectors.toList());

		if (waysWithinRadius.isEmpty()) {
			return null;
		}

		// Get a random Way
		Random random = new Random();
		Way randomWay = waysWithinRadius.get(random.nextInt(waysWithinRadius.size()));

		// Get the nodes for this Way
		List<Node> nodes = wayToNodesMap.get(randomWay);

		// Filter Nodes within the radius
		List<Node> nodesWithinRadius = nodes.stream()
				.filter(node -> HaversineDistance.haversine(currentLocation, node) <= radius)
				.collect(Collectors.toList());

		if (nodesWithinRadius.isEmpty()) {
			return null;
		}

		// Return a random Node from this Way
		return nodesWithinRadius.get(random.nextInt(nodesWithinRadius.size()));
	}

	public static Optional<List<? extends Element>> getWaysAsWaysListWithinRadius(double radius) throws IOException {
		// Create the Jackson ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		OkHttpClient client = new OkHttpClient();
		Pair<Double, Double> location_pair = getCurrentLocationAsPair().get();
		Double current_latitude = location_pair.getKey();
		Double current_longitude = location_pair.getValue();
		String overpassQuery = "[out:json][timeout:3600];" + "way(around:" + radius + ", " + current_latitude + ", "
				+ current_longitude + ")"
					+ "[building != \"yes\"][highway != \"residential\"][highway != \"footway\"][area != \"yes\"];"
				+ "(._;>;);" + "out body;";
		System.out.println(overpassQuery);
		Request request = new Request.Builder()
				.url("https://overpass-api.de/api/interpreter?data=" + URLEncoder.encode(overpassQuery, "UTF-8"))
				.build();

		try (Response response = client.newCall(request).execute()) {
			ResponseBody jsonData = response.body();
			if (jsonData != null) {
				String jsonString = jsonData.string();
				// Use the Jackson ObjectMapper to deserialize the JSON
				WaysResponseData responseData = mapper.readValue(jsonString, WaysResponseData.class);
				if (responseData != null) {
					return Optional.of(responseData.getElements());
				}
			}
		}

		return Optional.empty();
	}

	public static Optional<List<? extends Element>> getWaysAsWaysListWithinRadius(final Node destinationNode,
			final int MAX_RADIUS, final int RADIUS_INCREMENT, int radius, double thresholdDistance,
			final double MAX_DISTANCE) throws IOException, DestinationNotFoundException, LongDistanceException {
		double distance = HaversineDistance.haversine(getNearestNode(), destinationNode);
		if (distance > MAX_DISTANCE) {
			throw new LongDistanceException("start node and destination node are too distant : " + distance + " KM");
		}

		// Create the Jackson ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerSubtypes(Node.class);
		mapper.registerSubtypes(Way.class);
		OkHttpClient client = new OkHttpClient();
		Pair<Double, Double> locationPair = getCurrentLocationAsPair().get();
		Double currentLatitude = locationPair.getKey();
		Double currentLongitude = locationPair.getValue();

		while (radius <= MAX_RADIUS) {
			String overpassQuery = "[out:json][timeout:3600];" + "way(around:" + radius + ", " + currentLatitude + ", "
					+ currentLongitude + ")"
						+ "[building != \"yes\"][highway != \"residential\"][highway != \"footway\"][area != \"yes\"];"
					+ "(._;>;);" + "out body;";
			System.out.println(overpassQuery);
			Request request = new Request.Builder()
					.url("https://overpass-api.de/api/interpreter?data=" + URLEncoder.encode(overpassQuery, "UTF-8"))
					.build();

			try (Response response = client.newCall(request).execute()) {
				ResponseBody jsonData = response.body();
				if (jsonData != null) {
					String jsonString = jsonData.string();
					// Use the Jackson ObjectMapper to deserialize the JSON
					WaysResponseData responseData = mapper.readValue(jsonString, WaysResponseData.class);
					if (responseData != null) {
						List<? extends Element> elements = responseData.getElements();

						// Check if any close node is found
						if (checkCloseNode(elements, destinationNode, thresholdDistance)) {

							return Optional.of(elements);
						}
					}
				}
			}

			// Increase the radius and continue the loop
			radius += RADIUS_INCREMENT;
		}

		throw new DestinationNotFoundException(
				"can't find the destination in a radius of " + MAX_RADIUS + " try to increase it");
	}

	public static Node getRandomNodeFromWayWithinRadius(double radius) throws IOException {
		// Get ways within the radius
		List<? extends Element> elements = getWaysAsWaysListWithinRadius(radius).get();

		// Filter only Way elements
		List<Way> ways = elements.stream().filter(element -> element instanceof Way).map(element -> (Way) element)
				.collect(Collectors.toList());

		if (ways.isEmpty()) {
			return null;
		}

		// Get a random way
		Random random = new Random();
		Way randomWay = ways.get(random.nextInt(ways.size()));

		// Get the nodes of this way
		List<Node> nodesOfWay = constructWayNodeMap(elements).get(randomWay);

		if (nodesOfWay.isEmpty()) {
			return null;
		}

		// Get a random node from the way
		Node randomNode = nodesOfWay.get(random.nextInt(nodesOfWay.size()));

		return randomNode;
	}

	private static boolean checkCloseNode(List<? extends Element> elements, Node destinationNode,
			double thresholdDistance) {
		// Iterate through the elements and check if any node is close to the
		// destination node
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node) element;
				if (isCloseToDestination(node, destinationNode, thresholdDistance)) {
					return true; // Found a close node, break the loop and return true
				}
			}
		}
		return false; // No close node found
	}

	public static Node findClosestNode(org.jgrapht.Graph<Node, DefaultWeightedEdge> graph, Node startingNode) {
		Node closestNode = null;
		double smallestDistance = Double.MAX_VALUE;

		for (Node node : graph.vertexSet()) {
			double distance = HaversineDistance.haversine(node, startingNode);
			if (distance < smallestDistance) {
				smallestDistance = distance;
				closestNode = node;
			}
		}

		return closestNode;
	}

	private static boolean isCloseToDestination(Node node, Node destinationNode, double thresholdDistance) {
		// Use the haversine method to calculate the distance between the current node
		// and the destination node
		double distance = HaversineDistance.haversine(node, destinationNode);

		// Check if the distance is within the threshold
		return distance <= thresholdDistance;

	}

	public static Map<Way, List<Node>> constructWayNodeMap(List<? extends Element> elements) {
		// Create a map for quick lookup of nodes by their id
		Map<Long, Node> nodeMap = elements.stream().filter(e -> e instanceof Node).map(e -> (Node) e)
				.collect(Collectors.toMap(Node::getId, node -> node));

		// Construct Map<Way, List<Node>> from the list of elements
		Map<Way, List<Node>> wayNodeMap = elements.stream().filter(e -> e instanceof Way).map(e -> (Way) e)
				.collect(Collectors.toMap(way -> way, way -> way.getNodes().stream().map(nodeId -> nodeMap.get(nodeId))
						.collect(Collectors.toList())));

		return wayNodeMap;
	}
	
	public static Graph<Node, DefaultWeightedEdge> buildGraphWithExtremities(Map<Way, List<Node>> wayNodeMap) {
	    Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

	    for (Map.Entry<Way, List<Node>> entry : wayNodeMap.entrySet()) {
	        List<Node> nodeList = entry.getValue();
			if (nodeList.size() >= 2) {
				Node startNode = nodeList.get(0);
				Node endNode = nodeList.get(nodeList.size() - 1);
				if (!startNode.equals(endNode)) {
					 graph.addVertex(startNode);
			            graph.addVertex(endNode);
					DefaultWeightedEdge edge = graph.addEdge(startNode, endNode);

					if (edge != null) {
						double distance = haversine(startNode, endNode); // calculate distance between startNode and
																			// endNode
						graph.setEdgeWeight(edge, distance);
	            }	
				}
	           
	            
	            
	        }
	    }
	    return graph;
	}

	public static Map<Node, List<Way>> findIntersectionNodes(Map<Way, List<Node>> wayToNodesMap) {
		// Create a map to hold the number of occurrences of each node
		Map<Node, Integer> nodeCounts = new HashMap<>();
		Map<Node, List<Way>> nodeToWayMap = new HashMap<>();

		// Iterate over each way and its nodes
		for (Map.Entry<Way, List<Node>> entry : wayToNodesMap.entrySet()) {
			Way way = entry.getKey();
			List<Node> nodes = entry.getValue();

			// Iterate over each node in the way
			for (Node node : nodes) {
				// Increment the count for this node
				nodeCounts.put(node, nodeCounts.getOrDefault(node, 0) + 1);

				// Add the way to the list of ways for this node
				nodeToWayMap.computeIfAbsent(node, k -> new ArrayList<>()).add(way);
			}
		}

		// Filter out the nodes that are not intersection nodes
		nodeToWayMap.entrySet().removeIf(entry -> nodeCounts.get(entry.getKey()) <= 1);

		// Now nodeToWayMap contains only intersection nodes as keys,
		// and for each intersection node, nodeToWayMap.get(node) will give you the ways
		// that intersect at that node.
		return nodeToWayMap;
	}

	public static org.jgrapht.Graph<Node, DefaultWeightedEdge> buildGraph(Map<Node, List<Way>> intersectionNodes,
			Map<Way, List<Node>> wayToNodesMap) {
		// Create a new graph
		org.jgrapht.Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		// Add the nodes to the graph
		for (Node node : intersectionNodes.keySet()) {
			graph.addVertex(node);
		}

		// For each way, add an edge between each pair of nodes in the way
		for (Map.Entry<Way, List<Node>> entry : wayToNodesMap.entrySet()) {
			Way way = entry.getKey();
			List<Node> nodes = entry.getValue();

			// If the way is a roundabout, handle it differently
			if (way.getTags() != null && way.getTags().containsKey("junction")
					&& way.getTags().get("junction").equals("roundabout")) {
				// Add edges between each pair of consecutive nodes, and between the first and
				// last nodes
				for (int i = 0; i < nodes.size(); i++) {
					Node node1 = nodes.get(i);
					Node node2 = nodes.get((i + 1) % nodes.size()); // Get the next node, or the first node if i+1 is
					if (!node1.equals(node2)) {
						graph.addVertex(node1);
						graph.addVertex(node2);
						DefaultWeightedEdge edge = graph.addEdge(node1, node2);
						if (edge != null) {
							double distance = HaversineDistance.haversine(node1, node2);
							graph.setEdgeWeight(edge, distance);
						}
					}

				}
			} else {
				// Add edges between each pair of consecutive nodes
				for (int i = 0; i < nodes.size() - 1; i++) {
					Node node1 = nodes.get(i);
					Node node2 = nodes.get(i + 1);

					if (intersectionNodes.containsKey(node1) && intersectionNodes.containsKey(node2)) {
						DefaultWeightedEdge edge = graph.addEdge(node1, node2);
						if (edge != null) {
							double distance = HaversineDistance.haversine(node1, node2);
							graph.setEdgeWeight(edge, distance);
						}
					}
				}
			}
		}

		return graph;
	}

	public static void generateQueryForNodes(org.jgrapht.Graph<Node, DefaultWeightedEdge> graph) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("[out:json];\n");
		queryBuilder.append("(");

		for (Node node : graph.vertexSet()) {
			queryBuilder.append("node(").append(node.getId()).append(");");
		}

		queryBuilder.append(");\n");
		queryBuilder.append("out body;");

		String overpassQuery = queryBuilder.toString();
		System.out.println(overpassQuery);
	}

	public static boolean isGraphConnected(org.jgrapht.Graph<Node, DefaultWeightedEdge> graph) {
		ConnectivityInspector<Node, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);
		return inspector.isConnected();
	}

	public static Node getNearestIntersectionNode(Node givenNode, Map<Node, List<Way>> intersectionNodes) {
		Node nearestIntersectionNode = null;
		double minDistance = Double.MAX_VALUE;

		for (Node intersectionNode : intersectionNodes.keySet()) {
			double distance = HaversineDistance.haversine(givenNode, intersectionNode);
			if (distance < minDistance) {
				minDistance = distance;
				nearestIntersectionNode = intersectionNode;
			}
		}

		return nearestIntersectionNode;
	}
	public static void printIntersectionNodes(Map<Node, List<Way>> intersectionNodes) {
	    for (Map.Entry<Node, List<Way>> entry : intersectionNodes.entrySet()) {
	        Node node = entry.getKey();
	        List<Way> ways = entry.getValue();
	        
	        System.out.println("Node: " + node);

	        System.out.println();
	    }
	}
	
	public static org.graphstream.graph.Graph convertJGraphTToGraphStream(org.jgrapht.Graph<Node, DefaultWeightedEdge> jgraph) {
		org.graphstream.graph.Graph graphStream = new SingleGraph("Converted Graph");

	    for (Node node : jgraph.vertexSet()) {
	        graphStream.addNode(String.valueOf(node.getId()));
	    }

	    for (DefaultWeightedEdge edge : jgraph.edgeSet()) {
	        Node sourceNode = jgraph.getEdgeSource(edge);
	        Node targetNode = jgraph.getEdgeTarget(edge);
	        double weight = jgraph.getEdgeWeight(edge);

	        Edge e = graphStream.addEdge(String.valueOf(sourceNode.getId()) + "-" + targetNode.getId(), String.valueOf(sourceNode.getId()), String.valueOf(targetNode.getId()), true);
	    }
	    
	    return graphStream;
	}


	public static void main(String[] args) {
		try {
			Node startNode = new Node(33.5949145, -7.6235462); // Define your start node
			Node endNode = new Node(33.5933676, -7.6201223); // Define your end node

			List<? extends Element> elements = GeoLocationService
					.getWaysAsWaysListWithinRadius(endNode, 10000, 10, 10, 0, 10).get();

			Map<Way, List<Node>> wayToNodesMap = constructWayNodeMap((List<Element>) elements);

			// Find the intersection nodes
			Map<Node, List<Way>> intersectionNodes = findIntersectionNodes(wayToNodesMap);
			// Replace your start and end nodes with the nearest intersection nodes
			startNode = getNearestIntersectionNode(startNode, intersectionNodes);
			endNode = getNearestIntersectionNode(endNode, intersectionNodes);
			org.jgrapht.Graph<Node, DefaultWeightedEdge> graph2 = buildGraphWithExtremities(wayToNodesMap);

			org.jgrapht.Graph<Node, DefaultWeightedEdge> graph = buildGraph(intersectionNodes, wayToNodesMap);
			org.graphstream.graph.Graph conGraph = convertJGraphTToGraphStream(graph2);
			System.setProperty("org.graphstream.ui", "swing");
			conGraph.display();
			for(DefaultWeightedEdge e : graph.edgeSet()){
			    System.out.println(graph.getEdgeSource(e) + " --> " + graph.getEdgeTarget(e));
			}
			DijkstraShortestPath<Node, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
			GraphPath<Node, DefaultWeightedEdge> path = dijkstraShortestPath.getPath(startNode, endNode);
			
			if (isGraphConnected(graph)) {
				System.out.println("The graph is connected.");
			} else {
				System.out.println("The graph is not connected.");
			}

			// If there's a path from the start node to the end node, print it out
			if (path != null) {
				List<Node> shortestPath = path.getVertexList(); // This gives you the nodes in the shortest path
				for (Node node : shortestPath) {
					System.out.println(node);
				}
			} else {
				System.out.println("No path found from the start node to the end node.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
