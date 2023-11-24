package salesforce.connector.demo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.axonivy.connector.salesforce.model.Account;
import com.axonivy.connector.salesforce.model.Opportunity;
import com.axonivy.connector.salesforce.model.OpportunityUpdateDTO;
import com.axonivy.connector.salesforce.utils.ConvertUtils;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import salesforce.connector.demo.dto.OpportunityDTO;
import salesforce.connector.demo.enums.Stage;

public class ListOppsBean {
	List<Opportunity> opportunities;
	List<OpportunityDTO> opps;
	private Opportunity selectedOpp;
	private String accountName;
	private BarChartModel barModel;
	String ownerId;
	List<Account> accs;
	List<String> stages;
	private OpportunityUpdateDTO updateDTO;
	
	public ListOppsBean() {
		ownerId= "0055h000009a4XMAAY";
		opps = new ArrayList<>();
		getAllOpps();
		Ivy.log().info("list opportunities: " + opportunities.size());
		
		opps = Utils.convertToOppDTO(opportunities);
		
		createBarChartModel();
	}
	
	public void openOpportunityDetail(String id) {
		selectedOpp = SubProcessCall.withPath("Functional Processes/getOpportunity")
				.withStartSignature("getOpportunity(String)")
				.withParam("id", id)
				.call()
				.get("opp", Opportunity.class);
		accountName = Utils.getAccName(selectedOpp.getAccountId());
	}
	
	@SuppressWarnings("unchecked")
	public void getAllOpps() {
		opportunities = (List<Opportunity>) SubProcessCall.withPath("Functional Processes/getAllOpps")
				.withStartSignature("getAllOpps()")
		         .call()
		         .get("opps", Opportunity.class);
	}
	
	@SuppressWarnings("unchecked")
	public void getAllAccounts() {
		accs = (List<Account>) SubProcessCall.withPath("Functional Processes/getAccounts")
				.withStartSignature("getAllAccounts()")
		         .call()
		         .get("accs", Account.class);
	}
	
	public void addNewOppotunity() {
		accountName = null;
		selectedOpp = new Opportunity();
		selectedOpp.setOwnerId(ownerId);
		getAllAccounts();
		getListStages();
	}
	
	public void updateOppotunity(String id) {
		updateDTO = new OpportunityUpdateDTO();
		openOpportunityDetail(id);
		getAllAccounts();
		getListStages();
	}
	
	public void convertToUpdateDTO() throws IllegalAccessException, InvocationTargetException {
		selectedOpp.setAccountId(getAccountIdByName(accountName));
		updateDTO = ConvertUtils.convertToOpportunityObjUpdate(selectedOpp);
	}
	
	public void updateCurrentListAfterUpdate() {
		OptionalInt result = IntStream.range(0, opps.size())
                .filter(x -> selectedOpp.getId().equals(opps.get(x).getId()))
                .findFirst();
		if (result.isPresent())
		{
		   int index = result.getAsInt();
		   opps.set(index, Utils.convertToOppDTO(selectedOpp, updateDTO));
		}
	}
	
	public void beforeDelete(String id) {
		selectedOpp = SubProcessCall.withPath("Functional Processes/getOpportunity")
				.withStartSignature("getOpportunity(String)")
				.withParam("id", id)
				.call()
				.get("opp", Opportunity.class);
	}
	
	public void updateCurrentListAfterDelete() {
		OptionalInt result = IntStream.range(0, opps.size())
                .filter(x -> selectedOpp.getId().equals(opps.get(x).getId()))
                .findFirst();
		if (result.isPresent())
		{
		   int index = result.getAsInt();
		   opps.remove(index);
		}
	}
	
	private void getListStages() {
		stages = Arrays.stream(Stage
				.values())
				.map(e -> e.getLabel())
				.collect(Collectors.toList());
	}

	public List<String> completeAccount(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> accountList = new ArrayList<>();
        List<Account> acccountList = accs;
        for (Account acc : acccountList) {
        	accountList.add(acc.getName());
        }

        return accountList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
	
	public String getAccountIdByName(String name) {
		String accId = null;
		Account acc =  accs.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
		if(acc != null) accId = acc.getId();
		return accId;
	}
	
	public void reset() {
		selectedOpp = new Opportunity();
		accountName = null;
	}
	
	private void createBarChartModel() {
		barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet dataSet = new BarChartDataSet();
        dataSet.setLabel("Oppotunities");

        List<Number> values = new ArrayList<>();
        values.add(countByStage(Stage.PROSPECTING.label));
        values.add(countByStage(Stage.QUALIFICATION.label));
        values.add(countByStage(Stage.NEEDS_ANALYSIS.label));
        values.add(countByStage(Stage.VALUE_PROPOSITION.label));
        values.add(countByStage(Stage.DECISION_MAKERS.label));
        values.add(countByStage(Stage.PERCEPTION_ANALYSIS.label));
        values.add(countByStage(Stage.PROPOSAL_PRICE_QUOTE.label));
        values.add(countByStage(Stage.NEGOTIATION_REVIEW.label));
        values.add(countByStage(Stage.CLOSED_WON.label));
        values.add(countByStage(Stage.CLOSED_LOST.label));
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(201, 203, 207)");
        bgColors.add("rgb(54, 162, 235)");
        
        bgColors.add("rgb(153, 102, 255)");
        bgColors.add("rgb(153, 102, 51)");
        bgColors.add("rgb(255, 51, 204)");
        bgColors.add("rgb(204, 153, 0)");
        bgColors.add("rgb(51, 153, 51)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add(Stage.PROSPECTING.label);
        labels.add(Stage.QUALIFICATION.label);
        labels.add(Stage.NEEDS_ANALYSIS.label);
        labels.add(Stage.VALUE_PROPOSITION.label);
        labels.add(Stage.DECISION_MAKERS.label);
        labels.add(Stage.PERCEPTION_ANALYSIS.label);
        labels.add(Stage.PROPOSAL_PRICE_QUOTE.label);
        labels.add(Stage.NEGOTIATION_REVIEW.label);
        labels.add(Stage.CLOSED_WON.label);
        labels.add(Stage.CLOSED_LOST.label);
        data.setLabels(labels);

        barModel.setData(data);
        
      //Options
        BarChartOptions options = new BarChartOptions();
//        options.setMaintainAspectRatio(false);
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
//        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Opportunity");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(false);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("italic");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel.setOptions(options);
    }
	
	private int countByStage(String stageName) {
		return opps.stream().filter(o -> o.getStage().equals(stageName)).collect(Collectors.toList()).size();
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

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public List<Account> getAccs() {
		return accs;
	}

	public void setAccs(List<Account> accs) {
		this.accs = accs;
	}

	public List<String> getStages() {
		return stages;
	}

	public OpportunityUpdateDTO getUpdateDTO() {
		return updateDTO;
	}

	public void setUpdateDTO(OpportunityUpdateDTO updateDTO) {
		this.updateDTO = updateDTO;
	}
	
}
