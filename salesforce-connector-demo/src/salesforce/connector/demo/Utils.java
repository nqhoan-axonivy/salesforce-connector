package salesforce.connector.demo;

import java.util.List;
import java.util.stream.Collectors;

import com.axonivy.connector.salesforce.model.Account;
import com.axonivy.connector.salesforce.model.Opportunity;
import com.axonivy.connector.salesforce.model.OpportunityUpdateDTO;

import ch.ivyteam.ivy.process.call.SubProcessCall;
import salesforce.connector.demo.dto.OpportunityDTO;

public class Utils {
	public static String getAccName(String accountId) {
		Account acc = SubProcessCall.withPath("Functional Processes/getAccount")
				.withStartSignature("getAccount(String)")
		         .withParam("id", accountId)
		         .call()
		         .get("acc", Account.class);
		return acc.getName();
	}
	
	public static List<OpportunityDTO> convertToOppDTO(List<Opportunity> list) {
		return list.stream().map( o -> new OpportunityDTO(o.getId(), o.getName(), getAccName(o.getAccountId()), o.getAmount(), o.getCloseDate(), o.getStageName())).collect(Collectors.toList());
	}
	
	public static OpportunityDTO convertToOppDTO(Opportunity o) {
		return new OpportunityDTO(o.getId(), o.getName(), getAccName(o.getAccountId()), o.getAmount(), o.getCloseDate(), o.getStageName());
	}
	
	public static OpportunityDTO convertToOppDTO(Opportunity o, OpportunityUpdateDTO o1) {
		return new OpportunityDTO(o.getId(), o1.getName(), getAccName(o1.getAccountId()), o1.getAmount(), o1.getCloseDate(), o1.getStageName());
	}
}
