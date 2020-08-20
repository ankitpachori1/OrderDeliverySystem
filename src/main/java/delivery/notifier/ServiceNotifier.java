package delivery.notifier;

import delivery.Application;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ServiceNotifier {

    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    public void notifyOrderService() throws MalformedURLException {
        String port = Application.applicationPort;
        JSONObject req = new JSONObject();
        req.put("host", new URL("http://localhost/"));
        req.put("port", port);
        restTemplate.postForObject("http://localhost:8088/delivery/init", req, String.class);
    }
}
