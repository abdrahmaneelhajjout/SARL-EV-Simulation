package SARL.agents.geolocation.mapbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import SARL.agents.geolocation.overpass.HaversineDistance;

public class NodeUtils {
	public static List<Node> getNodesBetween(Node startNode, Node endNode, double distanceBetweenNodes) {
		List<Node> nodesBetween = new ArrayList<>();
		nodesBetween.add(startNode); // startNode is always included

		double totalDistance = HaversineDistance.haversine(startNode, endNode) * 100; // *100 to KM to Meters

		if (totalDistance > distanceBetweenNodes) {
			// Calculate the number of nodes to be added
			int numNodes = (int) (totalDistance / distanceBetweenNodes);

			// Calculate the lat, long differences between each node
			double dLat = (endNode.getLatitude() - startNode.getLatitude()) / numNodes;
			double dLong = (endNode.getLongitude() - startNode.getLongitude()) / numNodes;

			for (int i = 1; i < numNodes; i++) {
				double newNodeLat = startNode.getLatitude() + i * dLat;
				double newNodeLong = startNode.getLongitude() + i * dLong;
				Node newNode = new Node(newNodeLat, newNodeLong);
				nodesBetween.add(newNode);
			}
		}

		return nodesBetween;
	}
	
	public static <T> List<T> randomSubList(List<T> list, int newSize) {
	    list = new ArrayList<>(list);
	    Collections.shuffle(list);
	    return list.subList(0, newSize);
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
		printOverpassQuery(getNodesBetween(startNode, endNode, 10));
	}

}
