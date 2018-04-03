package com.db.tasks.search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	
	private static String INDEX_DIR = "c:/db/index";
	private static String INDEX_DIR2 = "c:/db/index2";
	/**
	private  String INDEX_DIR;
	
	public Indexer(String INDEX_DIRECTORY)
	{
		this.INDEX_DIRECTORY = INDEX_DIRECTORY;
	}
	*/
	public static IndexWriter createWriter() throws IOException 
	{
	    FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
	    IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
	    IndexWriter writer = new IndexWriter(dir, config);
	    return writer;
	}
	
	public static IndexWriter createWriter2() throws IOException 
	{
	    FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR2));
	    IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
	    IndexWriter writer = new IndexWriter(dir, config);
	    return writer;
	}
	
	public static Document createDocument(String id, String header, String content) 
	{
	    Document document = new Document();
	    document.add(new StringField("id", id , Field.Store.YES));
	    document.add(new TextField("header", header , Field.Store.YES));
	    document.add(new TextField("content",content , Field.Store.YES));
	   
	    return document;
	}
	
	public static Document createDocument(String id, String header, String content, Set<String> organization, Set<String>  person, Set<String>  location) 
	{
		Document document = createDocument(id, header, content);
		if(CollectionUtils.isNotEmpty(organization))
		{
		   for(String organiz:organization)
		   {
			   document.add(new StringField("organization", organiz , Field.Store.YES));
		   }
		}
		if(CollectionUtils.isNotEmpty(person))
		{
		   for(String organiz:person)
		   {
			   document.add(new StringField("person", organiz , Field.Store.YES));
		   }
		}
		if(CollectionUtils.isNotEmpty(location))
		{
		   for(String organiz:location)
		   {
			   document.add(new StringField("location", organiz , Field.Store.YES));
		   }
		}
	    return document;
	}
	
	public static IndexSearcher createSearcher() throws IOException 
	{
	    Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
	    IndexReader reader = DirectoryReader.open(dir);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    return searcher;
	}
	
	public static IndexSearcher createSearcher2() throws IOException 
	{
	    Directory dir = FSDirectory.open(Paths.get(INDEX_DIR2));
	    IndexReader reader = DirectoryReader.open(dir);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    return searcher;
	}
	/**
	 * Searches for bigrams
	 * @param searchTerm
	 * @param searcher
	 * @return
	 * @throws Exception
	 */
	public static TopDocs searchByContent(String searchTerm, IndexSearcher searcher) throws Exception
	{
	   QueryParser qp = new QueryParser("content", new StandardAnalyzer());
	  Query termQuery = qp.parse(searchTerm);

	    TopDocs hits = searcher.search(termQuery, 30);
	    return hits;
	}
	
	public static TopDocs searchByName(String searchTerm, IndexSearcher searcher) throws Exception
	{
	   QueryParser qp = new QueryParser("person", new StandardAnalyzer());
	  Query termQuery = qp.parse(searchTerm);

	    TopDocs hits = searcher.search(termQuery, 30);
	    if(hits!=null)
	    	printResult(hits, searcher);
	    return hits;
	}
	
	public static TopDocs searchByLocation(String searchTerm, IndexSearcher searcher) throws Exception
	{
	   QueryParser qp = new QueryParser("location", new StandardAnalyzer());
	  Query termQuery = qp.parse(searchTerm);

	    TopDocs hits = searcher.search(termQuery, 30);
	    if(hits!=null)
	    	printResult(hits, searcher);
	    return hits;
	}
	
	public static TopDocs searchByOrganization(String searchTerm, IndexSearcher searcher) throws Exception
	{
	   QueryParser qp = new QueryParser("organization", new StandardAnalyzer());
	  Query termQuery = qp.parse(searchTerm);

	    TopDocs hits = searcher.search(termQuery, 30);
	    if(hits!=null)
	    	printResult(hits, searcher);
	    return hits;
	}
	
	/**
	 * Searches for a particular phrase
	 * @param searchTerm
	 * @param searcher
	 * @return
	 * @throws Exception
	 */
	public static TopDocs searchByContentPhrase(String searchTerm, IndexSearcher searcher) throws Exception
	{
		String[] arr = searchTerm.split("\\s");
		PhraseQuery q = new PhraseQuery("content", arr);
		TopDocs hits = searcher.search(q, 60);
	   
	    return hits;
	}
	
	public  IndexSearcher createSearcher(String IndexDirectory) throws IOException 
	{
	    Directory dir = FSDirectory.open(Paths.get(IndexDirectory));
	    IndexReader reader = DirectoryReader.open(dir);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    return searcher;
	}
	
	private static void printResult(TopDocs hits, IndexSearcher searcher)
	{
		try {
			
			for (ScoreDoc sd : hits.scoreDocs) 
			{
				
		
				Document d = searcher.doc(sd.doc);
				System.out.println("=============START RESULTS==========");
				System.out.println(String.format(d.get("id")));
				System.out.println("\n");
				System.out.println("=============ORGANIZATION==========");
				System.out.println(d.get("organization"));
				System.out.println("=============LOCATION==========");
				System.out.println(String.format(d.get("location")));
				System.out.println("=============PERSON==========");
				System.out.println(String.format(d.get("person")));
				System.out.println("=============END RESULT==========");
			} 
			
			}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
     
	}
	/**
	 * Tester
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
    {
        IndexWriter writer = createWriter();
       List<Document> documents = new ArrayList<>();
         
       // Document document1 = createDocument("1","Asian Stocks Rise on Improved Investor Sentiment", "Asian Stocks Rise on Improved Investor Sentiment");
       // documents.add(document1);
         
       // Document document2 = createDocument("1","Asian Stocks Rise on Improved Investor Sentiment", "Asian Stocks Rise on Improved Investor Sentiment");
       // documents.add(document2);
         
        //Let's clean everything first
       // writer.deleteAll();
         
       // writer.addDocuments(documents);
       // writer.commit();
      //  writer.close();
        
       IndexSearcher searcher = createSearcher();
       /**
        
       TopDocs foundDocs = searchByContent("brexit growth", searcher);
       System.out.println("Total Results :: " + foundDocs.totalHits);
       for (ScoreDoc sd : foundDocs.scoreDocs) 
       {
           Document d = searcher.doc(sd.doc);
           System.out.println(String.format(d.get("id")));
       }
        */
       TopDocs hits =  searchByContentPhrase("Brexit growth",searcher);
        //String[] arr = {"brexit","growth"};
	   // PhraseQuery q = new PhraseQuery("content", arr);
	   // TopDocs hits = searcher.search(q, 70);
        
        for (ScoreDoc sd : hits.scoreDocs) 
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("id")));
        }
      
    }
}
