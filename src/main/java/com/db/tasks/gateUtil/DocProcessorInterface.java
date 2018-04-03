package com.db.tasks.gateUtil;

import java.io.InputStream;
import java.util.List;

import com.db.tasks.objects.DBDoc;



public interface DocProcessorInterface 
{
	
	public List<DBDoc> extractDocumentFromXML(String xmlFile);
	public DBDoc extractNamedEntities(String text);
	public void extractNamedEntities(InputStream inputStream);
}
