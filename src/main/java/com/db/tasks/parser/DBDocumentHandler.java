package com.db.tasks.parser;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.db.tasks.objects.DocStructure;



public class DBDocumentHandler extends DefaultHandler 
{

	private List<DocStructure> docList;
	private List<String> listOfP;
	private DocStructure doc;
	private boolean headline;
	private boolean text;
	private boolean p;
	StringBuffer textContent;
	StringBuffer bodyContent;
	StringBuffer headlinecontent;
	
	@Override
	 public void startElement(String uri, String localName, String qName, 
			 Attributes attributes)
	 {
		if (qName.equalsIgnoreCase("body")) 
		{
			//create a new document
			doc = new DocStructure();
			 //init list of DocumentStructures
            if (CollectionUtils.isEmpty(docList))
            {
            	docList = new LinkedList<DocStructure>();
            }
            
		}
		else if(qName.equalsIgnoreCase("headline"))
		{
			headline = true;
			headlinecontent = null;
			
		}
		else if(qName.equalsIgnoreCase("text"))
		{
			text = true;
			headlinecontent = null;
		
			if(listOfP == null)
			{
				listOfP = new LinkedList<String>();
			}
		}
		else if(qName.equalsIgnoreCase("p"))
		{
			p = true;
		}
	 }
	 
	@Override
	 public void endElement(String uri, String localName, String qName) throws SAXException {
	        if (qName.equalsIgnoreCase("body"))
	        {
	        	docList.add(doc);
	                
	        }
	        else if(qName.equalsIgnoreCase("text"))
	        {
	        	doc.setBodyp(listOfP);
	        	
	        	doc.setBody(headlinecontent.toString());
	            
	        }
	        else if(qName.equalsIgnoreCase("headline"))
	        {
	        	doc.setHeading(headlinecontent.toString());
	        }
	        
	    }
	

	
	 @Override
	 public void characters(char ch[], int start, int length) throws SAXException {

		
	           if(headlinecontent ==null)
	           {
	        	   headlinecontent = new StringBuffer().append(ch, start, length);
	           }
	           else
	           {
	        	   headlinecontent.append(ch, start, length);
	           }
	      
	      
	       
	    }

	public List<DocStructure> getDocList() {
		return docList;
	}

	public void setDocList(List<DocStructure> docList) {
		this.docList = docList;
	}
}
