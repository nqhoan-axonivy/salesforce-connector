package salesforce.connector.demo.enums;

public enum Stage {
	PROSPECTING("Prospecting"),
	QUALIFICATION("Qualification"),
	NEEDS_ANALYSIS("Needs Analysis"),
	VALUE_PROPOSITION("Value Proposition"),
	DECISION_MAKERS("Id. Decision Makers"),
	PERCEPTION_ANALYSIS("Perception Analysis"),
	PROPOSAL_PRICE_QUOTE("Proposal/Price Quote"),
	NEGOTIATION_REVIEW("Negotiation/Review"),
	CLOSED_WON("Closed Won"),
	CLOSED_LOST("Closed_Lost");

	public final String label;
	
	Stage(String label) {
		this.label = label;
	}
}
