package com.lcc3710;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class Template {
	public Template(String xml) {		
		//parse xml
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			
			Document doc = db.parse(in);
			
			// START ACTUAL PARSING
			Element topElement = doc.getDocumentElement();
			// get template attributes
			NamedNodeMap attr = topElement.getAttributes();
			if (attr.getLength() > 0) {
				Node temp;
				if ((temp = attr.getNamedItem("title")) != null)
					this.title = temp.getNodeValue();
				if ((temp = attr.getNamedItem("creator")) != null)
					this.creator = temp.getNodeValue();
				if ((temp = attr.getNamedItem("icon")) != null)
					this.icon = temp.getNodeValue();
			}
			
			// get widgets
			NodeList widgetNL = topElement.getElementsByTagName("Widget");
			ArrayList<TemplateWidget> wl = new ArrayList<TemplateWidget>();
			for (int i = 0; i < widgetNL.getLength(); i++) {
				Node w = widgetNL.item(i);
				attr = w.getAttributes();
				NodeList cl = w.getChildNodes();
				
				TemplateWidget tw = new TemplateWidget();				
				// parse attributes
				if (attr.getLength() > 0) {			
					Node temp;
					if ((temp = attr.getNamedItem("type")) != null)
						tw.type = temp.getNodeValue();
					if ((temp = attr.getNamedItem("label")) != null)
						tw.label = temp.getNodeValue();
					if ((temp = attr.getNamedItem("required")) != null) {
						if (temp.getNodeValue().equals("true")) {
							tw.required = true;
						} else {
							tw.required = false;
						}
					}
				}
				// parse children
				for (int j = 0; j < cl.getLength(); j++) {
					Node c = cl.item(j);
					String value = c.getFirstChild().getNodeValue();
					String nodeName = c.getNodeName();
					// big ass if statement
					// add stuff if we need more fields for widgets in the xml
					if (nodeName.equals("size")) {
						tw.size = value;
					} else if (nodeName.equals("inputType")) {
						tw.inputType = value;
					} else if (nodeName.equals("hint")) {
						tw.hint = value;
					} else if (nodeName.equals("value")) {
						attr = c.getAttributes();
						
						if (tw.type.equals("Location")) {
							tw.latitude = Double.parseDouble(attr.getNamedItem("latitude").getNodeValue());
							tw.longitude = Double.parseDouble(attr.getNamedItem("longitude").getNodeValue());
						}
						
						tw.value = value;
					}
					//end big ass if statement
				}
				// end attribute parse
				// put the TemplateWidget into our array list
				wl.add(tw);
			}
			// END ACTUAL PARSING
			this.widgets = wl.toArray(new TemplateWidget[0]);
		} catch (Exception e) {
			Log.e("xml_perf", "DOM parser failed", e);
		}
	}
	
	public String title;
	public String creator;
	public String icon;
	public TemplateWidget[] widgets;
}
