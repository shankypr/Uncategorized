package com.deductionapi.marshaller;

import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.deductionapi.model.DeductionItem;
import com.deductionapi.model.DeductionItemList;

public class XmlManager {
	
	public String getXmlStringFromDeductionItems(List<DeductionItem> itemList) {
		DeductionItemList items = new DeductionItemList(itemList);
		Logger.getGlobal().log(Level.INFO, "In XML header");
		String response = "No XML avail";
		try {
			Logger.getGlobal().log(Level.INFO, "In  Try Block");
			JAXBContext jaxbContext = JAXBContext.newInstance(DeductionItemList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// jaxbMarshaller.marshal(item, file);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(items, sw);
			response = sw.toString();
			Logger.getGlobal().log(Level.INFO, "String Writer's val: " + sw.toString());
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, "There was an Exception trying to create XML file!!\n");
			e.printStackTrace();
		}
		return response;
	}
	
}
