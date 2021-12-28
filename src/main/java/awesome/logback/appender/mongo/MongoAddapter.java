package awesome.logback.appender.mongo;

import java.io.Closeable;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

/**
 * 
 * @author awesome
 */
public interface MongoAddapter extends Closeable {

    public MongoCollection<Document> getLogCollection();
}
