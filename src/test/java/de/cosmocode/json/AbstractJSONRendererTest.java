package de.cosmocode.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.cosmocode.junit.Asserts;

public abstract class AbstractJSONRendererTest {

    private JSONRenderer renderer;
    
    private JSONArray array;
    private JSONObject object;
    private JSONWriter writer;
    
    protected abstract JSONRenderer create();
    
    @Before
    public void before() {
        renderer = create();
        array = new JSONArray();
        object = JSON.createLinkedJSONObject();
        writer = new JSONStringer();
    }
    
    @Test
    public void arrayEmpty() {
        Assert.assertEquals(
            array.toString(),
            renderer.array().endArray().toString()
        );
    }
    
    @Test
    public void arrayOne() {
        Assert.assertEquals(
            array.put("test").toString(), 
            renderer.array().
                value("test").endArray().toString()
        );
    }
    
    @Test
    public void arrayThree() {
        Assert.assertEquals(
            array.
                put("test").
                put(true).
                put((Object) null).toString(), 
            renderer.array().
                value("test").
                value(true).
                value((Object) null).endArray().toString()
        );
    }

    @Test
    public void arrayFive() {
        Assert.assertEquals(
            array.
                put("test").
                put(true).
                put((Object) null).
                put(123).
                put(Integer.MAX_VALUE).toString(),
            renderer.array().
                values("test").
                value(true).
                value((Object) null).
                value(123).
                value(Integer.MAX_VALUE).endArray().toString()
        );
    }
    
    @Test
    public void arrayThis() {
        Assert.assertSame(
            renderer, 
            renderer.array()
        );
    }
    
    @Test
    public void endArrayThis() {
        Asserts.assertSame(
            renderer, 
            renderer.array().endArray()
        );
    }
    
    @Test(expected = IllegalStateException.class)
    public void arrayAfterLast() {
        renderer.array().endArray().array();
    }
    
    @Test(expected = IllegalStateException.class)
    public void endArrayAfterLast() {
        renderer.array().endArray().endArray();
    }
    
    @Test
    public void objectEmpty() {
        Assert.assertEquals(
            object.toString(),
            renderer.object().endObject().toString()
        );
    }
    
    @Test
    public void objectOne() throws JSONException {
        Assert.assertEquals(
            object.
                put("key", "value").toString(),
            renderer.object().
                key("key").value("value").endObject().toString()
        );
    }
    
    @Test
    public void objectThree() throws JSONException {
        Assert.assertEquals(
            object.
                put("key", "value").
                put("bool", true).
                put("int", 123).toString(),
            renderer.object().
                key("key").value("value").
                key("bool").value(true).
                key("int").value(123).endObject().toString()
        );
    }
    
    @Test
    public void objectFive() throws JSONException {
        Assert.assertEquals(
            writer.object().
                key("key").value("value").
                key("bool").value(true).
                key("int").value(123).
                key("comp").value(String.CASE_INSENSITIVE_ORDER).
                key("null").value((Object) null).endObject().toString(),
            renderer.object().
                key("key").value("value").
                key("bool").value(true).
                key("int").value(123).
                key("comp").value(String.CASE_INSENSITIVE_ORDER).
                key("null").value((Object) null).endObject().toString()
        );
    }
    
    @Test
    public void objectThis() {
        Assert.assertSame(renderer, renderer.object());
    }
    
    @Test
    public void endObjectThis() {
        Assert.assertSame(renderer, renderer.object().endObject());
    }
    
    @Test(expected = IllegalStateException.class)
    public void objectAfterLast() {
        renderer.object().endObject().object();
    }
    
    @Test(expected = IllegalStateException.class)
    public void endObjectAfterLast() {
        renderer.object().endObject().endObject();
    }
    
    @Test
    public void keyDuplicate() throws JSONException {
        Assert.assertEquals(
            writer.object().
                key("key").value("value").
                key("key").value("value").endObject().toString(),
            renderer.object().
                key("key").value("value").
                key("key").value("value").endObject().toString()
        );
    }
    
    @Test
    public void keyCharSequenceNull() throws JSONException {
        Assert.assertEquals(
            writer.object().
                key("null").value(true).endObject().toString(),
            renderer.object().
                key(null).value(true).endObject().toString()
        );
    }
    
    @Test
    public void keyObjectNull() throws JSONException {
        Assert.assertEquals(
            writer.object().
                key("null").value(true).endObject().toString(),
            renderer.object().
                key((Object) null).value(true).endObject().toString()
        );
    }
    
    @Test
    public void keyCharSequenceToString() {
        final List<Object> booleanHolder = Lists.newArrayList();
        
        final CharSequence key = new CharSequence() {
            
            @Override
            public CharSequence subSequence(int start, int end) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public int length() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public char charAt(int index) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public String toString() {
                booleanHolder.add("random element");
                return super.toString();
            }
            
        };
        
        renderer.object().
                key(key).value(Boolean.TRUE).
            endObject();
        
        Assert.assertTrue("toString of CharSequence has not being called", booleanHolder.size() == 1);
    }
    
    @Test
    public void keyObjectToString() {
        final List<Object> booleanHolder = Lists.newArrayList();
        
        final Object key = new Object() {
            
            @Override
            public String toString() {
                booleanHolder.add("random element");
                return super.toString();
            }
            
        };
        
        renderer.object().
                key(key).value(Boolean.TRUE).
            endObject();
        
        Assert.assertTrue("toString of CharSequence has not being called", booleanHolder.size() == 1);
    }
    
    @Test
    public void keyCharSequenceThis() {
        Assert.assertSame(renderer, renderer.object().key("key"));
    }
    
    @Test
    public void keyObjectThis() {
        Assert.assertSame(renderer, renderer.object().key(new Object()));
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyCharSequenceBeforeFirst() {
        renderer.key("key");
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyObjectBeforeFirst() {
        renderer.key(new Object());
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyCharSequenceAfterKey() {
        renderer.object().key("key").key("key");
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyObjectAfterKey() {
        renderer.object().key(new Object()).key(new Object());
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyCharSequenceAfterArray() {
        renderer.object().array().key("key");
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyObjectAfterArray() {
        renderer.object().array().key(new Object());
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyCharSequenceAfterLast() {
        renderer.object().endObject().key("key");
    }
    
    @Test(expected = IllegalStateException.class)
    public void keyObjectAfterLast() {
        renderer.object().endObject().key(new Object());
    }
    
    @Test
    public void nullValueArrayOne() throws JSONException {
        Assert.assertEquals(
            writer.array().
                value(null).endArray().toString(),
            renderer.array().
                nullValue().endArray().toString()
        );
    }
    
    @Test
    public void nullValueArrayThree() throws JSONException {
        Assert.assertEquals(
            writer.array().
                value(null).
                value(null).
                value(null).endArray().toString(),
            renderer.array().
                nullValue().
                nullValue().
                nullValue().endArray().toString()
        );
    }
    
    @Test
    public void nullValueArrayFive() throws JSONException {
        Assert.assertEquals(
            writer.array().
                value(null).
                value(null).
                value(null).
                value(null).
                value(null).endArray().toString(),
            renderer.array().
                nullValue().
                nullValue().
                nullValue().
                nullValue().
                nullValue().endArray().toString()
        );
    }
    
    @Test
    public void nullValueObjectOne() throws JSONException {
        Assert.assertEquals(
            writer.object().
                key("k1").value(null).endObject().toString(),
            renderer.object().
                key("k1").nullValue().endObject().toString()
        );
    }
    
    @Test
    public void nullValueObjectThree() throws JSONException {
        Assert.assertEquals(
            writer.object().
                key("k1").value(null).
                key("k2").value(null).
                key("k3").value(null).endObject().toString(),
            renderer.object().
                key("k1").nullValue().
                key("k2").nullValue().
                key("k3").nullValue().endObject().toString()
        );
    }
    
    @Test
    public void nullValueObjectFive() throws JSONException {
        Assert.assertEquals(
            writer.object().
                key("k1").value(null).
                key("k2").value(null).
                key("k3").value(null).
                key("k4").value(null).
                key("k5").value(null).endObject().toString(),
            renderer.object().
                key("k1").nullValue().
                key("k2").nullValue().
                key("k3").nullValue().
                key("k4").nullValue().
                key("k5").nullValue().endObject().toString()
        );
    }
    
    @Test
    public void nullValueThis() {
        Assert.assertSame(renderer, renderer.array().nullValue());
    }
    
    @Test(expected = IllegalStateException.class)
    public void nullValueBeforeFirst() {
        renderer.nullValue();
    }
    
    @Test(expected = IllegalStateException.class)
    public void nullValueAfterObject() {
        renderer.object().nullValue();
    }
    
    @Test(expected = IllegalStateException.class)
    public void nullValueAfteValueInObject() {
        renderer.object().key("key").value("value").nullValue();
    }
    
    @Test(expected = IllegalStateException.class)
    public void nullValueAfterLast() {
        renderer.object().endObject().nullValue();
    }

}
