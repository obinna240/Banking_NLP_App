package com.db.tasks.search;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class SearchByContent 
{
	Indexer indexer;
	public SearchByContent()
	{
		indexer = new Indexer(); 
	}
	
	/**
	 * Performs default search
	 * @param indexdir
	 * @throws Exception
	 */
	public void defaultSearch(String indexdir, String saveInDirectory) throws Exception
	{
		StringBuffer strBuffer = new StringBuffer();
		System.out.println("Searching Index located in default directory \'DIR\'" );
		System.out.println("Doing default search \'brexit growth\', \'trump growth\'" );
		IndexSearcher searcher = null;
		if(StringUtils.isBlank(indexdir))
		{
			searcher = indexer.createSearcher();
		}
		else
		{
			searcher = indexer.createSearcher(indexdir);
		}
		System.out.println("Searching for occurences of phrase \'Brexit Growth\'...");
		 strBuffer.append("Searching for occurences of phrase \"Brexit Growth\"...");
		TopDocs fdoc = indexer.searchByContentPhrase("brexit growth", searcher);
		if(fdoc!=null)
        {
	        System.out.println("Total Results for \"Brexit growth\" = " + fdoc.totalHits);
	        strBuffer.append("Total Results for \"Brexit growth\" = " + fdoc.totalHits);
	        strBuffer.append("\n");
	        strBuffer.append("---------------------------------------------------------");
	        strBuffer.append("\n");
	        for (ScoreDoc sd : fdoc.scoreDocs) 
	        {
	            Document d = searcher.doc(sd.doc);
	            System.out.println("The phrase \"Brexit Growth\"' can be found in document "+String.format(d.get("id")));
	            strBuffer.append("The phrase \"Brexit Growth\" can be found in document = "+String.format(d.get("id")));
	            strBuffer.append("\n");
	        }
        }
        else
        {
        	System.out.println("Total Results for \"Brexit Growth\" = 0");
        	strBuffer.append("Total Results for \"Brexit Growth\" = 0");
        	strBuffer.append("\n");
        	
        }
		
		strBuffer.append("---------------------------------------------------------");
        strBuffer.append("\n");
		TopDocs fdoc2 = indexer.searchByContentPhrase("trump growth", searcher);
		strBuffer.append("Searching for occurences of phrase \"Trump growth\"...");
		if(fdoc!=null)
        {
	        System.out.println("Total Results for \"Trump Growth\" = " + fdoc2.totalHits);
	        
	        strBuffer.append("Total Results for \"Trump Growth\" = " + fdoc2.totalHits);
	        strBuffer.append("\n");
	        strBuffer.append("---------------------------------------------------------");
	        strBuffer.append("\n");
	        for (ScoreDoc sd : fdoc2.scoreDocs) 
	        {
	            Document d = searcher.doc(sd.doc);
	            System.out.println("The phrase \"Trump Growth\" can be found in document = "+d.get("id"));
	            
	            strBuffer.append("The phrase \"Trump Growth\" can be found in document = "+d.get("id"));
	            strBuffer.append("\n");
	        }
        }
        else
        {
        	 strBuffer.append("---------------------------------------------------------");
 	        strBuffer.append("\n");
        	System.out.println("Total Results for \"Trump Growth\" = 0");
        	
        	strBuffer.append("Total Results for \"Trump Growth\" = 0");
        	strBuffer.append("\n");
        	
        }
		 strBuffer.append("---------------------------------------------------------");
	        strBuffer.append("\n");
		
		System.out.println("Searching for occurences of \"brexit\" and \"growth\"...");
		
		strBuffer.append("Searching for occurences of \"brexit\" and \"growth\"...");
		
        TopDocs foundDocs = indexer.searchByContent("Brexit Growth", searcher);
        if(foundDocs!=null)
        {
        	 
	        System.out.println("Total Occurrences for \"Brexit Growth\" = " + foundDocs.totalHits);
	        strBuffer.append("Total Occurrences for \"Brexit Growth\" = " + foundDocs.totalHits);
	        strBuffer.append("\n");
	        for (ScoreDoc sd : foundDocs.scoreDocs) 
	        {
	            Document d = searcher.doc(sd.doc);
	            System.out.println("Documents containing \'brexit\' and \'growth\' = "+ String.format(d.get("id")));
	            
	            strBuffer.append("Documents containing \'brexit\' and \'growth\' = "+ String.format(d.get("id")));
	            strBuffer.append("\n");
	        }
        }
        else
        {
        	System.out.println("Total Occurrences for \"Brexit\" and \"growth\" = 0");
        	strBuffer.append("Total Occurrences for \"Brexit\" and \"growth\" = 0");
        	 strBuffer.append("\n");
        }
        strBuffer.append("---------------------------------------------------------");
	        strBuffer.append("\n");
	        strBuffer.append("\n");
	        strBuffer.append("\n");
        System.out.println("Searching for \"Trump\" and \"Growth\"...");
        strBuffer.append("Searching for \"Trump\" and \"Growth\"...");
        strBuffer.append("\n");
        
        TopDocs foundDocs2 = indexer.searchByContent("Trump Growth", searcher);
        if(foundDocs2!=null)
        {
        	System.out.println("\n");
        	 System.out.println("----------------------------------------------------------------");
        	 strBuffer.append("---------------------------------------------------------");
 	        strBuffer.append("\n");
	        System.out.println("Total Occurrences for \"Trump\" and \"Growth\" = " + foundDocs2.totalHits);
	        strBuffer.append("Total Occurrences for \"Trump\" and \"Growth\" = " + foundDocs2.totalHits);
	        strBuffer.append("\n");
	        for (ScoreDoc sd : foundDocs2.scoreDocs) 
	        {
	            Document d = searcher.doc(sd.doc);
	            System.out.println("Documents containing \'Trump\' and \'growth\' = "+String.format(d.get("id")));
	            
	            strBuffer.append("Documents containing \'Trump\' and \'growth\' = "+String.format(d.get("id")));
	            strBuffer.append("\n");
	        }
        }
        else
        {
        	System.out.println("\n");
       	 System.out.println("----------------------------------------------------------------");
        	System.out.println("Total Occurrences for \"Trump\" and \"Growth\" = 0");
        	strBuffer.append("Total Occurrences for \"Trump\" and \"Growth\" = 0");
        	
        }
       String report = strBuffer.toString();
       if(StringUtils.isNotBlank(report))
       {
    	   FileUtils.write(new File(saveInDirectory), report, "utf-8");
       }
	}
	
	/**
	 * Search for your own text
	 * @param indexdir
	 * @param text
	 * @throws Exception
	 */
	public void otherSearch(String indexdir, String text) throws Exception
	{
		System.out.println("Searching Index located in default directory \'DIR\'" );
		System.out.println("Doing default search \'brexit growth\', \'trump growth\'" );
		IndexSearcher searcher = null;
		if(StringUtils.isBlank(indexdir))
		{
			searcher = indexer.createSearcher();
		}
		else
		{
			searcher = indexer.createSearcher(indexdir);
		}
		System.out.println("Searching for "+text+"...");
		
        TopDocs foundDocs = indexer.searchByContent(text, searcher);
        if(foundDocs!=null)
        {
	        System.out.println("Total Results for \""+text+"\" = " + foundDocs.totalHits);
	        for (ScoreDoc sd : foundDocs.scoreDocs) 
	        {
	            Document d = searcher.doc(sd.doc);
	            System.out.println(String.format(d.get("id")));
	        }
        }
        else
        {
        	System.out.println("Total Results for \""+text+"\" = 0");
        	
        }
       
       
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("By default we would do a search for \"Trump grwowth\" and \"Brexit growth\" ");
		System.out.println("We assume that the Lucene index is stored in DIR" );
		System.out.println("Alternatively specify the location of the index" );
		
			
			String indexLocation = "";
			SearchByContent searchByContent = new SearchByContent();
			if(ArrayUtils.isNotEmpty(args))
			{
				indexLocation = args[0];
				searchByContent.defaultSearch(indexLocation, "c:/db/searchreport/savedReport.txt");
				
			}
			if(StringUtils.isBlank(indexLocation))
			{
				
				searchByContent.defaultSearch("c:/db/index", "c:/db/searchreport/savedReport.txt");
				searchByContent.defaultSearch("c:/db/index2", "c:/db/searchreport/savedReport2.txt");
				
			}
	}
}
