package SARL.agents.geolocation.overpass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodesResponseData {
    private List<Node> elements;

	public List<Node> getElements() {
		return elements;
	}

	public void setElements(List<Node> elements) {
		this.elements = elements;
	}

}
