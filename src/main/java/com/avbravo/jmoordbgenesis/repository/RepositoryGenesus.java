/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.jmoordbgenesis.repository;

import com.avbravo.jmoordbbase.internal.DocumentToJavaJmoordbResult;
import com.avbravo.jmoordbbase.internal.DocumentToJavaMongoDB;
import com.avbravo.jmoordbbase.internal.JavaToDocument;
import com.avbravo.jmoordbbase.model.CompositeKey;
import com.avbravo.jmoordbbase.model.DatePatternBeans;
import com.avbravo.jmoordbbase.model.EmbeddedModel;
import com.avbravo.jmoordbbase.model.FieldBeans;
import com.avbravo.jmoordbbase.model.MicroservicesModel;
import com.avbravo.jmoordbbase.model.PrimaryKey;
import com.avbravo.jmoordbbase.model.ReferencedModel;
import com.avbravo.jmoordbbase.model.SecondaryKey;
import com.avbravo.jmoordbbase.model.TertiaryKey;
import com.avbravo.jmoordbgenesis.producer.Loggerenesis;
import com.avbravo.jmoordbgenesis.util.GenesisUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
//@ApplicationScoped
public abstract class RepositoryGenesus<T> implements RepositoryBase {

    // <editor-fold defaultstate="collapsed" desc="field">
        private Class<T> entityClass;
    private String database = "";
    private String collection;
    private Boolean haveElements = Boolean.FALSE;
        T t1, tlocal;
         //lazy load
    private Boolean lazy;
    List<T> list = new ArrayList<>();
    List<PrimaryKey> primaryKeyList = new ArrayList<>();
    List<SecondaryKey> secondaryKeyList = new ArrayList<>();
    List<TertiaryKey> tertiaryKeyList = new ArrayList<>();
    List<CompositeKey> compositeKeyList = new ArrayList<>();

    List<EmbeddedModel> embeddedModelList = new ArrayList<>();
    List<ReferencedModel> referencedModelList = new ArrayList<>();
    List<MicroservicesModel> microservicesModelList = new ArrayList<>();
    List<DatePatternBeans> datePatternBeansList = new ArrayList<>();
    List<FieldBeans> fieldBeansList = new ArrayList<>();
    Exception exception;
    
    /*
    limite de documentos que puede traer un findAll()
    para evitar la sobrecarga
     */
    Integer limitOfDocumentInFindAllMethod = 4000;

    private JavaToDocument javaToDocument = new JavaToDocument();
    private DocumentToJavaMongoDB documentToJava = new DocumentToJavaMongoDB();
    private DocumentToJavaJmoordbResult documentToJavaJmoordbResult = new DocumentToJavaJmoordbResult();

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="@Inject">
    @javax.inject.Inject
    @Loggerenesis
    Logger LOG;
    @Inject
    MongoClient mongoClient;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Repository(Class<T> entityClass, String database, String collection, Boolean... lazy)">
    public RepositoryGenesus(Class<T> entityClass, String database, String collection, Boolean... lazy) {
        try {
              this.entityClass = entityClass;
            this.database = database;
              this.collection = collection;
        } catch (Exception e) {
            LOG.info("Repository() " + e.getLocalizedMessage());
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Repository(Class<T> entityClass, Boolean.. lazy)">

    /**
     * toma el nombre de la base de datos de la configuracion inicial toma el
     * nombre de la coleccion en base al nombre del entity
     *
     * @param entityClass
     * @param lazy
     */
    public RepositoryGenesus(Class<T> entityClass, Boolean... lazy) {
        try {
              this.entityClass = entityClass;
            this.database = database;
              this.collection = collection;
        } catch (Exception e) {
            LOG.info("Repository() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Repository(Class<T> entityClass, String database, String collection, Boolean... lazy)">
    public RepositoryGenesus(Class<T> entityClass, String internalDatabaseHistoryAcronimo, Boolean... lazy) {
        try {
              this.entityClass = entityClass;
            this.database = database;
              this.collection = collection;
        } catch (Exception e) {
            LOG.info("Repository() " + e.getLocalizedMessage());
        }

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="find(String key, Object value">
    @Override
    public Optional<T> find(String key, Object value) {
        try {
            //   Object t = entityClass.newInstance();
            MongoDatabase db = mongoClient.getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(new Document(key, value));

            Consumer<Document> printConsumer = document
                    -> tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList);

            db.getCollection(collection).find(new Document(key, value)).forEach(printConsumer);

            if (tlocal != null) {

                return Optional.of(tlocal);
            }
            return Optional.empty();

        } catch (Exception e) {
       LOG.info(GenesisUtil.nameOfClass() + "."+ GenesisUtil.nameOfMethod()+" = "+ e.getLocalizedMessage());
        }

        return Optional.empty();
    }
    // </editor-fold>
}
