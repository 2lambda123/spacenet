package edu.mit.spacenet.io.gson.scenario;

import java.util.UUID;

import edu.mit.spacenet.data.ElementPreview;
import edu.mit.spacenet.domain.ClassOfSupply;
import edu.mit.spacenet.domain.Environment;
import edu.mit.spacenet.domain.element.ElementIcon;
import edu.mit.spacenet.domain.element.ElementType;
import edu.mit.spacenet.domain.element.I_State;
import edu.mit.spacenet.domain.resource.I_Resource;

public class SurfaceVehicle extends Carrier {
	public Double maxSpeed;
	public UUID fuelType;
	public Double fuelMaxAmount;
	public Double fuelAmount;

	public static SurfaceVehicle createFrom(edu.mit.spacenet.domain.element.SurfaceVehicle element, Context context) {
		SurfaceVehicle e = new SurfaceVehicle();
		e.id = context.getUUID(element);
		e.templateId = context.getTemplateUUID(element.getTid());
		SurfaceVehicle template = (SurfaceVehicle) context.getObject(e.templateId);
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
			e.maxCargoMass = element.getMaxCargoMass();
			e.maxCargoVolume = element.getMaxCargoVolume();
			e.cargoEnvironment = element.getCargoEnvironment().getName();
			e.maxCrewSize = element.getMaxCrewSize();
			e.maxSpeed = element.getMaxSpeed();
			e.fuelType = context.getUUID(element.getFuelTank().getResource());
			e.fuelMaxAmount = element.getFuelTank().getMaxAmount();
			e.fuelAmount = element.getFuelTank().getAmount();
			e.contents = context.getUUIDs(element.getContents());
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
			if(!template.maxCargoMass.equals(element.getMaxCargoMass())) {
				e.maxCargoMass = element.getMaxCargoMass();
			}
			if(!template.maxCargoVolume.equals(element.getMaxCargoVolume())) {
				e.maxCargoVolume = element.getMaxCargoVolume();
			}
			if(!template.cargoEnvironment.equals(element.getCargoEnvironment().getName())) {
				e.cargoEnvironment = element.getCargoEnvironment().getName();
			}
			if(!template.maxCrewSize.equals(element.getMaxCrewSize())) {
				e.maxCrewSize = element.getMaxCrewSize();
			}
			if(!template.maxSpeed.equals(element.getMaxSpeed())) {
				e.maxSpeed = element.getMaxSpeed();
			}
			if(!template.fuelType.equals(context.getUUID(element.getFuelTank().getResource()))) {
				e.fuelType = context.getUUID(element.getFuelTank().getResource());
			}
			if(!template.fuelMaxAmount.equals(element.getFuelTank().getMaxAmount())) {
				e.fuelMaxAmount = element.getFuelTank().getMaxAmount();
			}
			if(!template.fuelAmount.equals(element.getFuelTank().getAmount())) {
				e.fuelAmount = element.getFuelTank().getAmount();
			}
		}
		return e;
	}
	
	@Override
	public edu.mit.spacenet.domain.element.SurfaceVehicle toSpaceNet(Context context) {
		edu.mit.spacenet.domain.element.SurfaceVehicle e = new edu.mit.spacenet.domain.element.SurfaceVehicle();
		e.setUid(context.getId(id, e));
		e.setTid(context.getTemplateId(templateId));
		edu.mit.spacenet.domain.element.SurfaceVehicle template = (edu.mit.spacenet.domain.element.SurfaceVehicle) context.getObject(templateId);
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
		e.setStates(states == null ? template.getStates() : State.toSpaceNet(id, states, context));
		if(currentState == null && template != null) {
			e.setCurrentState(template.getCurrentState());
		} else if(currentState != null) {
			e.setCurrentState((I_State) context.getObject(currentState));
		}
		e.setParts(parts == null ? template.getParts() : Part.toSpaceNet(parts, context));
		e.setMaxCargoMass(maxCargoMass == null ? template.getMaxCargoMass() : maxCargoMass);
		e.setMaxCargoVolume(maxCargoVolume == null ? template.getMaxCargoVolume() : maxCargoVolume);
		e.setCargoEnvironment(cargoEnvironment == null ? template.getCargoEnvironment() : Environment.getInstance(cargoEnvironment));
		e.setMaxCrewSize(maxCrewSize == null ? template.getMaxCrewSize() : maxCrewSize);
		e.setMaxSpeed(maxSpeed == null ? template.getMaxSpeed() : maxSpeed);
		edu.mit.spacenet.domain.element.ResourceTank t = new edu.mit.spacenet.domain.element.ResourceTank();
		t.setResource(fuelType == null ? template.getFuelTank().getResource() : (I_Resource) context.getObject(fuelType));
		t.setMaxAmount(fuelMaxAmount == null ? template.getFuelTank().getMaxAmount() : fuelMaxAmount);
		t.setAmount(fuelAmount == null ? template.getFuelTank().getAmount() : fuelAmount);
		e.setFuelTank(t);
		e.getContents().addAll(contents == null ? template.getContents() : Element.toSpaceNet(contents, context));
		return e;
	}
	
	@Override
	public ElementPreview getPreview(Context context) {
		return new ElementPreview(context.getTemplateId(templateId), name, ElementType.SURFACE_VEHICLE, ElementIcon.getInstance(icon));
	}
}
