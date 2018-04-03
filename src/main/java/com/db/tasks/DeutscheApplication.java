package com.db.tasks;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.db.tasks.parser.SaveXmlObjects;
import com.db.tasks.parser.UnformedParser;


@SpringBootApplication
@ImportResource("classpath:bean.xml")
public class DeutscheApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeutscheApplication.class, args);
	}
	
	/**
	@Bean
	    CommandLineRunner init(SaveXmlObjects  saveXmlObject) {

        return args -> {
        	System.out.println("Note Files are saved in your classpath...");
        	System.out.println("Transforming sample news.txt using GATE");
        	//Resource resource = new ClassPathResource("sample_news.txt");
        	//ResourceLoader loader = 
        	//Resource loader = 
        	//String file = ResourceLoader.getSystemResource("classpath:resources/reWrittenXml1.xml").getFile();
        			
    
        	//InputStream in = getClass().getResourceAsStream("/file.txt"); 
        	saveXmlObject.saveXmlObjects("reWrittenXml1.xml", "dir");
        	
        	System.out.println("Sample_news.txt is converted to reWrittenXml1.xml using GATE");
        	
        	System.out.println("=============CONVERSION TO WELL FORMED XML ENDED =============");
           
        };
        
	}
	*/
	
}

