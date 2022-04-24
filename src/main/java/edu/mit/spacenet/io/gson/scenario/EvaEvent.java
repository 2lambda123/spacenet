package edu.mit.spacenet.io.gson.scenario;

import java.time.Duration;

import edu.mit.spacenet.simulator.event.EventType;

public class EvaEvent extends Event {
	public String type = TYPE_MAP.inverse().get(EventType.EVA);

	public static EvaEvent createFrom(edu.mit.spacenet.simulator.event.EvaEvent event, Context context) {
		EvaEvent e = new EvaEvent();
		e.name = event.getName();
		e.mission_time = Duration.ofSeconds((long) event.getTime()*24*60*60);
		e.priority = event.getPriority();
		return e;
	}
	
	@Override
	public edu.mit.spacenet.simulator.event.EvaEvent toSpaceNet(Context context) {
		edu.mit.spacenet.simulator.event.EvaEvent e = new edu.mit.spacenet.simulator.event.EvaEvent();
		e.setName(name);
		e.setTime(mission_time.getSeconds() / (24*60*60));
		e.setPriority(priority);
		return e;
	}

}