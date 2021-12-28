package awesome.logback.appender.mongo;

import awesome.logback.util.Config;

/**
 * 
 * @author awesome
 */
class MongoFactoyAddapterImpl implements MongoFactoyAddapter {

    private static MongoFactoyAddapterImpl mongoFactoyAddapterImpl = new MongoFactoyAddapterImpl();

    MongoFactoyAddapterImpl() {
    }

    public static MongoFactoyAddapterImpl instance() {
        return mongoFactoyAddapterImpl;
    };

    @Override
    public String getClassName() {
        return this.getClass().getName();
    }

    @Override
    public MongoAddapter create() {
        return new MongoAddapterImpl(Config.get("mongo.client.conn"), Config.get("mongo.client.username"),
                Config.get("mongo.client.password").toCharArray(), Config.get("mongo.client.db"),
                Config.get("mongo.log.db"), Config.get("mongo.log.collection"));
    }
}
