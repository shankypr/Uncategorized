package com.deductionapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class DeductionItemList {
    
    private List<DeductionItem> deductionItems = new ArrayList<DeductionItem>();
 
    public DeductionItemList() {
    	
    }
    
    public DeductionItemList(List<DeductionItem> items) {
    	if(items==null) {
    		Logger.getGlobal().log(Level.SEVERE, "Null list of items passed in");
    	}
    	else {
    		this.deductionItems = items;
    	}
    }
    
    public List<DeductionItem> getDeductionItems() {
        return deductionItems;
    }
 
    @XmlElement()
    public void setDeductionItems(List<DeductionItem> deductionItems) {
        this.deductionItems = deductionItems;
    }
    
    
	public void addItem(DeductionItem item) throws Exception{
		if(item == null) {
			throw new Exception("DeductionItem passed in is null");
		}
		if(item.getItemName() == null) {
			throw new Exception("DeductionItem name cannot be null");
		}
		
		deductionItems.add(item);
		Logger.getGlobal().log(Level.INFO, "Deduction Item with Name Added: "+item.getItemName());
		
	}
}
