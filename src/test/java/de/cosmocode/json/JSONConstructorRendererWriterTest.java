package de.cosmocode.json;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.After;
import org.junit.Assert;

import com.google.common.collect.Maps;

public class JSONConstructorRendererWriterTest extends AbstractJSONRendererTest {

    private final Map<JSONRenderer, Writer> cache = Maps.newHashMap();
    
    @After
    public void after() {
        cache.clear();
    }
    
    @Override
    protected JSONRenderer create() {
        final Writer writer = new StringWriter();
        final JSONRenderer renderer = JSON.createJSONRenderer(writer);
        cache.put(renderer, writer);
        return renderer;
    }
    
    @Override
    protected void assertEquals(JSONRenderer expected, JSONRenderer actual) {
        Assert.assertEquals(cache.get(expected).toString(), cache.get(actual).toString());
    }

    @Override
    protected void assertEquals(JSONWriter expected, JSONRenderer actual) {
        Assert.assertEquals(expected.toString(), cache.get(actual).toString());
    }
    
    @Override
    protected void assertEquals(Map<Object, Object> expected, JSONRenderer actual) {
        try {
            final String source = cache.get(actual).toString();
            final JSONObject object = new JSONObject(source);
            final Map<String, Object> map = JSON.asMap(object);
            Assert.assertEquals(expected, map);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
