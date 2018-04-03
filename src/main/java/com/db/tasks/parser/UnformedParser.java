package com.db.tasks.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.transform.sax.SAXSource;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.xmlcalabash.core.XProcConfiguration;
import com.xmlcalabash.core.XProcException;
import com.xmlcalabash.core.XProcRuntime;


@Component
public class UnformedParser 
{
	

	
	private XdmNode readAndConvertUnformedXML(String readTextFile) 
	{
		
			XdmNode doc = null;	
			File f = new File(readTextFile); //hard coded
			
			
			
		
			String fs = "";
			try 
			{
				fs = FileUtils.readFileToString(f, "utf-8");
				StringReader inputStream = new StringReader(fs);
			    InputSource source = new InputSource(inputStream);
			   Parser parser = new Parser();
			   XProcRuntime runtime = new XProcRuntime(new XProcConfiguration());
			    parser.setEntityResolver(runtime.getResolver());
			    SAXSource saxSource = new SAXSource(parser, source);
			    net.sf.saxon.s9api.DocumentBuilder builder = runtime.getProcessor().newDocumentBuilder();
			  
			        
					try {
						doc = builder.build(saxSource);
					} catch (SaxonApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        
			 
			} 
			catch (IOException e1)
			{
				System.out.println("");
				e1.printStackTrace();
			}
			
			return doc; 
	}
	
	/**
	 * Converts unformed Text File to Well formed XML
	 * @param readUnformedTextFile
	 * @param writeWellFormedXml
	 */
	public void convertToWellFormedXml(String readUnformedTextFile, String writeWellFormedXml)
	{
		XdmNode xdmObject =  readAndConvertUnformedXML(readUnformedTextFile);
		try 
		{
			if(xdmObject!=null)
			{
				FileUtils.write(new File(writeWellFormedXml), xdmObject.toString(), "UTF-8");
			}
			else
			{
				System.out.println("Object is null");
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void convertToWellFormedXml(InputStream ins, String writeWellFormedXml)
	{
		try {
			String string = IOUtils.toString(ins, "utf-8");
			convertToWellFormedXml(string, writeWellFormedXml);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{try {
			ins.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
	}
	public static void main(String[] args)
	{
		//System.out.println("Enter text file i.e sample_news.txt and the name of the xml file to return e.g sample_news.xml" );
		
		String sampleText = "";
		String writeXml = "";
		if(ArrayUtils.isNotEmpty(args))
		{
		sampleText = args[0];
		writeXml = args[1];
		}
		
		UnformedParser unformedParser = new UnformedParser();
		if(StringUtils.isNotBlank(sampleText) && StringUtils.isNotBlank(writeXml))
		{
			
			unformedParser.convertToWellFormedXml(sampleText, writeXml);
		}
		else if(StringUtils.isBlank(sampleText) && StringUtils.isBlank(writeXml))
		{
			unformedParser.convertToWellFormedXml("c:/db/sample_news.txt", "c:/db/rewrittenxml1.xml");
			System.out.println("You must provide sample text and the name of an xml file to write to. However, the default sample_news.txt file will be written to a well fomrmed xml -- rewrittenXml1 ");
		}
	}
}

