package testData;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonPlaceholderTestData {
    public int codeOK=200;
    public JSONObject expectedDataJson(){
        JSONObject expectedBody = new JSONObject();
        expectedBody.put("userId",3);
        expectedBody.put("id",22);
        expectedBody.put("title","dolor sint quo a velit explicabo quia nam" );
        expectedBody.put("body", "eos qui et ipsum ipsam suscipit aut\n" +
                "sed omnis non odio\n" +
                "expedita earum mollitia molestiae aut atque rem suscipit\n" +
                "nam impedit esse");
        System.out.println(expectedBody);
        return expectedBody;
    }
    public Map<String, Object> requestBodyMethodMap(){
        Map<String,Object> requestBodyMap= new HashMap<>();
        requestBodyMap.put("body", "Merhaba");
        requestBodyMap.put("userId", 10.0);
        requestBodyMap.put("id", 70.0);
        return requestBodyMap;
    }

}
