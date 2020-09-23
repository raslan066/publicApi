package apple.client;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Map;

public class RestClient {

    // 1. Get request without Headers
    public CloseableHttpResponse get(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        return httpClient.execute(httpGet);
    }

    // 2. Get request with Headers
    public CloseableHttpResponse get(String url, Map<String, String> headerMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for(Map.Entry<String, String> e: headerMap.entrySet()){
            httpGet.addHeader(e.getKey(),e.getValue());
        }
        return httpClient.execute(httpGet);
    }

    // 3. Post Request with Headers
    public CloseableHttpResponse post(String url, String entityString, Map<String, String> headerMap ) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url); //http Post request
        httpPost.setEntity(new StringEntity(entityString)); //payload

        //for Headers
        for(Map.Entry<String, String> e: headerMap.entrySet()){
            httpPost.addHeader(e.getKey(),e.getValue());
        }

        return httpClient.execute(httpPost);
    }

    // 4. Put Request with Headers
    public CloseableHttpResponse put(String url, String entityString, Map<String, String> headerMap ) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url); //http Post request
        httpPut.setEntity(new StringEntity(entityString)); //payload

        //for Headers
        for(Map.Entry<String, String> e: headerMap.entrySet()){
            httpPut.addHeader(e.getKey(),e.getValue());
        }

        return httpClient.execute(httpPut);
    }


    public CloseableHttpResponse delete(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        return httpClient.execute(httpDelete);
    }


}
