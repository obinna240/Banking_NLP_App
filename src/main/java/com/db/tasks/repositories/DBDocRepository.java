package com.db.tasks.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.db.tasks.objects.DBDoc;


public interface DBDocRepository extends CrudRepository<DBDoc, String>, DBDocOperations
{
	
	
	

}



