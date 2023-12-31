package edu.mit.spacenet.io.gson.scenario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import edu.mit.spacenet.data.ElementPreview;
import edu.mit.spacenet.domain.ClassOfSupply;
import edu.mit.spacenet.domain.Environment;
import edu.mit.spacenet.domain.element.ElementIcon;
import edu.mit.spacenet.domain.element.ElementType;
import edu.mit.spacenet.domain.element.I_Element;
import edu.mit.spacenet.domain.element.I_State;

public class Element {
	public static final BiMap<String, ElementType> TYPE_MAP = new ImmutableBiMap.Builder<String, ElementType>()
			.put("Element", ElementType.ELEMENT)
			.put("Crew Member", ElementType.CREW_MEMBER)
			.put("Resource Container", ElementType.RESOURCE_CONTAINER)
			.put("Resource Tank", ElementType.RESOURCE_TANK)
			.put("Carrier", ElementType.CARRIER)
			.put("Propulsive Vehicle", ElementType.PROPULSIVE_VEHICLE)
			.put("Surface Vehicle", ElementType.SURFACE_VEHICLE)
			.build();

	public UUID id;
	public UUID templateId;
	public String name;
	public String description;
	public Double accommodatationMass;
	public Double mass;
	public Double volume;
	public Integer classOfSupply;
	public String environment;
	public List<State> states;
	public UUID currentState;
	public List<Part> parts;
	public String icon;

	public static Element createFrom(I_Element element, Context context) {
		if(element.getElementType() == ElementType.ELEMENT) {
			Element e = new Element();
			e.id = context.getUUID(element);
			e.templateId = context.getTemplateUUID(element.getTid());
			Element template = (Element) context.getObject(e.templateId);
			if(template == null) {
				e.name = element.getName();
				e.description = element.getDescription();
				e.accommodatationMass = element.getAccommodationMass();
				e.mass = element.getMass();
				e.volume = element.getVolume();
				e.classOfSupply = element.getClassOfSupply().getId();
				e.environment = element.getEnvironment().getName();
				if(element.getIconType() != element.getElementType().getIconType()) {
					e.icon = element.getIconType().getName();
				}
				e.states = State.createFrom(element.getStates(), context);
				e.currentState = context.getUUID(element.getCurrentState());
				e.parts = Part.createFrom(element.getParts(), context);
			} else {
				if(!template.name.equals(element.getName())) {
					e.name = element.getName();
				}
				if(!template.description.equals(element.getDescription())) {
					e.description = element.getDescription();
				}
				if(!template.accommodatationMass.equals(element.getAccommodationMass())) {
					e.accommodatationMass = element.getAccommodationMass();
				}
				if(!template.mass.equals(element.getMass())) {
					e.mass = element.getMass();
				}
				if(!template.volume.equals(element.getVolume())) {
					e.volume = element.getVolume();
				}
				if(!template.classOfSupply.equals(element.getClassOfSupply().getId())) {
					e.classOfSupply = element.getClassOfSupply().getId();
				}
				if(!template.environment.equals(element.getEnvironment().getName())) {
					e.environment = element.getEnvironment().getName();
				}
				if((template.icon == null && element.getIconType() != element.getElementType().getIconType()) 
						|| (template.icon != null && !template.icon.equals(element.getIconType().getName()))) {
					e.icon = element.getIconType().getName();
				}
			}
			return e;
		} else if(element.getElementType() == ElementType.RESOURCE_CONTAINER) {
			return ResourceContainer.createFrom((edu.mit.spacenet.domain.element.ResourceContainer) element, context);
		} else if(element.getElementType() == ElementType.RESOURCE_TANK) {
			return ResourceTank.createFrom((edu.mit.spacenet.domain.element.ResourceTank) element, context);
		} else if(element.getElementType() == ElementType.CARRIER) {
			return Carrier.createFrom((edu.mit.spacenet.domain.element.Carrier) element, context);
		} else if(element.getElementType() == ElementType.PROPULSIVE_VEHICLE) {
			return PropulsiveVehicle.createFrom((edu.mit.spacenet.domain.element.PropulsiveVehicle) element, context);
		} else if(element.getElementType() == ElementType.SURFACE_VEHICLE) {
			return SurfaceVehicle.createFrom((edu.mit.spacenet.domain.element.SurfaceVehicle) element, context);
		} else if(element.getElementType() == ElementType.CREW_MEMBER) {
			return CrewMember.createFrom((edu.mit.spacenet.domain.element.CrewMember) element, context);
		} else {
			throw new UnsupportedOperationException("unknown element type: " + element.getElementType());
		}
	}
	
	public static List<Element> createFrom(Collection<? extends I_Element> elements, Context context) {
		List<Element> es = new ArrayList<Element>();
		for(I_Element e : elements) {
			es.add(Element.createFrom(e, context));
		}
		return es;
	}
	
	public edu.mit.spacenet.domain.element.Element toSpaceNet(Context context) {
		edu.mit.spacenet.domain.element.Element e = new edu.mit.spacenet.domain.element.Element();
		e.setUid(context.getId(id, e));
		e.setTid(context.getId(templateId, context.getObject(templateId)));
		edu.mit.spacenet.domain.element.Element template = (edu.mit.spacenet.domain.element.Element) context.getObject(templateId);
		e.setName(name == null ? template.getName() : name);
		e.setDescription(description == null ? template.getDescription() : description);
		e.setAccommodationMass(accommodatationMass == null ? template.getAccommodationMass() : accommodatationMass);
		e.setMass(mass == null ? template.getMass() : mass);
		e.setVolume(volume == null ? template.getVolume() : volume);
		e.setClassOfSupply(classOfSupply == null ? template.getClassOfSupply() : ClassOfSupply.getInstance(classOfSupply));
		e.setEnvironment(environment == null ? template.getEnvironment() : Environment.getInstance(environment));
		if(icon == null && template != null && template.getIconType() != template.getElementType().getIconType()) {
			e.setIconType(template.getIconType());
		}
		e.setIconType(icon == null && template != null ? template.getIconType() : ElementIcon.getInstance(icon));
		e.setStates(states == null ? template.getStates() : State.toSpaceNet(id, states, context));
		if(currentState == null && template != null) {
			e.setCurrentState(template.getCurrentState());
		} else if(currentState != null) {
			e.setCurrentState((I_State) context.getObject(currentState));
		}
		e.setParts(parts == null ? template.getParts() : Part.toSpaceNet(parts, context));
		return e;
	}
	
	public ElementPreview getPreview(Context context) {
		return new ElementPreview(context.getTemplateId(templateId), name, ElementType.ELEMENT, ElementIcon.getInstance(icon));
	}
	
	public static SortedSet<I_Element> toSpaceNet(Collection<UUID> elements, Context context) {
		SortedSet<I_Element> es = new TreeSet<I_Element>();
		if(elements != null) {
			for(UUID uuid : elements) {
				es.add(((I_Element) context.getObject(uuid)));
			}
		}
		return es;
	}
}
