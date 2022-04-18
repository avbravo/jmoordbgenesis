/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.avbravo.jmoordbgenesis.repository;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public interface RepositoryBase<T>  {
    
        public MongoDatabase getMongoDatabase();
    public T find(String key, Object value);

    public T find(Document document);

    public T findFirst(Document... document);
}
