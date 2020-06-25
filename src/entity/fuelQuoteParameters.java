package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "fuelQuoteParameters")
@Table(name = "fuelQuoteParameters")
public class fuelQuoteParameters{
	
	@Id
	@Column(name="fqpid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fqpid;
	
	@Column(name="baserate")
	private double baseRate;
	
	@Column(name="profitmargin")
	private double profitMargin;
	
	@Column(name="fluctuationsummer")
	private double fluctuationSummer;
	
	@Column(name="fluctuationwinter")
	private double fluctuationWinter;
	
	@Column(name="transportationratepermile")
	private double transportationRatePerMile;
	
	@Column(name="startdate")
	private String startDate;
	
	public int getFqpid() {
		return fqpid;
	}

	public void setFqpid(int fqpid) {
		this.fqpid = fqpid;
	}

	public double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(double baseRate) {
		this.baseRate = baseRate;
	}

	public double getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(double profitMargin) {
		this.profitMargin = profitMargin;
	}

	public double getFluctuationSummer() {
		return fluctuationSummer;
	}

	public void setFluctuationSummer(double fluctuationSummer) {
		this.fluctuationSummer = fluctuationSummer;
	}

	public double getFluctuationWinter() {
		return fluctuationWinter;
	}

	public void setFluctuationWinter(double fluctuationWinter) {
		this.fluctuationWinter = fluctuationWinter;
	}

	public double getTransportationRatePerMile() {
		return transportationRatePerMile;
	}

	public void setTransportationRatePerMile(double transportationRatePerMile) {
		this.transportationRatePerMile = transportationRatePerMile;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	
	public fuelQuoteParameters()
	{
		//Default constructor
	}

	@Override
	public String toString() {
		return "fuelQuoteParameters [fqpid=" + fqpid + ", baseRate=" + baseRate + ", profitMargin=" + profitMargin
				+ ", fluctuationSummer=" + fluctuationSummer + ", fluctuationWinter=" + fluctuationWinter
				+ ", transportationRatePerMile=" + transportationRatePerMile + ", startDate=" + startDate + "]";
	}
	
	
}