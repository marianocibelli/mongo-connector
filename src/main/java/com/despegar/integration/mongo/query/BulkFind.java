package com.despegar.integration.mongo.query;

import com.despegar.integration.mongo.entities.Bulkeable;
import com.despegar.integration.mongo.entities.GenericIdentifiableEntity;
import com.despegar.integration.mongo.query.BulkOperation;

public class BulkFind<T extends GenericIdentifiableEntity<?>> implements Bulkeable{
	
	private Query query;
	private BulkOperation operation;
	private Update update;
	private T entity;
	
	public BulkFind(Query query){
		this.query = query;
	}
	
	
	public void remove(){
		this.operation = BulkOperation.REMOVE;
	}
	
	public void update(Update update){
		this.update = update;
		this.operation = BulkOperation.UPDATE;
	}
	
	public void removeOne(){
		this.operation = BulkOperation.REMOVE_ONE;
	}
	
	public void updateOne(Update update){
		this.update = update;
		this.operation = BulkOperation.UPDATE_ONE;
	}
	
	public void replaceOne(T entity){
		this.entity = entity;
		this.operation = BulkOperation.REPLACE_ONE;
	}
	
	public Query getQuery() {
		return query;
	}

	
	@Override
	public BulkOperation getOperation() {
		return operation;
	}


	public Update getUpdate() {
		return update;
	}


	public T getEntity() {
		return entity;
	}
	
}
