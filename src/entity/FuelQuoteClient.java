package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "fuelQuoteClient")
@Table(name = "fuelQuoteClient")
public class FuelQuoteClient implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "numberOfGallons")
	private int numberOfGallons;

	@Column(name = "deliveryDate")
	private Date deliveryDate;
	
	@Transient
	private String deliveryDateStr;

	@Column(name = "perGallon")
	private double perGallon;

	@Column(name = "discount")
	private double discount;

	@Column(name = "totlaAmount")
	private double totalAmount;

	@Column(name = "priceAfterDiscount")
	private double priceAfterDiscount;

	@Transient
	private double competitorsPrice;

	//@Transient
	private double transportationRate;
	
	@Column(name = "userId")
	private int userId;
	
	@Transient
	private String address;
	
	@Transient
	private String firstName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfGallons() {
		return numberOfGallons;
	}

	public void setNumberOfGallons(int numberOfGallons) {
		this.numberOfGallons = numberOfGallons;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public double getPerGallon() {
		return perGallon;
	}

	public void setPerGallon(double perGallon) {
		this.perGallon = perGallon;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getCompetitorsPrice() {
		return competitorsPrice;
	}

	public void setCompetitorsPrice(double competitorsPrice) {
		this.competitorsPrice = competitorsPrice;
	}

	public double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(double priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTransportationRate() {
		return transportationRate;
	}

	public void setTransportationRate(double transportationRate) {
		this.transportationRate = transportationRate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeliveryDateStr() {
		return deliveryDateStr;
	}

	public void setDeliveryDateStr(String deliveryDateStr) {
		this.deliveryDateStr = deliveryDateStr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String state) {
		this.address = state;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "FuelQuoteClient [id=" + id + ", numberOfGallons=" + numberOfGallons + ", deliveryDate=" + deliveryDate
				+ ", deliveryDateStr=" + deliveryDateStr + ", perGallon=" + perGallon + ", discount=" + discount
				+ ", totalAmount=" + totalAmount + ", priceAfterDiscount=" + priceAfterDiscount + ", competitorsPrice="
				+ competitorsPrice + ", transportationRate=" + transportationRate + ", userId=" + userId + "]";
	}

}
