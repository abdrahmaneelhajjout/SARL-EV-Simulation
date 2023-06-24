package SARL.agents;

import SARL.agents.utils.geolocation.mapbox.Node;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import javafx.util.Pair;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.12")
@SarlElementType(15)
@SuppressWarnings("all")
public class chargeStationFoundEvent extends Event {
  public final Node closestStation;
  
  public final Pair<Address, String> stationAdressAndNamePair;
  
  public chargeStationFoundEvent(final Node closestStation, final Pair<Address, String> stationAdressAndNamePair) {
    this.closestStation = closestStation;
    this.stationAdressAndNamePair = stationAdressAndNamePair;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  /**
   * Returns a String representation of the chargeStationFoundEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("closestStation", this.closestStation);
    builder.add("stationAdressAndNamePair", this.stationAdressAndNamePair);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 4427037846L;
}
