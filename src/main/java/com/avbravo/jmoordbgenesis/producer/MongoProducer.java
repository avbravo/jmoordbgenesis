/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.jmoordbgenesis.producer;

import com.avbravo.jmoordbgenesis.microprofile.MicroprofileConfigFile;
import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.io.Serializable;
import java.util.Map;
import javax.inject.Inject;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.microprofile.config.Config;
import java.util.logging.Logger;


/**
 *
 * @author avbravo
 */
@Stateless
//@ApplicationScoped
public class MongoProducer implements Serializable{
    
    @Inject @Loggerenesis
    Logger LOG;
    
        @Inject
    private Config config;
    
    @ApplicationScoped
       @Produces
      public MongoClient mongoClient() {
        try {
            
              final Map<String, String> properties = MicroprofileConfigFile.getProperties(this.config);
            LOG.info(
                    "Loading default MongoClient  loading from MicroProfile Config properties. The keys: "
                    + properties.keySet()
            );

            //final EmbeddedStorageConfigurationBuilder builder = EmbeddedStorageConfigurationBuilder.New();
            for (final Map.Entry<String, String> entry : properties.entrySet()) {
                System.out.println("key " + entry.getKey() + " value " + entry.getValue());
                //builder.set(entry.getKey(), entry.getValue());
            }
            
            ConnectionString uri = new ConnectionString("mongodb://host1:27017,host2:27017,host3:27017/");
       //   String uri = "mongodb://user:pass@sample.host:27017/?maxPoolSize=20&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("admin");
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Connected successfully to server.");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
      }
}
