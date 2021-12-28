package awesome.logback.appender.mongo;

import java.io.IOException;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import lombok.Getter;

/**
 * 
 * @author awesome
 */
public class MongoAddapterImpl implements MongoAddapter {

    private final MongoClient mongoClient;
    @Getter
    private final MongoCollection<Document> logCollection;

    MongoAddapterImpl(String connectionString, String userName, char[] password, String database, String logDb,
            String logCollectionStr) {
        MongoCredential mongoCredential = MongoCredential.createCredential(userName, database, password);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().credential(mongoCredential)
                .applyConnectionString(new ConnectionString(connectionString)).build();
        mongoClient = MongoClients.create(mongoClientSettings);
        logCollection = mongoClient.getDatabase(logDb).getCollection(logCollectionStr);
    }

    @Override
    public void close() throws IOException {
        mongoClient.close();
    }
}
