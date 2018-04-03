package com.db.tasks.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;



@Component
//@Order(1)
public class RunUnformedParser 
{
	@Autowired
	ResourceLoader loader;
	
	
/**
	@Override
	public void run(String... args) throws Exception {
		UnformedParser unformedParser = new UnformedParser();
		System.out.println("Note Files are saved in your classpath...");
    	System.out.println("Transforming sample news.txt using GATE");
    
    	
    	unformedParser.convertToWellFormedXml("string", "classpath:resources/reWrittenXml1.xml");
    	
    	System.out.println("Sample_news.txt is converted to reWrittenXml1.xml using GATE");
    	
    	System.out.println("=============CONVERSION TO WELL FORMED XML ENDED =============");
		
	}
*/
}



