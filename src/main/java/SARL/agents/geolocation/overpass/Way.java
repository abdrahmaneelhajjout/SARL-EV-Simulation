package SARL.agents.geolocation.overpass;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(
	    use=JsonTypeInfo.Id.NAME,
	    include=JsonTypeInfo.As.PROPERTY,
	    property="type")
@JsonTypeName("way")
public class Way extends Element{

	private List<Long> nodes;

	public Way(String type, long id, List<Long> nodes , Map<String, String> tags) {
		super(type, id, tags);
		this.nodes = nodes;
	}

	public Way() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public List<Long> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return "Way [nodes=" + nodes + ", type=" + type + ", id=" + id + ", tags=" + tags + "]";
	}
	
	

}
