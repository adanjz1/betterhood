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
			// get template title
			NodeList titleNL = topElement.getElementsByTagName("Title");
			if (titleNL.getLength() != 0) {
				this.title = titleNL.item(0).getFirstChild().getNodeValue();
			}
			// get widgets
			NodeList widgetNL = topElement.getElementsByTagName("Widget");
			ArrayList<TemplateWidget> wl = new ArrayList<TemplateWidget>();
			for (int i = 0; i < widgetNL.getLength(); i++) {
				TemplateWidget tw = new TemplateWidget();
				NodeList cl = widgetNL.item(i).getChildNodes();
				// parse attributes
				for (int j = 0; j < cl.getLength(); j++) {
					Node c = cl.item(j);
					String value = c.getFirstChild().getNodeValue();
					String nodeName = c.getNodeName();
					// big ass if statement
					// add stuff if we need more fields for widgets in the xml
					if (nodeName.equals("type")) {
						tw.type = value;
					} else if (nodeName.equals("label")) {
						tw.label = value;
					} else if (nodeName.equals("required")) {
						if (value.equals("true")) {
							tw.required = true;
						} else {
							tw.required = false;
						}
					} else if (nodeName.equals("size")) {
						tw.size = value;
					} else if (nodeName.equals("inputType")) {
						tw.inputType = value;
					} else if (nodeName.equals("hint")) {
						tw.hint = value;
					} else if (nodeName.equals("value")) {
						NamedNodeMap attr = c.getAttributes();
						
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
	public TemplateWidget[] widgets;
}
