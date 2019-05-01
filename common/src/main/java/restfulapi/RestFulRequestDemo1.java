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
public class RestFulRequestDemo1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String stubsApiBaseUri = "http://10.194.110.37:7250/error500/predict_data";
        String component = "meeting";
        String servertype = "mngsvr";
        String errortype = "mngmres";
        String cluster = "AA";
        String pointNum = "1";
        String date = "2019-04-28 13:55:00";

        HttpClient client = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(stubsApiBaseUri);
        builder.addParameter("date", date);
        builder.addParameter("component", component);
        builder.addParameter("servertype", servertype);
        builder.addParameter("errortype", errortype);
        builder.addParameter("cluster", cluster);
        builder.addParameter("point_num", pointNum);

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
