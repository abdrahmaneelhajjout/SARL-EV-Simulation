package SARL.agents.utils.geolocation.overpass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WaysResponseData<T extends Element> {
	
    private List<Element> elements;

	public WaysResponseData(List<Element> elements) {
		super();
		this.elements = elements;
	}
	
	public WaysResponseData() {
		this.elements = elements;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	
	
	
    
}
