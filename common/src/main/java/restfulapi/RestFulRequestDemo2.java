package restfulapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
/**
 * Created by yidxue on 2019/4/30
 */
public class RestFulRequestDemo2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String stubsApiBaseUri = "http://10.194.110.37:7250/description";
        HttpClient client = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(stubsApiBaseUri);
        String listStubsUri = builder.build().toString();
        HttpGet getStubMethod = new HttpGet(listStubsUri);
        HttpResponse getStubResponse = client.execute(getStubMethod);
        int getStubStatusCode = getStubResponse.getStatusLine()
                                    .getStatusCode();
        if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
            return;
        }
        String responseBody = EntityUtils.toString(getStubResponse.getEntity());
        System.out.println(responseBody);
    }
}
