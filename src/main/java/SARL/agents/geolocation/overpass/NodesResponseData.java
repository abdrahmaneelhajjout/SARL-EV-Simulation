package SARL.agents.geolocation.overpass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodesResponseData {
    public List<Node> elements;

}
