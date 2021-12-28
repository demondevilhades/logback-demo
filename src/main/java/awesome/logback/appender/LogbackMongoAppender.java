package awesome.logback.appender;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;

import awesome.logback.appender.mongo.MongoBinder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * create: 
 * <pre>
 * local.createCollection("app_log")
 * local.app_log.createIndex( {level: 1} )
 * local.app_log.createIndex( {time_stamp: 1} )
 * local.app_log.createIndex( {logger_name: 1} )
 * local.app_log.createIndex( {formatted_message: 1} )
 * local.app_log.createIndex( {"caller_data.class_name": 1, "caller_data.method_name": 1, "caller_data.line_number": 1} )
 * local.app_log.createIndex( {"throwable_proxy.class_name": 1, "throwable_proxy.message": 1} )
 * </pre>
 * 
 * find:
 * <pre>
 * {"datetime":{$dateToString:{format:"%Y-%m-%d %H:%M:%S.%L","date":{"$add":[new Date(0), "$time_stamp", 28800000]}}},"formatted_message":1}
 * </pre>
 * 
 * @author awesome
 */
public class LogbackMongoAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    
    public static MongoCollection<Document> logCollection;

    private final String KEY_FORMATTED_MESSAGE = "formatted_message";
    private final String KEY_LEVEL = "level";
    private final String KEY_LOGGER_CONTEXT_VO = "logger_context_vo";
    private final String KEY_LOGGER_NAME = "logger_name";
    private final String KEY_MARKER = "marker";
    private final String KEY_THREAD_NAME = "thread_name";
    private final String KEY_TIME_STAMP = "time_stamp";
    private final String KEY_HAS_CALLER_DATA = "has_caller_data";
    private final String KEY_THREAD = "thread";
    private final String KEY_MDC = "mdc";

    private final String KEY_CALLER_DATA_CLASS_NAME = "caller_data.class_name";
    private final String KEY_CALLER_DATA_METHOD_NAME = "caller_data.method_name";
    private final String KEY_CALLER_DATA_LINE_NUMBER = "caller_data.line_number";
    private final String KEY_CALLER_DATA_FILE_NAME = "caller_data.file_name";

    private final String KEY_THROWABLE_PROXY_CLASS_NAME = "throwable_proxy.class_name";
    private final String KEY_THROWABLE_PROXY_MESSAGE = "throwable_proxy.message";
    private final String KEY_THROWABLE_PROXY_COMMON_FRAMES = "throwable_proxy.common_frames";
    
    public LogbackMongoAppender() {
        super();
    }

    @Override
    public void start() {
        logCollection = MongoBinder.getSingleton().getMongoAddapterImpl().getLogCollection();
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if(logCollection == null) {
            return;
        }
        
        try {
            Document document = new Document();
            document
                .append(KEY_FORMATTED_MESSAGE, iLoggingEvent.getFormattedMessage())
                .append(KEY_LEVEL, iLoggingEvent.getLevel().levelStr)
                .append(KEY_LOGGER_CONTEXT_VO, iLoggingEvent.getLoggerContextVO().getName())
                .append(KEY_LOGGER_NAME, iLoggingEvent.getLoggerName())
                .append(KEY_THREAD_NAME, iLoggingEvent.getThreadName())
                .append(KEY_TIME_STAMP, iLoggingEvent.getTimeStamp())
                .append(KEY_HAS_CALLER_DATA, iLoggingEvent.hasCallerData())
                .append(KEY_THREAD, Thread.currentThread().getName())
                .append(KEY_MARKER, (iLoggingEvent.getMarker() == null) ? null : iLoggingEvent.getMarker().getName())
                .append(KEY_MDC, (iLoggingEvent.getMDCPropertyMap() == null) ? null : iLoggingEvent.getMDCPropertyMap())
//              .append("argument_array", iLoggingEvent.getArgumentArray())
//              .append("message", iLoggingEvent.getMessage())
//              .append("caller_data", iLoggingEvent.getCallerData())
//              .append("throwable_proxy", iLoggingEvent.getThrowableProxy())
                ;

            StackTraceElement[] cds = iLoggingEvent.getCallerData();
            if (cds != null && cds.length > 0) {
                document.append(KEY_CALLER_DATA_CLASS_NAME, cds[0].getClassName())
                        .append(KEY_CALLER_DATA_METHOD_NAME, cds[0].getMethodName())
                        .append(KEY_CALLER_DATA_LINE_NUMBER, cds[0].getLineNumber())
                        .append(KEY_CALLER_DATA_FILE_NAME, cds[0].getFileName());
            }
            IThrowableProxy iThrowableProxy = iLoggingEvent.getThrowableProxy();
            if (iThrowableProxy != null) {
                document.append(KEY_THROWABLE_PROXY_CLASS_NAME, iThrowableProxy.getClassName())
                        .append(KEY_THROWABLE_PROXY_MESSAGE, iThrowableProxy.getMessage())
                        .append(KEY_THROWABLE_PROXY_COMMON_FRAMES, iThrowableProxy.getCommonFrames());
            }
            
            InsertOneResult insertOneResult = logCollection.insertOne(document);
            if(!insertOneResult.wasAcknowledged()) {
                System.err.println("unacknowledged");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
