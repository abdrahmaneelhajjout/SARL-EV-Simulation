package SARL.agents.geolocation.mapbox;

import java.util.ArrayList;
import java.util.List;

import SARL.agents.geolocation.overpass.HaversineDistance;

public class NodeUtils {
	public static List<Node> getNodesBetween(Node startNode, Node endNode, double distanceBetweenNodes) {
		List<Node> nodesBetween = new ArrayList<>();

		// Calculate the total distance between the start and end nodes
		double totalDistance = HaversineDistance.haversine(startNode, endNode)*100;

		// Calculate the number of intermediate nodes based on the distance between each
		// node
		int numIntermediateNodes = (int) (totalDistance / distanceBetweenNodes);
		System.out.println(numIntermediateNodes);
		// Calculate the latitude and longitude differences between the start and end
		// nodes
		double latDiff = endNode.getLatitude() - startNode.getLatitude();
		double lonDiff = endNode.getLongitude() - startNode.getLongitude();

		// Calculate the latitude and longitude increments for each intermediate node
		double latIncrement = latDiff / (numIntermediateNodes + 1);
		double lonIncrement = lonDiff / (numIntermediateNodes + 1);

		// Generate the intermediate nodes
		double currentLat = startNode.getLatitude();
		double currentLon = startNode.getLongitude();
		for (int i = 0; i < numIntermediateNodes; i++) {
			currentLat += latIncrement;
			currentLon += lonIncrement;
			nodesBetween.add(new Node(currentLat, currentLon));
		}

		return nodesBetween;
	}


	public static void printOverpassQuery(List<Node> nodes) {
		for (Node node : nodes) {
			String qlStatement = String.format("node(around:1, %f, %f);", node.getLatitude(), node.getLongitude());
			System.out.println(qlStatement);
		}
	}

	public static void main(String[] args) {
		Node startNode = new Node(33.5895672, -7.6254343);
		Node endNode = new Node(33.5891336, -7.6243139);
		printOverpassQuery(getNodesBetween(startNode, endNode, 0.1));
	}

}
