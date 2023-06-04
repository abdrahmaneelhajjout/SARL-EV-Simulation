package SARL.agents.geolocation.overpass;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeInfo(
	    use=JsonTypeInfo.Id.NAME,
	    include=JsonTypeInfo.As.PROPERTY,
	    property="type")
@JsonTypeName("node")
public class Node extends Element {

	private double lat;
	private double lon;

	public Node(String type, long id, double lat, double lon,  Map<String, String> tags) {
		super(type, id, tags);
		this.lat = lat;
		this.lon = lon;
	}

	
	public Node() {
		super();
	}

	public Node(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		return true;
	}


	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "Node [lat=" + lat + ", lon=" + lon + ", type=" + type + ", id=" + id + ", tags=" + tags + "]";
	}
	
	
	 public SARL.agents.geolocation.mapbox.Node convertNode() {
	        double latitude = this.getLat();
	        double longitude = this.getLon();
	        return new SARL.agents.geolocation.mapbox.Node(latitude, longitude);
	    }

}
