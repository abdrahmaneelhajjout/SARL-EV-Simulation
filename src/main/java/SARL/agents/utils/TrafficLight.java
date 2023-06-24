package SARL.agents.utils;

import java.util.NoSuchElementException;
import java.util.Random;

public class TrafficLight {
	public TrafficLightStatus lightStatus;

	public TrafficLight(TrafficLightStatus lightStatus) {
		this.lightStatus = lightStatus;
	}
	
	public TrafficLight() {
		this.lightStatus = getRandomTrafficStatus();
	}

	public TrafficLightStatus getLightStatus() {
		return lightStatus;
	}

	public void setLightStatus(TrafficLightStatus lightStatus) {
		this.lightStatus = lightStatus;
	}

	public TrafficLight getNext() {
		switch (lightStatus) {
		case RED:
			setLightStatus(TrafficLightStatus.GREEN);
			return this;
		case GREEN:
			setLightStatus(TrafficLightStatus.ORANGE);
			return this;
		case ORANGE:
			setLightStatus(TrafficLightStatus.RED);
			return this;
		default:
			throw new NoSuchElementException("no trffic light");
		}
	}
	
    public static TrafficLightStatus getRandomTrafficStatus() {
    	TrafficLightStatus[] status = TrafficLightStatus.values();
        int rnd = new Random().nextInt(status.length);
        return status[rnd] ;
    }
}
