package edu.mit.spacenet.io.gson.scenario;

import java.util.UUID;

import edu.mit.spacenet.domain.element.I_Element;

public class SparingByMassDemandModel extends DemandModel {
	public double unpressurizedSparesRate;
	public double pressurizedSparesRate;
	public boolean partsListEnabled;

	public static SparingByMassDemandModel createFrom(edu.mit.spacenet.domain.model.SparingByMassDemandModel demandModel, Context context) {
		SparingByMassDemandModel m = new SparingByMassDemandModel();
		m.id = context.getUUID(demandModel);
		m.name = demandModel.getName();
		m.description = demandModel.getDescription();
		m.unpressurizedSparesRate = demandModel.getUnpressurizedSparesRate();
		m.pressurizedSparesRate = demandModel.getPressurizedSparesRate();
		m.partsListEnabled = demandModel.isPartsListEnabled();
		return m;
	}
	
	@Override
	public edu.mit.spacenet.domain.model.SparingByMassDemandModel toSpaceNet(UUID source, Context context) {
		edu.mit.spacenet.domain.model.SparingByMassDemandModel m = new edu.mit.spacenet.domain.model.SparingByMassDemandModel((I_Element) context.getObject(source));
		m.setTid(context.getId(id, m));
		m.setName(name);
		m.setDescription(description);
		m.setUnpressurizedSparesRate(unpressurizedSparesRate);
		m.setPressurizedSparesRate(pressurizedSparesRate);
		m.setPartsListEnabled(partsListEnabled);
		return m;
	}
}
