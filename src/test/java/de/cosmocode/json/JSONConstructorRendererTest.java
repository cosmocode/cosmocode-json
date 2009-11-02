package de.cosmocode.json;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.Assert;

public class JSONConstructorRendererTest extends AbstractJSONRendererTest {

    @Override
    protected JSONRenderer create() {
        return JSON.createJSONRenderer();
    }
    
    @Override
    protected void assertEquals(JSONRenderer expected, JSONRenderer actual) {
        Assert.assertEquals(expected.toString(), actual.toString());
    }
    
    @Override
    protected void assertEquals(JSONWriter expected, JSONRenderer actual) {
        Assert.assertEquals(expected.toString(), actual.toString());
    }
    
    @Override
    protected void assertEquals(Map<Object, Object> expected, JSONRenderer actual) {
        try {
            final String source = actual.toString();
            final JSONObject object = new JSONObject(source);
            final Map<String, Object> map = JSON.asMap(object);
            Assert.assertEquals(expected, map);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    
}
