package com.despegar.integration.mongo.connector;

import java.util.List;

import org.apache.commons.lang.mutable.MutableInt;

import com.despegar.integration.domain.api.IdentificableEntity;
import com.despegar.integration.mongo.connector.HandlerQuery.UpdateOperation;


/**
 * Basic Handlers operations
 */
public interface Handler<T extends IdentificableEntity> {

    /**
     * Returns {@link IdentificableEntity} class instance by id
     */
    T get(String id);

    /**
     * Returns the first {@link IdentificableEntity} class instance in the collection
     */
    T getOne();

    /**
     * Returns the {@link IdentificableEntity} class instance that match the query
     */
    T getOne(HandlerQuery query);

    /**
     * Returns all {@link IdentificableEntity} class instances
     */
    List<T> getAll();

    /**
     * Returns all {@link IdentificableEntity} class instances that match the query
     */
    List<T> getAll(HandlerQuery query);


    /**
     * @param query
     * @param count passed as an out parameter to get the count
     * @return
     * 
     * Returns all {@link IdentificableEntity} class instances that match the query
     */
    List<T> getAll(HandlerQuery query, MutableInt count);

    /**
     * Store {@link IdentificableEntity} instance. If it already exist, it will create a new one.
     * @return the id of the {@link IdentificableEntity} instance 
     */
    String add(T t);

    /**
     * Stores {@link IdentificableEntity} instance. If it already exists and an id is set, it will update the old one.
     * 
     *  @return This object (which is already a string!).
     */
    String save(T t);

    /**
     * It removes the {@link IdentificableEntity} instance.
     */
    void remove(String id);

    /**
     * It removes all the elements
     */
    void removeAll();

    /**
     * Returns the total number of elements for the selected query
     */
    Integer count(HandlerQuery query);


    /**
     * Returns distinct values for a key
     * @param key
     * @return
     */
    List<?> distinct(String key);

    /**
     * Updates a collection based on the updateQuery
     * 
     * @param query - Query to filter the documents to update
     * @param updateQuery - Query to modify the document, can be a document or use one of the {@link UpdateOperation}.
     * @param upsert - Whether to create a new instance if no instance matches the filter query.
     * @return
     */
    String update(final HandlerQuery query, final HandlerQuery updateQuery, boolean upsert);

    /**
     * Remove a set of elements of the collection that match the query    
     * @param query
     */
    public abstract void remove(HandlerQuery query);

    public abstract String insertIfNotPresent(final T t);
}
