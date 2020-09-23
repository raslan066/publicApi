package apple.base;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;


public class TestBase {
    public static final int RESPONSE_STATUS_CODE_200 = 200;
    public static final int RESPONSE_STATUS_CODE_201 = 201;
    public static final int RESPONSE_STATUS_CODE_400 = 400;
    public static final int RESPONSE_STATUS_CODE_401 = 401;
    public static final int RESPONSE_STATUS_CODE_500 = 500;

    public TestBase(){
//        PropertyConfigurator.configure("log4j.properties");
        //Disables weird extra logging records comes from http request.
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.apache.http");
        root.setLevel(ch.qos.logback.classic.Level.INFO);
    }

    public void logHeaders(ExtentTest reporting, Logger LOGGER, CloseableHttpResponse closeableHttpResponse){
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        log(reporting, LOGGER, null, "Response Headers:");
        log(reporting, LOGGER, null,  "=================");
        for (Header header : headersArray) {
            log(reporting, LOGGER,LogStatus.PASS, header.getName() + ": " + header.getValue());
        }
    }

    protected void log(ExtentTest reporting, Logger LOGGER, LogStatus logStatus, String message) {
        if (logStatus != null) {
            reporting.log(logStatus, message);
            LOGGER.info(logStatus + " " + message);
        } else {
            reporting.log(LogStatus.UNKNOWN, message);
            LOGGER.info(message);
        }
    }
}
