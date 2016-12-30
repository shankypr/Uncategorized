package com.deductionapi.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeductionItem {
	int itemId;
	String itemName;
	String itemDescription;
	float estimatedValue;
	int year;


	public DeductionItem() {
		
	}
	public DeductionItem(int id,String name,String description,float estimatedValue,int year) {
		this.itemId=id;
		this.itemName=name;
		this.itemDescription=description;
		this.estimatedValue=estimatedValue;
		this.year=year;
	}
	
	public int getYear() {
		return year;
	}

	@XmlElement
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getItemId() {
		return itemId;
	}

	@XmlElement
	public void setItemId(int id) {
		this.itemId = id;
	}
	
	
	public String getItemName() {
		return itemName;
	}

	@XmlElement
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}

	@XmlElement
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public float getEstimatedValue() {
		return estimatedValue;
	}

	@XmlElement
	public void setEstimatedValue(float estimatedValue) {
		this.estimatedValue = estimatedValue;
	}

	@XmlElement
	public float getDeductionAmt() {
		return (float) (estimatedValue * .3);
	}


	
}
