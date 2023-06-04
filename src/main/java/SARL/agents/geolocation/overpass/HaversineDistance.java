
package SARL.agents.geolocation.overpass;

public class HaversineDistance {

	public static double haversine(double lat1, double lon1, double lat2, double lon2) {
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}

	public static double haversine(Node node1, Node node2) {
		double lat1, lat2, lon1, lon2;
		lat1 = node1.getLat();
		lat2 = node2.getLat();
		lon1 = node1.getLon();
		lon2 = node2.getLon();
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}

	public static double haversine(SARL.agents.geolocation.mapbox.Node node1,
			SARL.agents.geolocation.mapbox.Node node2) {
		double lat1, lat2, lon1, lon2;
		lat1 = node1.getLatitude();
		lat2 = node2.getLatitude();
		lon1 = node1.getLongitude();
		lon2 = node2.getLongitude();
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}

	// Driver Code
	public static void main(String[] args) {
		double lat1 = 33.001331;
		double lon1 = -7.6188843;
		double lat2 = 33.0011429;
		double lon2 = -7.6188112;
		System.out.println(haversine(new Node("node", 1, 33.001331, -7.6188843, null),
				new Node("node", 1, 33.0011429, -7.6188112, null)) + " K.M.");
	}

}