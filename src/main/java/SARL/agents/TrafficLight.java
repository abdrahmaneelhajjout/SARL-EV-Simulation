package SARL.agents;

import java.util.NoSuchElementException;

public class TrafficLight {
    public TrafficLightStatus lightStatus;

    public TrafficLight(TrafficLightStatus lightStatus) {
        this.lightStatus = lightStatus;
    }

    public TrafficLightStatus getLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(TrafficLightStatus lightStatus) {
        this.lightStatus = lightStatus;
    }



 
    public TrafficLight getNext() {
        switch(lightStatus) {
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
}
