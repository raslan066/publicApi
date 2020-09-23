package apple.apitests;

import apple.data.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import apple.base.TestBase;
import apple.client.RestClient;
import apple.utilities.ConfigurationReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class APITests extends TestBase {

    TestBase testBase;
    String serviceURL;
    String apiURL;
    String URL;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    public static ExtentReports extent;
    public static ExtentTest reporting;
    static Logger LOGGER = Logger.getLogger(APITests.class.getSimpleName());
    public Properties prop;

    @BeforeSuite
    public void startTest() {
        String workingDir = System.getProperty("user.dir");
        extent = new ExtentReports(workingDir + ConfigurationReader.getProperty("reportPath") + "Get-API-tests-reports.html", true);
        extent.loadConfig(new File(workingDir + ConfigurationReader.getProperty("reportConfigPath")));
    }

    @BeforeMethod
    public void setup() {
        testBase = new TestBase();
        serviceURL = ConfigurationReader.getProperty("URL");
        apiURL = ConfigurationReader.getProperty("serviceURL");
        URL = serviceURL + apiURL;
    }

    /**
     * User can Login with a valid username and password using "/login" endpoint of the API
     */
    @Test(groups = {"smoke", "login"}, priority = 1)
    public void login(Method method) throws IOException {
        reporting = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), "User can Login with a valid username and password using \"/login\" endpoint of the API");
        log(null, "---------------------------- Executing " + method.getName() + " ----------------------------");
        restClient = new RestClient();
        String username = "logger";
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("api-key", "special-key");
        headerMap.put(username, "logger");
        headerMap.put("password", "abc123");
        closeableHttpResponse = restClient.get(URL + "/login", headerMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code was not 200");
        log(LogStatus.PASS, username + " user has been logged in successfully!");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        Assert.assertTrue(responseString.contains("logged in user session:"));
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        log(null, "Response Headers:");
        log(null, "=================");
        logHeaders();
    }

    /**
     * User can logout by using "/logout" end point of the API
     */
    @Test(groups = {"smoke", "logout"}, priority = 2)
    public void logout(Method method) throws IOException {
        reporting = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), "User can logout by using \"/logout\" end point of the API");
        log(null, "---------------------------- Executing " + method.getName() + " ----------------------------");
        restClient = new RestClient();
        Map<String, String> headerMap = new HashMap<String, String>();

        closeableHttpResponse = restClient.get(URL + "/logout", headerMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code was not 200");
        log(LogStatus.PASS, "Logged out successfully!");
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        log(null, "Response Headers:");
        log(null, "=================");
        logHeaders();
    }

    @Test(groups = {"smoke", "update"}, priority = 1)
    public void updateAUser(Method method) throws IOException {
        reporting = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), "User can update all information with a given userName using by /{userName} end point");
        log(null, "---------------------------- Executing " + method.getName() + " ----------------------------");
        restClient = new RestClient();
        String userName = "logger";
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("username", "logger");
        headerMap.put("password", "abc123");

        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users(1234, userName, "Resul", "Aslan", "abc@abc.com", "password", "1232344567", 1234567);

        String usersJSonString = mapper.writeValueAsString(users);

        closeableHttpResponse = restClient.put(URL + "/" + userName, usersJSonString, headerMap);

        //1. get Status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
        log(LogStatus.PASS, userName + " user has been updated in successfully!");
        logHeaders();
    }

    /**
     * User can delete a user using by /{userName} end point
     */
    @Test(groups = {"smoke", "delete"}, priority = 1)
    public void deleteAUser(Method method) throws IOException {
        reporting = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), "User can delete a user using by /{userName} end point");
        log(null, "---------------------------- Executing " + method.getName() + " ----------------------------");
        restClient = new RestClient();
        String userName = "logger";
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("username", "logger");
        headerMap.put("password", "abc123");

        ObjectMapper mapper = new ObjectMapper();
        closeableHttpResponse = restClient.delete(URL + "/" + userName);

        //1. get Status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
        log(LogStatus.PASS, userName + " user has been deleted in successfully!");
        logHeaders();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log(LogStatus.FAIL,"Test Case Failed is " + result.getName());
            log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
        }
        extent.endTest(reporting);
    }

    @AfterTest
    public void afterSuite() {
        extent.flush();
        extent.close();
    }

    private void logHeaders(){
        logHeaders(reporting, LOGGER, closeableHttpResponse);
    }

    private void log(LogStatus logStatus, String message){
        log(reporting, LOGGER, logStatus, message);
    }

}
