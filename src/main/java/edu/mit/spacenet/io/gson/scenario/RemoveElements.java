package edu.mit.spacenet.io.gson.scenario;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.threeten.extra.PeriodDuration;

import edu.mit.spacenet.simulator.event.EventType;

public class RemoveElements extends Event {
	
	public List<UUID> elements = new ArrayList<UUID>();

	public static RemoveElements createFrom(edu.mit.spacenet.simulator.event.RemoveEvent event, Context context) {
		RemoveElements e = new RemoveElements();
		e.type = TYPE_MAP.inverse().get(EventType.REMOVE);
		e.name = event.getName();
		e.mission_time = PeriodDuration.of(Duration.ofSeconds((long) event.getTime()*24*60*60));
		e.priority = event.getPriority();
		e.location = context.getUUID(event.getLocation());
		e.elements = context.getUUIDs(event.getElements());
		return e;
	}
	
	@Override
	public edu.mit.spacenet.simulator.event.RemoveEvent toSpaceNet(Context context) {
		edu.mit.spacenet.simulator.event.RemoveEvent e = new edu.mit.spacenet.simulator.event.RemoveEvent();
		e.setName(name);
		e.setTime(mission_time.getDuration().getSeconds() / (24*60*60d));
		e.setPriority(priority);
		e.setLocation((edu.mit.spacenet.domain.network.Location) context.getObject(location));
		e.setElements(Element.toSpaceNet(elements, context));
		return e;
	}

}
