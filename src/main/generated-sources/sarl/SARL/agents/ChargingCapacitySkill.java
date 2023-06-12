package SARL.agents;

import SARL.agents.FindChargeStationEvent;
import SARL.agents.VehicleAgent;
import SARL.agents.capacities.ChargingCapacity;
import SARL.agents.capacities.GeoLocationCapacity;
import SARL.agents.geolocation.mapbox.Node;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(22)
@SuppressWarnings("all")
public class ChargingCapacitySkill extends Skill implements ChargingCapacity {
  private VehicleAgent owner;
  
  public void install() {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        Agent _owner = ChargingCapacitySkill.this.getOwner();
        this.$$result = (_owner != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    Agent _owner = this.getOwner();
    this.owner = ((VehicleAgent) _owner);
  }
  
  public int getBatteryLevel() {
    return ((this.owner.getBatteryLevel()) == null ? 0 : (this.owner.getBatteryLevel()).intValue());
  }
  
  public void goCharge() {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    Agent _owner = this.getOwner();
    Node _currentLocation = ((VehicleAgent) _owner).getCurrentLocation();
    Agent _owner_1 = this.getOwner();
    Node _destinationNode = ((VehicleAgent) _owner_1).getDestinationNode();
    Integer _batteryLevel = this.owner.getBatteryLevel();
    FindChargeStationEvent _findChargeStationEvent = new FindChargeStationEvent(_currentLocation, _destinationNode, ((_batteryLevel) == null ? 0 : (_batteryLevel).intValue()));
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_findChargeStationEvent);
    Node closestChagreStationNode = null;
    while (Objects.equal((closestChagreStationNode = ((VehicleAgent) this.getOwner()).getChargeStationNode()), null)) {
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _agentName = this.owner.getAgentName();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((_agentName + " - ") + closestChagreStationNode));
    GeoLocationCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY$CALLER();
    Agent _owner_2 = this.getOwner();
    this.owner.setFullPath(_$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY$CALLER.getRouteToDestination(((VehicleAgent) _owner_2).getCurrentLocation(), closestChagreStationNode, 
      this.owner.getDestinationNode()));
  }
  
  public int getBatteryCapacity() {
    Agent _owner = this.getOwner();
    return ((((VehicleAgent) _owner).getBatteryCapacity()) == null ? 0 : (((VehicleAgent) _owner).getBatteryCapacity()).intValue());
  }
  
  public int getBatteryChargeCapacity() {
    Agent _owner = this.getOwner();
    return ((((VehicleAgent) _owner).getBatteryChargeCapacity()) == null ? 0 : (((VehicleAgent) _owner).getBatteryChargeCapacity()).intValue());
  }
  
  public void updateBatteryLevel(final int amount) {
    Agent _owner = this.getOwner();
    Integer _batteryLevel = ((VehicleAgent) _owner).getBatteryLevel();
    int newLevel = (((_batteryLevel) == null ? 0 : (_batteryLevel).intValue()) + amount);
    Agent _owner_1 = this.getOwner();
    Integer _batteryCapacity = ((VehicleAgent) _owner_1).getBatteryCapacity();
    if ((newLevel > _batteryCapacity.doubleValue())) {
      Agent _owner_2 = this.getOwner();
      Agent _owner_3 = this.getOwner();
      ((VehicleAgent) _owner_2).setBatteryLevel(((VehicleAgent) _owner_3).getBatteryCapacity());
    } else {
      Agent _owner_4 = this.getOwner();
      ((VehicleAgent) _owner_4).setBatteryLevel(Integer.valueOf(newLevel));
    }
  }
  
  public void setBatteryLevel(final int amount) {
    try {
      if (((amount >= 0) && (amount <= ((VehicleAgent) this.getOwner()).getBatteryCapacity().doubleValue()))) {
        Agent _owner = this.getOwner();
        ((VehicleAgent) _owner).setBatteryLevel(Integer.valueOf(amount));
      } else {
        throw new Exception("Invalid battery level. Battery level should be between 0 and battery capacity.");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Extension
  @ImportedCapacityFeature(GeoLocationCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY;
  
  @SyntheticMember
  @Pure
  private GeoLocationCapacity $CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY == null || this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY.get() == null) {
      this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY = $getSkill(GeoLocationCapacity.class);
    }
    return $castSkill(GeoLocationCapacity.class, this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
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
  
  @SyntheticMember
  public ChargingCapacitySkill() {
    super();
  }
  
  @SyntheticMember
  public ChargingCapacitySkill(final Agent agent) {
    super(agent);
  }
}
