package com.db.tasks.parser;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.SAXException;




import com.db.tasks.objects.DocStructure;
import com.db.tasks.search.Indexer;


/**
 * This class takes the well formed xml and prints out the heading and content of the document
 * You can also specify to write each header and content to a diretory
 * @author oonyimadu
 *
 */
public class XML_Parser
{
	 public static void main(String[] args) 
	 {
		 String xmlFile = "";
		 String directory = "";
		 if(ArrayUtils.isEmpty(args))
		 {
			 System.out.println("Enter the well formed xml file for parsing and a directory to save the documents and headers. This class will print all documents and headers" );
			 System.out.println("In the absence of a directory, it will write to a directory in the classpath" );
			 System.out.println("This method also indexes the objects in Lucene index called 'DIR' on your class path" );
			 
			 xmlFile = "c:/db/rewrittenxml1.xml";
			directory = "c:/db/xml_parsed_Document_With_Headers";
			}
		 else 
		 {
			 if(args.length == 2)
			 {
		 System.out.println("Enter the well formed xml file for parsing and a directory to save the documents and headers. This class will print all documents and headers" );
		 System.out.println("In the absence of a directory, it will write to a directory in the classpath" );
		 System.out.println("This method also indexes the objects in Lucene index called 'DIR' on your class path" );
			

		 xmlFile = args[0];
		 directory = args[1];
			 }
		 }
		
		    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		    
				try
				{
					javax.xml.parsers.SAXParser saxParser = saxParserFactory.newSAXParser();
					 DBDocumentHandler handler = new DBDocumentHandler();
					 saxParser.parse(new File(xmlFile), handler);
					 List<DocStructure> docList = handler.getDocList();
					 
					 IndexWriter writer = Indexer.createWriter2();
				     List<Document> documents = new ArrayList<>();
				     
				     
				     
					 Integer id = 1;
					 for(DocStructure dT : docList)
				        {
				        	
				            String bd = dT.getBody();
				            bd = StringUtils.trimToEmpty(StringUtils.normalizeSpace(bd));
				          
				            
				            StringBuffer sbuff = new StringBuffer();
				            sbuff.append("=========START HEADING================");
				            sbuff.append("\n");
				            String head = StringUtils.normalizeSpace(dT.getHeading());
				            sbuff.append(head);
				            sbuff.append("=========END HEADING================");
				            sbuff.append("\n");
				            sbuff.append("=========START BODY================");
				            sbuff.append("\n");
				            sbuff.append(bd);
				            sbuff.append("\n");
				            sbuff.append("=========END BODY================");
				            String stringbuffer = sbuff.toString();
				            System.out.println(stringbuffer);
				            
				            if(StringUtils.isBlank(directory))
				            {
				            	FileUtils.write(new File(id+".txt"), stringbuffer, "utf-8");
				            }
				            else
				            {
				            	
				            	FileUtils.write(new File(directory+"/"+id+".txt"), stringbuffer, "utf-8");
				            }
				            
				            //write to index
				            
				            Document newdocument1 = Indexer.createDocument(id.toString(),head,bd);
				            documents.add(newdocument1);
				            
				            id++;
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
				catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
		         catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        
		 }  
		        
		
	 
}
