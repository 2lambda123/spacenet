package edu.mit.spacenet.io.gson.scenario;

import edu.mit.spacenet.domain.network.node.Body;

public class LagrangeNode extends Node {
	public String body_2;
	public int lp_number;
	
	public static LagrangeNode createFrom(edu.mit.spacenet.domain.network.node.LagrangeNode node, Context context) {
		LagrangeNode n = new LagrangeNode();
		n.id = context.getUUID(node);
		n.name = node.getName();
		n.description = node.getDescription();
		n.body_1 = node.getBody().getName();
		n.body_2 = node.getMinorBody().getName();
		n.lp_number = node.getNumber();
		n.contents = context.getUUIDs(node.getContents());
		return n;
	}
	
	public edu.mit.spacenet.domain.network.node.LagrangeNode toSpaceNet(Context context) {
		edu.mit.spacenet.domain.network.node.LagrangeNode n = new edu.mit.spacenet.domain.network.node.LagrangeNode();
		n.setTid(context.getId(id, n));
		n.setName(name);
		n.setDescription(description);
		n.setBody(Body.getInstance(body_1));
		n.setMinorBody(Body.getInstance(body_2));
		n.setNumber(lp_number);
		n.getContents().addAll(Element.toSpaceNet(contents, context));
		return n;
	}
}
