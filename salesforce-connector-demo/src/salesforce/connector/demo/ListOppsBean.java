package salesforce.connector.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.axonivy.connector.salesforce.model.Account;
import com.axonivy.connector.salesforce.model.Opportunity;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import salesforce.connector.demo.dto.OpportunityDTO;

public class ListOppsBean {
	List<Opportunity> opportunities;
	List<OpportunityDTO> opps;
	private Opportunity selectedOpp;
	private String accountName;
	
	public ListOppsBean(List<Opportunity> list) {
		opps = new ArrayList<>();
		opportunities = new ArrayList<>(list);
		Ivy.log().info("list ne: " + opportunities.size());
		
		opps = opportunities.stream().map( o -> new OpportunityDTO(o.getId(), o.getName(), getAccName(o.getAccountId()), o.getAmount(), o.getCloseDate(), o.getStageName())).collect(Collectors.toList());
	}

	public static String getAccName(String accountId) {
		Account acc = SubProcessCall.withPath("Functional Processes/getAccount")
				.withStartSignature("getAccount(String)")
		         .withParam("id", accountId)
		         .call()
		         .get("acc", Account.class);
		return acc.getName();
	}
	
	public void openOpportunityDetail(String id) {
		 selectedOpp = SubProcessCall.withPath("Functional Processes/getOpportunity")
					.withStartSignature("getOpportunity(String)")
			         .withParam("id", id)
			         .call()
			         .get("opp", Opportunity.class);
		 accountName = getAccName(selectedOpp.getAccountId());
	}

	public List<Opportunity> getOpportunities() {
		return opportunities;
	}

	public void setOpportunities(List<Opportunity> opportunities) {
		this.opportunities = opportunities;
	}

	public List<OpportunityDTO> getOpps() {
		return opps;
	}

	public void setOpps(List<OpportunityDTO> opps) {
		this.opps = opps;
	}

	public Opportunity getSelectedOpp() {
		return selectedOpp;
	}

	public void setSelectedOpp(Opportunity selectedOpp) {
		this.selectedOpp = selectedOpp;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	
}
