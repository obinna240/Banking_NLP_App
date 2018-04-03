package com.db.tasks.gateUtil;


import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import org.apache.commons.lang.StringUtils;



import com.db.tasks.objects.DBDoc;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.Utils;
import gate.creole.ResourceInstantiationException;
import gate.util.DocumentProcessor;
import gate.util.GateException;

/**
 * 
 * @author oonyimadu
 * Processes documents
 */
public class DocProcessor implements DocProcessorInterface
{
	DocumentProcessor processor;
	
	
	
	
	/**
	 * 
	 * @param documentUrl
	 */
	public DocProcessor(DocumentProcessor processor) 
	{
	
		//loader = new Loader();
		//processor = loader.getProcessor();
		this.processor = processor;

	}
	
	



	public DBDoc extractNamedEntities(String text) {
		Document doc = createTextDocument(text);
		DBDoc namedEntityDoc = null;
		if(doc!=null)
		{
			try 
			{
				processor.processDocument(doc);
				
				AnnotationSet defaultSet = doc.getAnnotations();
				namedEntityDoc = processDefSet(defaultSet,doc);
				System.out.println("HEADING = "+namedEntityDoc.getHeading()+"\n");
				System.out.println("BODY = "+namedEntityDoc.getBody()+"\n");
				System.out.println("location = "+namedEntityDoc.getGeoLocation()+"\n");
				System.out.println("person = "+namedEntityDoc.getPeople()+"\n");
				System.out.println("Organization = "+namedEntityDoc.getCompany()+"\n");
			} 
			catch (GateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return namedEntityDoc;
	}





	public void extractNamedEntities(InputStream inputStream) {
		// TODO Auto-generated method stub
		//IOUtils.w
	}
	

	
		
	/**
	 * 
	 * @return Document 
	 */
	private Document createDocument(String xmlFile)
	{
		Document document = null;
		try 
		{
			
			document = Factory.newDocument(new File(xmlFile).toURI().toURL());
		} 
		catch (ResourceInstantiationException e)
		{
						
			e.printStackTrace();
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			
		}
		return document;
	}
	
	/**
	 * Creates a new textual GATE document from string
	 * @param string
	 * @return
	 */
	private Document createTextDocument(String string)
	{
		Document document = null;
		try {
			document = Factory.newDocument(string);
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	public DocumentProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(DocumentProcessor processor) {
		this.processor = processor;
	}
	
	
	

	public List<DBDoc> extractDocumentFromXML(String xmlFile) 
	{
		
		Document document = createDocument(xmlFile);
		List<DBDoc> listDoc = null;
		if(document!=null)
		{
			try 
			{
				processor.processDocument(document);
				AnnotationSet markupSet = document.getAnnotations("Original markups");
				AnnotationSet defaultSet = document.getAnnotations();
				listDoc = processMarkupSet(markupSet, defaultSet, document);
			} 
			catch (GateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listDoc;
	}
	
	private Set<String> getAnnotationValue(AnnotationSet annotSet, Document doc)
	{
		Set<String> setString  = new HashSet<String>();
		for(Annotation annot:annotSet)
		{
			String annotationStr = Utils.cleanStringFor(doc, annot);
			setString.add(annotationStr);
		}
		return setString;
	}
	
	private DBDoc processDefSet(AnnotationSet defaultSet,Document doc)
	{
		DBDoc dbdoc = new DBDoc();
		AnnotationSet organizationset = defaultSet.get("Organization");
		AnnotationSet personset = defaultSet.get("Person");
		AnnotationSet apersonset = defaultSet.get("APerson");
		AnnotationSet locationset = defaultSet.get("Location");
		
		Set<String> setOrg = getAnnotationValue(organizationset, doc);
		dbdoc.setCompany(setOrg);
		
		Set<String> setLoc = getAnnotationValue(locationset, doc);
		dbdoc.setGeoLocation(setLoc);
		
		Set<String> setPers = getAnnotationValue(personset, doc);
		Set<String> setAPers = getAnnotationValue(apersonset, doc);
		Set<String> nset = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(setPers))
		{
			
			nset.addAll(setPers);
			
			
		}
		if(CollectionUtils.isNotEmpty(setAPers))
		{
			
			nset.addAll(setAPers);
			
			
		}
		
	
			dbdoc.setPeople(nset);

		//Factory.deleteResource(doc);
		return dbdoc;
	}

	/**
	 * 
	 * @param markupSet
	 * @param doc
	 * @return List<DBDoc>
	 */
	private List<DBDoc> processMarkupSet(AnnotationSet markupSet, AnnotationSet defaultSet,Document doc)
	{
		List<DBDoc> dbdocList = new LinkedList<DBDoc>();
		AnnotationSet docSet = markupSet.get("docdata");
		if(CollectionUtils.isNotEmpty(docSet))
		{
			Integer count = 1;
			for(Annotation annot:docSet)
			{
				DBDoc dbDoc = new DBDoc();
				AnnotationSet headset = markupSet.get("headline", annot.getStartNode().getOffset(), annot.getEndNode().getOffset());
				AnnotationSet textset = markupSet.get("text", annot.getStartNode().getOffset(), annot.getEndNode().getOffset());
				
				AnnotationSet organizationset = defaultSet.get("Organization", annot.getStartNode().getOffset(), annot.getEndNode().getOffset());
				Set<String> oset = getValues(organizationset, doc);
				if(CollectionUtils.isNotEmpty(oset))
				{
				dbDoc.setCompany(oset);
				}
				
				AnnotationSet personset = defaultSet.get("Person", annot.getStartNode().getOffset(), annot.getEndNode().getOffset());
				Set<String> pset = getValues(personset, doc);
				if(CollectionUtils.isEmpty(pset))
				{
					
					pset = new HashSet<String>();
					
					
				}
				
				AnnotationSet apersonset = defaultSet.get("APerson", annot.getStartNode().getOffset(), annot.getEndNode().getOffset());
				Set<String> nset = getValues(apersonset, doc);
				if(CollectionUtils.isEmpty(nset))
				{
					
					nset = new HashSet<String>();
					
					
				}
				pset.addAll(nset);
				if(CollectionUtils.isNotEmpty(pset))
				{
					
					dbDoc.setPeople(pset);
					
					
				}
				
				
				AnnotationSet locationset = defaultSet.get("Location", annot.getStartNode().getOffset(), annot.getEndNode().getOffset());
				Set<String> lset = getValues(locationset, doc);
				if(CollectionUtils.isNotEmpty(lset))
				{
					
					dbDoc.setGeoLocation(lset);
					
					
				}
				
				
				String headline = Utils.cleanStringFor(doc, headset);
				String text = Utils.cleanStringFor(doc, textset);
				dbDoc.setBody(text);
				dbDoc.setHeading(headline);
				dbDoc.setId(Integer.toString(count));
				dbdocList.add(dbDoc);
				count++;
			}
			
		}
		Factory.deleteResource(doc);
		return dbdocList;
	}

	
	private Set<String> getValues(AnnotationSet aset, Document doc)
	{
		Set<String> vals = null;
		if(CollectionUtils.isNotEmpty(aset))
		{
			vals = new HashSet<String>();
			for(Annotation annt:aset)
			{
				String strvalue = Utils.cleanStringFor(doc,annt);
				if(StringUtils.isNotBlank(strvalue))
				{
					vals.add(strvalue);
				}
			}
		}
		return vals;
	}
	
	

	
	/**
	 * Main method 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		
		/**
		if(args.length==2)
		{
			DocProcessor d = new DocProcessor(args[0]);
		
			d.doAnalysis(args[1]);
		}
		else
		{
			System.out.println("Please enter the url followed by a space and then the name of a text file for the result report");
		}
		*/
	}






}

