package edu.mit.spacenet.io.gson.scenario;

import java.time.Duration;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import edu.mit.spacenet.simulator.event.EventType;

public abstract class Event {
	public static final BiMap<String, EventType> TYPE_MAP = new ImmutableBiMap.Builder<String, EventType>()
			.put("Create Elements", EventType.CREATE)
			.put("Add Resources", EventType.ADD)
			.put("Move Elements", EventType.MOVE)
			.put("Transfer Resources", EventType.TRANSFER)
			.put("Remove Elements", EventType.REMOVE)
			.put("Reconfigure Element", EventType.RECONFIGURE)
			.put("Reconfigure Group", EventType.RECONFIGURE_GROUP)
			.put("Consume Resources", EventType.DEMAND)
			.put("Propulsive Burn", EventType.BURN)
			.put("Crewed EVA", EventType.EVA)
			.put("Crewed Exploration", EventType.EXPLORATION)
			.put("Space Transport", EventType.SPACE_TRANSPORT)
			.put("Surface Transport", EventType.SURFACE_TRANSPORT)
			.put("Flight Transport", EventType.FLIGHT_TRANSPORT)
			.build();
	
	public String name;
	public int priority;
	public Duration mission_time;

	public static Event createFrom(edu.mit.spacenet.simulator.event.I_Event event, Context context) {
		if(event.getEventType() == EventType.CREATE) {
			return CreateElements.createFrom((edu.mit.spacenet.simulator.event.CreateEvent) event, context);
		} else if(event.getEventType() == EventType.ADD) {
			return AddResources.createFrom((edu.mit.spacenet.simulator.event.AddEvent) event, context);
		} else if(event.getEventType() == EventType.MOVE) {
			return MoveElements.createFrom((edu.mit.spacenet.simulator.event.MoveEvent) event, context);
		} else if(event.getEventType() == EventType.TRANSFER) {
			return TransferResources.createFrom((edu.mit.spacenet.simulator.event.TransferEvent) event, context);
		} else if(event.getEventType() == EventType.REMOVE) {
			return RemoveElements.createFrom((edu.mit.spacenet.simulator.event.RemoveEvent) event, context);
		} else if(event.getEventType() == EventType.RECONFIGURE) {
			return ReconfigureElements.createFrom((edu.mit.spacenet.simulator.event.ReconfigureEvent) event, context);
		} else if(event.getEventType() == EventType.RECONFIGURE_GROUP) {
			return ReconfigureElements.createFrom((edu.mit.spacenet.simulator.event.ReconfigureGroupEvent) event, context);
		} else if(event.getEventType() == EventType.DEMAND) {
			return ConsumeResources.createFrom((edu.mit.spacenet.simulator.event.DemandEvent) event, context);
		} else if(event.getEventType() == EventType.BURN) {
			return BurnEvent.createFrom((edu.mit.spacenet.simulator.event.BurnEvent) event, context);
		} else if(event.getEventType() == EventType.EVA) {
			return EvaEvent.createFrom((edu.mit.spacenet.simulator.event.EvaEvent) event, context);
		} else if(event.getEventType() == EventType.EXPLORATION) {
			return Exploration.createFrom((edu.mit.spacenet.simulator.event.ExplorationProcess) event, context);
		} else if(event.getEventType() == EventType.SPACE_TRANSPORT) {
			return SpaceTransport.createFrom((edu.mit.spacenet.simulator.event.SpaceTransport) event, context);
		} else if(event.getEventType() == EventType.SURFACE_TRANSPORT) {
			return SurfaceTransport.createFrom((edu.mit.spacenet.simulator.event.SurfaceTransport) event, context);
		} else if(event.getEventType() == EventType.FLIGHT_TRANSPORT) {
			return FlightTransport.createFrom((edu.mit.spacenet.simulator.event.FlightTransport) event, context);
		} else {
			throw new UnsupportedOperationException("unknown event type: " + event);
		}
	}
	
	public abstract edu.mit.spacenet.simulator.event.I_Event toSpaceNet(Context context);
}