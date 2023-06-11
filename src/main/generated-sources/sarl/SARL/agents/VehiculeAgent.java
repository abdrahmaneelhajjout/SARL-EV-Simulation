package SARL.agents;

import SARL.agents.FullPathUpdateEvent;
import SARL.agents.VehiculeAgentBehavior;
import SARL.agents.VehiculeStatus;
import SARL.agents.capacities.GeoLocationCapacity;
import SARL.agents.geolocation.mapbox.Node;
import SARL.agents.geolocation.mapbox.NodeUtils;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.Behaviors;
import io.sarl.core.ContextJoined;
import io.sarl.core.ContextLeft;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.core.MemberJoined;
import io.sarl.core.MemberLeft;
import io.sarl.core.ParticipantJoined;
import io.sarl.core.ParticipantLeft;
import io.sarl.core.SpaceCreated;
import io.sarl.core.SpaceDestroyed;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Event;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javafx.util.Pair;
import javax.inject.Inject;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author ELHAJJOUT
 */
@SuppressWarnings("unused_private_member")
@SarlSpecification("0.12")
@SarlElementType(19)
public class VehiculeAgent extends Agent {
  @Accessors
  private String agentName;
  
  @Accessors
  private Node startNode;
  
  @Accessors
  private List<Node> fullPath;
  
  @Accessors
  private List<Node> subPath;
  
  @Accessors
  private Node currentLocation;
  
  @Accessors
  private Node destinationNode;
  
  @Accessors
  private double speedKmPerHour;
  
  @Accessors
  private VehiculeStatus status;
  
  @Accessors
  private Integer batteryLevel;
  
  @Accessors
  private Integer batteryCapacity = Integer.valueOf(2000);
  
  @Accessors
  private Integer batteryChargeCapacity = Integer.valueOf(20);
  
  @Accessors
  private Node chargeStationNode;
  
  private Pair<Node, Node> location_pair;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(occurrence.parameters[0]);
    Object _get = occurrence.parameters[0];
    this.agentName = (_get == null ? null : _get.toString());
    Object _get_1 = occurrence.parameters[1];
    this.location_pair = ((Pair<Node, Node>) _get_1);
    Object _get_2 = occurrence.parameters[2];
    this.speedKmPerHour = ((((Double) _get_2)) == null ? 0 : (((Double) _get_2)).doubleValue());
    this.startNode = this.location_pair.getKey();
    this.currentLocation = this.location_pair.getKey();
    this.destinationNode = this.location_pair.getValue();
    GeoLocationCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY$CALLER();
    this.fullPath = _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_GEOLOCATIONCAPACITY$CALLER.getRouteToDestination(this.startNode, this.destinationNode);
    this.subPath = NodeUtils.getNodesBetween(this.fullPath.get(0), this.fullPath.get(1), 1000);
    VehiculeAgentBehavior agentBehavior = new VehiculeAgentBehavior(this);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.setLoggingName(this.agentName);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(agentBehavior);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info((("----------------- " + this.agentName) + " ----------------------"));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info("The agent was started.");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4.info(("from " + this.startNode));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5.info(("to " + this.destinationNode));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6.info("------------------------------------------------------------");
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    FullPathUpdateEvent _fullPathUpdateEvent = new FullPathUpdateEvent(this.agentName, this.fullPath);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_fullPathUpdateEvent);
  }
  
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The agent was stopped.");
  }
  
  private void $behaviorUnit$AgentSpawned$2(final AgentSpawned occurrence) {
  }
  
  private void $behaviorUnit$AgentKilled$3(final AgentKilled occurrence) {
  }
  
  private void $behaviorUnit$ContextJoined$4(final ContextJoined occurrence) {
  }
  
  private void $behaviorUnit$ContextLeft$5(final ContextLeft occurrence) {
  }
  
  private void $behaviorUnit$MemberJoined$6(final MemberJoined occurrence) {
  }
  
  private void $behaviorUnit$MemberLeft$7(final MemberLeft occurrence) {
  }
  
  private void $behaviorUnit$MemberLeft$8(final MemberLeft occurrence) {
  }
  
  private void $behaviorUnit$SpaceCreated$9(final SpaceCreated occurrence) {
  }
  
  private void $behaviorUnit$SpaceDestroyed$10(final SpaceDestroyed occurrence) {
  }
  
  private void $behaviorUnit$ParticipantJoined$11(final ParticipantJoined occurrence) {
  }
  
  private void $behaviorUnit$ParticipantLeft$12(final ParticipantLeft occurrence) {
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
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextLeft(final ContextLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextLeft$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SpaceDestroyed(final SpaceDestroyed occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SpaceDestroyed$10(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextJoined(final ContextJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextJoined$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberLeft(final MemberLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$7(occurrence));
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$8(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SpaceCreated(final SpaceCreated occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SpaceCreated$9(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentSpawned(final AgentSpawned occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentSpawned$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ParticipantJoined(final ParticipantJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ParticipantJoined$11(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ParticipantLeft(final ParticipantLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ParticipantLeft$12(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentKilled(final AgentKilled occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentKilled$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberJoined(final MemberJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberJoined$6(occurrence));
  }
  
  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(AgentKilled.class);
    toBeFilled.add(AgentSpawned.class);
    toBeFilled.add(ContextJoined.class);
    toBeFilled.add(ContextLeft.class);
    toBeFilled.add(Destroy.class);
    toBeFilled.add(Initialize.class);
    toBeFilled.add(MemberJoined.class);
    toBeFilled.add(MemberLeft.class);
    toBeFilled.add(ParticipantJoined.class);
    toBeFilled.add(ParticipantLeft.class);
    toBeFilled.add(SpaceCreated.class);
    toBeFilled.add(SpaceDestroyed.class);
  }
  
  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (AgentKilled.class.isAssignableFrom(event)) {
      return true;
    }
    if (AgentSpawned.class.isAssignableFrom(event)) {
      return true;
    }
    if (ContextJoined.class.isAssignableFrom(event)) {
      return true;
    }
    if (ContextLeft.class.isAssignableFrom(event)) {
      return true;
    }
    if (Destroy.class.isAssignableFrom(event)) {
      return true;
    }
    if (Initialize.class.isAssignableFrom(event)) {
      return true;
    }
    if (MemberJoined.class.isAssignableFrom(event)) {
      return true;
    }
    if (MemberLeft.class.isAssignableFrom(event)) {
      return true;
    }
    if (ParticipantJoined.class.isAssignableFrom(event)) {
      return true;
    }
    if (ParticipantLeft.class.isAssignableFrom(event)) {
      return true;
    }
    if (SpaceCreated.class.isAssignableFrom(event)) {
      return true;
    }
    if (SpaceDestroyed.class.isAssignableFrom(event)) {
      return true;
    }
    return false;
  }
  
  @SyntheticMember
  @Override
  public void $evaluateBehaviorGuards(final Object event, final Collection<Runnable> callbacks) {
    super.$evaluateBehaviorGuards(event, callbacks);
    if (event instanceof AgentKilled) {
      final AgentKilled occurrence = (AgentKilled) event;
      $guardEvaluator$AgentKilled(occurrence, callbacks);
    }
    if (event instanceof AgentSpawned) {
      final AgentSpawned occurrence = (AgentSpawned) event;
      $guardEvaluator$AgentSpawned(occurrence, callbacks);
    }
    if (event instanceof ContextJoined) {
      final ContextJoined occurrence = (ContextJoined) event;
      $guardEvaluator$ContextJoined(occurrence, callbacks);
    }
    if (event instanceof ContextLeft) {
      final ContextLeft occurrence = (ContextLeft) event;
      $guardEvaluator$ContextLeft(occurrence, callbacks);
    }
    if (event instanceof Destroy) {
      final Destroy occurrence = (Destroy) event;
      $guardEvaluator$Destroy(occurrence, callbacks);
    }
    if (event instanceof Initialize) {
      final Initialize occurrence = (Initialize) event;
      $guardEvaluator$Initialize(occurrence, callbacks);
    }
    if (event instanceof MemberJoined) {
      final MemberJoined occurrence = (MemberJoined) event;
      $guardEvaluator$MemberJoined(occurrence, callbacks);
    }
    if (event instanceof MemberLeft) {
      final MemberLeft occurrence = (MemberLeft) event;
      $guardEvaluator$MemberLeft(occurrence, callbacks);
    }
    if (event instanceof ParticipantJoined) {
      final ParticipantJoined occurrence = (ParticipantJoined) event;
      $guardEvaluator$ParticipantJoined(occurrence, callbacks);
    }
    if (event instanceof ParticipantLeft) {
      final ParticipantLeft occurrence = (ParticipantLeft) event;
      $guardEvaluator$ParticipantLeft(occurrence, callbacks);
    }
    if (event instanceof SpaceCreated) {
      final SpaceCreated occurrence = (SpaceCreated) event;
      $guardEvaluator$SpaceCreated(occurrence, callbacks);
    }
    if (event instanceof SpaceDestroyed) {
      final SpaceDestroyed occurrence = (SpaceDestroyed) event;
      $guardEvaluator$SpaceDestroyed(occurrence, callbacks);
    }
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
    VehiculeAgent other = (VehiculeAgent) obj;
    if (!Objects.equals(this.agentName, other.agentName))
      return false;
    if (Double.doubleToLongBits(other.speedKmPerHour) != Double.doubleToLongBits(this.speedKmPerHour))
      return false;
    if (other.batteryLevel == null) {
      if (this.batteryLevel != null)
        return false;
    } else if (this.batteryLevel == null)
      return false;
    if (other.batteryLevel != null && other.batteryLevel.intValue() != this.batteryLevel.intValue())
      return false;
    if (other.batteryCapacity == null) {
      if (this.batteryCapacity != null)
        return false;
    } else if (this.batteryCapacity == null)
      return false;
    if (other.batteryCapacity != null && other.batteryCapacity.intValue() != this.batteryCapacity.intValue())
      return false;
    if (other.batteryChargeCapacity == null) {
      if (this.batteryChargeCapacity != null)
        return false;
    } else if (this.batteryChargeCapacity == null)
      return false;
    if (other.batteryChargeCapacity != null && other.batteryChargeCapacity.intValue() != this.batteryChargeCapacity.intValue())
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.agentName);
    result = prime * result + Double.hashCode(this.speedKmPerHour);
    result = prime * result + Objects.hashCode(this.batteryLevel);
    result = prime * result + Objects.hashCode(this.batteryCapacity);
    result = prime * result + Objects.hashCode(this.batteryChargeCapacity);
    return result;
  }
  
  @SyntheticMember
  public VehiculeAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public VehiculeAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
  
  @Pure
  protected String getAgentName() {
    return this.agentName;
  }
  
  protected void setAgentName(final String agentName) {
    this.agentName = agentName;
  }
  
  @Pure
  protected Node getStartNode() {
    return this.startNode;
  }
  
  protected void setStartNode(final Node startNode) {
    this.startNode = startNode;
  }
  
  @Pure
  protected List<Node> getFullPath() {
    return this.fullPath;
  }
  
  protected void setFullPath(final List<Node> fullPath) {
    this.fullPath = fullPath;
  }
  
  @Pure
  protected List<Node> getSubPath() {
    return this.subPath;
  }
  
  protected void setSubPath(final List<Node> subPath) {
    this.subPath = subPath;
  }
  
  @Pure
  protected Node getCurrentLocation() {
    return this.currentLocation;
  }
  
  protected void setCurrentLocation(final Node currentLocation) {
    this.currentLocation = currentLocation;
  }
  
  @Pure
  protected Node getDestinationNode() {
    return this.destinationNode;
  }
  
  protected void setDestinationNode(final Node destinationNode) {
    this.destinationNode = destinationNode;
  }
  
  @Pure
  protected double getSpeedKmPerHour() {
    return this.speedKmPerHour;
  }
  
  protected void setSpeedKmPerHour(final double speedKmPerHour) {
    this.speedKmPerHour = speedKmPerHour;
  }
  
  @Pure
  protected VehiculeStatus getStatus() {
    return this.status;
  }
  
  protected void setStatus(final VehiculeStatus status) {
    this.status = status;
  }
  
  @Pure
  protected Integer getBatteryLevel() {
    return this.batteryLevel;
  }
  
  protected void setBatteryLevel(final Integer batteryLevel) {
    this.batteryLevel = batteryLevel;
  }
  
  @Pure
  protected Integer getBatteryCapacity() {
    return this.batteryCapacity;
  }
  
  protected void setBatteryCapacity(final Integer batteryCapacity) {
    this.batteryCapacity = batteryCapacity;
  }
  
  @Pure
  protected Integer getBatteryChargeCapacity() {
    return this.batteryChargeCapacity;
  }
  
  protected void setBatteryChargeCapacity(final Integer batteryChargeCapacity) {
    this.batteryChargeCapacity = batteryChargeCapacity;
  }
  
  @Pure
  protected Node getChargeStationNode() {
    return this.chargeStationNode;
  }
  
  protected void setChargeStationNode(final Node chargeStationNode) {
    this.chargeStationNode = chargeStationNode;
  }
}
