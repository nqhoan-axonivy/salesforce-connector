package salesforce.connector.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.primefaces.model.charts.polar.PolarAreaChartDataSet;
import org.primefaces.model.charts.polar.PolarAreaChartModel;

import com.axonivy.connector.salesforce.model.Account;
import com.axonivy.connector.salesforce.model.Opportunity;

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
	
	public ListOppsBean(List<Opportunity> list) {
		opps = new ArrayList<>();
		opportunities = new ArrayList<>(list);
		Ivy.log().info("list ne: " + opportunities.size());
		
		opps = opportunities.stream().map( o -> new OpportunityDTO(o.getId(), o.getName(), getAccName(o.getAccountId()), o.getAmount(), o.getCloseDate(), o.getStageName())).collect(Collectors.toList());
		
		createBarChartModel();
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
        title.setText("Bar Chart");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
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
	
	
}
