package awesome.logback.appender.mongo;

/**
 * 
 * @author awesome
 */
interface MongoFactoyAddapter {

    public String getClassName();

    public MongoAddapter create();
}
