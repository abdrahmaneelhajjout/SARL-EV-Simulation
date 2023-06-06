package SARL.agents;

public enum TrafficLightStatus {
	RED,
	GREEN,
	ORANGE;
	 private long duration;

	 TrafficLightStatus() {
	        // Default duration for each status
	        this.duration = 5000;
	    }

	    public long getDuration() {
	        return duration;
	    }

	    public void setDuration(long duration) {
	        this.duration = duration;
	    }

}
