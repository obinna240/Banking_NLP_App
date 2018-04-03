package com.db.tasks.parser;

import gate.util.DocumentProcessor;

import java.io.File;
import java.io.IOException;







import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.FileSystemXmlApplicationContext;





import org.springframework.stereotype.Component;

import com.db.tasks.gateUtil.DocProcessor;
import com.db.tasks.objects.DBDoc;
import com.db.tasks.repositories.DBDocRepository;
import com.db.tasks.search.Indexer;



/**
 * 
 * @author oonyimadu
 *
 */
@Component
//@ImportResource("classpath:bean.xml")
public class SaveXmlObjects 
{
	@Autowired
	private DocumentProcessor processor;
	/**
	 * Uncomment this to save in mongo db
	 */
	//@Autowired
	//private DBDocRepository repo;
	
	public void saveXmlObjects(String xmlFile, String directory) throws IOException
	{
		List<DBDoc> listOfDocs = new LinkedList<DBDoc>();
		
		DocProcessor docProcessor = new DocProcessor(processor);
		if(StringUtils.isNotBlank(xmlFile))
		{
			listOfDocs = docProcessor.extractDocumentFromXML(xmlFile);
			
			IndexWriter writer = Indexer.createWriter();
		     List<Document> documents = new ArrayList<>();
			for(DBDoc doc:listOfDocs)
			{
				
				//uncomment this to save in mongodb
				//repo.save(doc);

	            StringBuffer sbuff = new StringBuffer();
	            sbuff.append("=========START HEADING================");
	            sbuff.append("\n");
	            String head = doc.getHeading();
	            sbuff.append(head+"\n");
	            sbuff.append("=========END HEADING================");
	            sbuff.append("\n");
	            sbuff.append("=========START BODY================");
	            sbuff.append("\n");
	            String bod = doc.getBody();
	            sbuff.append(bod+"\n");
	            sbuff.append("\n");
	            sbuff.append("=========END BODY================");
	            
	            
	            sbuff.append("=========START GEO LOCATION================");
	            sbuff.append("\n");
	            sbuff.append(doc.getGeoLocation()+"\n");
	            sbuff.append("\n");
	            sbuff.append("=========END GEO LOCATION================");
	            
	            sbuff.append("=========START PERSONS================");
	            sbuff.append("\n");
	            sbuff.append(doc.getPeople()+"\n");
	            sbuff.append("\n");
	            sbuff.append("=========END PERSONS================");
	            
	            sbuff.append("=========START ORGANIZATIONS================");
	            sbuff.append("\n");
	            sbuff.append(doc.getCompany()+"\n");
	            sbuff.append("\n");
	            sbuff.append("=========END ORGANIZATIONS================");
	            
	            String stringbuffer = sbuff.toString();
	            System.out.println(stringbuffer);
	            String id = doc.getId();
	            
	            if(StringUtils.isBlank(directory))
	            {
	            	FileUtils.write(new File(id+".txt"), stringbuffer, "utf-8");
	            	
	            }
	            else
	            {
	            	
	            	FileUtils.write(new File(directory+"/"+id+".txt"), stringbuffer, "utf-8");
	            }
	            
	            Document newdocument1 = Indexer.createDocument(id,head,bod,doc.getCompany(),doc.getPeople(), doc.getGeoLocation() );
	            documents.add(newdocument1);
			}
			if(CollectionUtils.isNotEmpty(documents))
			 {
				System.out.println("clearing index ...");
				 writer.deleteAll();
				 System.out.println("Adding documents ...");
				 writer.addDocuments(documents);
				 System.out.println("Commiting documents ...");
			     writer.commit();
			     System.out.println("Closing writer ...");
			     writer.close();
			 }
			
		}
	}

			public static void main(String[] args) throws IOException 
			{
				 System.out.println("You must Enter the well formed xml file for parsing and a directory to save the documents and headers. This class will print all documents and headers" );
				 System.out.println("In the absence of a directory, it will write to a directory in the classpath" );
				 System.out.println("This method also indexes the objects in Lucene index called 'DIR' on your class path" );
					
				 String xmlFile = "";
					String directory = "";
					if(ArrayUtils.isNotEmpty(args))
					{
						xmlFile = args[0];
						directory = args[1];
					}

				ApplicationContext context = new FileSystemXmlApplicationContext("c:/db/bean.xml");
				List<DBDoc> listOfDocs = new LinkedList<DBDoc>();
				//Repository repository = context.getBean(DBDocDAOImpl.class);
				
				DocumentProcessor processor = (DocumentProcessor) context.getBean("processor");
				DocProcessor docProcessor = new DocProcessor(processor);
				if(StringUtils.isNotBlank(xmlFile))
				{
					listOfDocs = docProcessor.extractDocumentFromXML(xmlFile);
				}
				else
				{
					listOfDocs = docProcessor.extractDocumentFromXML("reWrittenXml.xml");
				}
				 IndexWriter writer = Indexer.createWriter();
			     List<Document> documents = new ArrayList<>();
				for(DBDoc doc:listOfDocs)
				{
					//repository.saveObject(listOfDocs);
					//FileUtils.writeLines(doc.getId()+".txt", doc.getGeoLocation(), true);
					
				

		            StringBuffer sbuff = new StringBuffer();
		            sbuff.append("=========START HEADING================");
		            sbuff.append("\n");
		            String head = doc.getHeading();
		            sbuff.append(head+"\n");
		            sbuff.append("=========END HEADING================");
		            sbuff.append("\n");
		            sbuff.append("=========START BODY================");
		            sbuff.append("\n");
		            String bod = doc.getBody();
		            sbuff.append(bod+"\n");
		            sbuff.append("\n");
		            sbuff.append("=========END BODY================");
		            
		            
		            sbuff.append("=========START GEO LOCATION================");
		            sbuff.append("\n");
		            sbuff.append(doc.getGeoLocation()+"\n");
		            sbuff.append("\n");
		            sbuff.append("=========END GEO LOCATION================");
		            
		            sbuff.append("=========START PERSONS================");
		            sbuff.append("\n");
		            sbuff.append(doc.getPeople()+"\n");
		            sbuff.append("\n");
		            sbuff.append("=========END PERSONS================");
		            
		            sbuff.append("=========START ORGANIZATIONS================");
		            sbuff.append("\n");
		            sbuff.append(doc.getCompany()+"\n");
		            sbuff.append("\n");
		            sbuff.append("=========END ORGANIZATIONS================");
		            
		            String stringbuffer = sbuff.toString();
		            System.out.println(stringbuffer);
		            String id = doc.getId();
		            
		            if(StringUtils.isBlank(directory))
		            {
		            	FileUtils.write(new File(id+".txt"), stringbuffer, "utf-8");
		            	
		            }
		            else
		            {
		            	
		            	FileUtils.write(new File(directory+"/"+id+".txt"), stringbuffer, "utf-8");
		            }
		            
		            Document newdocument1 = Indexer.createDocument(id,head,bod,doc.getCompany(),doc.getPeople(), doc.getGeoLocation() );
		            documents.add(newdocument1);
				}
				if(CollectionUtils.isNotEmpty(documents))
				 {
					System.out.println("clearing index ...");
					 writer.deleteAll();
					 System.out.println("Adding documents ...");
					 writer.addDocuments(documents);
					 System.out.println("Commiting documents ...");
				     writer.commit();
				     System.out.println("Closing writer ...");
				     writer.close();
				 }
				
			}

			
}

	 

	 			
	 			
	 		