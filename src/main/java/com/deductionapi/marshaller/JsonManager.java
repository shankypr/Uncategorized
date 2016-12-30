package com.deductionapi.marshaller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.deductionapi.model.DeductionItem;
import com.deductionapi.model.DeductionItemList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonManager {

	public List<DeductionItem> getItemListFromJsonString(String jsonString) {
		if(jsonString == null || jsonString.isEmpty()) {
			Logger.getGlobal().log(Level.INFO, "Empty or Null Json String passed in");
			return null;
		}
		
		Gson gson = new Gson();

		
		DeductionItemList  list = gson.fromJson(jsonString, DeductionItemList.class);

		return list.getDeductionItems();
		
	
		

		//return null;

		
	}
	public String getJsonStringFromDeductionItems(List<DeductionItem> itemList) {
		if(itemList==null || itemList.size()==0 ) {
			return "{\"key\",\"No items\"}";
		}
		DeductionItemList items = new DeductionItemList(itemList);
		ObjectMapper mapper = new ObjectMapper();
		String response = "NO Json avail";
		try {
			response = mapper.writeValueAsString(items);
		} catch (JsonProcessingException e) {
			Logger.getGlobal().log(Level.SEVERE, "There was an Exception trying to create JSON file!!\n");
			e.printStackTrace();
		}
		return response;
	}
	
}
