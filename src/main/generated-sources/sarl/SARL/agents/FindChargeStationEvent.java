package SARL.agents;

import SARL.agents.utils.geolocation.mapbox.Node;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.12")
@SarlElementType(15)
@SuppressWarnings("all")
public class FindChargeStationEvent extends Event {
  public final long currentBatteryLevel;
  
  public final Node currentLocation;
  
  public final Node destination;
  
  public FindChargeStationEvent(final Node currentLocation, final Node destination, final long currentBatteryLevel) {
    this.currentLocation = currentLocation;
    this.destination = destination;
    this.currentBatteryLevel = currentBatteryLevel;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FindChargeStationEvent other = (FindChargeStationEvent) obj;
    if (other.currentBatteryLevel != this.currentBatteryLevel)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.currentBatteryLevel);
    return result;
  }
  
  /**
   * Returns a String representation of the FindChargeStationEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("currentBatteryLevel", this.currentBatteryLevel);
    builder.add("currentLocation", this.currentLocation);
    builder.add("destination", this.destination);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 2300769749L;
}
