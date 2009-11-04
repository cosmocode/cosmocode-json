package de.cosmocode.json;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;
import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.cosmocode.Holder;
import de.cosmocode.commons.DateMode;
import de.cosmocode.junit.Asserts;

abstract class AbstractJSONRendererTest {
    
    private static final Object NULL = null;
    
    private static final byte POSITIVE_BYTE = 123;
    private static final byte ZERO_BYTE = 0;
    private static final byte NEGATIVE_BYTE = -123;
    
    private static final short POSITIVE_SHORT = 21766;
    private static final short ZERO_SHORT = 0;
    private static final short NEGATIVE_SHORT = -21766;
    
    private static final char POSITIVE_CHAR = 65;
    private static final char ZERO_CHAR = 0;
    private static final char POSITIVE_CHAR_LITERAL = 'A';
    private static final char ZERO_CHAR_LITERAL = '\u0000';
    
    private static final int POSITIVE_INT = 250768;
    private static final int ZERO_INT = 0;
    private static final int NEGATIVE_INT = -250768;
    
    private static final long POSITIVE_LONG = 17258985684L;
    private static final long ZERO_LONG = 0;
    private static final long NEGATIVE_LONG = -17258985684L;
    
    private static final float POSITIVE_FLOAT = 123.456789f;
    private static final float ZERO_FLOAT = 0.0f;
    private static final float NEGATIVE_FLOAT = -123.456789f;
    
    private static final double POSITIVE_DOUBLE = 123.456789f;
    private static final double ZERO_DOUBLE = 0;
    private static final double NEGATIVE_DOUBLE = -123.456789f;
    
    private static final BigInteger POSITIVE_BIG_INTEGER = new BigInteger("9999999999999999999");
    private static final BigInteger ZERO_BIG_INTEGER = new BigInteger("0");
    private static final BigInteger NEGATIVE_BIG_INTEGER = new BigInteger("-9999999999999999999");
    private static final BigInteger NULL_BIG_INTEGER = null;
    
    private static final BigInteger POSITIVE_BIG_DECIMAL = new BigInteger("9999999999999999999");
    private static final BigInteger ZERO_BIG_DECIMAL = new BigInteger("0");
    private static final BigInteger NEGATIVE_BIG_DECIMAL = new BigInteger("-9999999999999999999");
    private static final BigInteger NULL_BIG_DECIMAL = null;
    
    private static final Date TODAY_DATE = new Date();
    private static final Date EPOCH_START_DATE = new Date(0);
    private static final Date NULL_DATE = null;
    
    private static enum TestEnum {
        
        A_VALUE;
        
        @Override
        public String toString() {
            throw new IllegalStateException("TestEnum#toString() should never be called");
        }
        
    }
    
    private static final TestEnum NULL_ENUM = null;
    
    private static final CharSequence STRING_CHAR_SEQUENCE = new String("a-string");
    private static final CharSequence STRING_BUILDER_CHAR_SEQUENCE;
    private static final CharSequence STRING_BUFFER_CHAR_SEQUENCE;
    private static final CharSequence NULL_CHAR_SEQUENCE = null;

    static {
        STRING_BUILDER_CHAR_SEQUENCE = new StringBuilder("a").append("-").append("stringbuilder");
        STRING_BUFFER_CHAR_SEQUENCE = new StringBuffer("a").append("-").append("stringbuffer");
    }
    
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private static final Object[] SIZE_ONE_ARRAY = new Object[] {
        "value"
    };
    private static final Object[] SIZE_THREE_ARRAY = new Object[] {
        "value", Boolean.TRUE, 123
    };
    private static final Object[] NULL_ARRAY = null;
    
    private static final Iterable<Object> EMPTY_ITERABLE = ImmutableList.of();
    private static final Iterable<Object> SIZE_ONE_ITERABLE = ImmutableList.<Object>of(SIZE_ONE_ARRAY);
    private static final Iterable<Object> SIZE_THREE_ITERABLE = ImmutableList.<Object>of(SIZE_THREE_ARRAY);
    private static final Iterable<Object> NULL_ITERABLE = null;
    
    private static final Iterator<Object> NULL_ITERATOR = null;
    
    private static final JSONListable EMPTY_LISTABLE = new JSONListable() {
        
        @Override
        public JSONRenderer renderAsList(JSONRenderer jsonRenderer) {
            return jsonRenderer;
        }
        
    };
    private static final JSONListable SIZE_ONE_LISTABLE = new JSONListable() {
        
        @Override
        public JSONRenderer renderAsList(JSONRenderer jsonRenderer) {
            return jsonRenderer.values(SIZE_ONE_ARRAY);
        }
        
    };
    private static final JSONListable SIZE_THREE_LISTABLE = new JSONListable() {
        
        @Override
        public JSONRenderer renderAsList(JSONRenderer jsonRenderer) {
            return jsonRenderer.values(SIZE_THREE_ARRAY);
        }
        
    };
    private static final JSONListable NULL_LISTABLE = null;
    
    private static final Map<Object, Object> EMPTY_MAP = ImmutableMap.of();
    private static final Map<Object, Object> SIZE_ONE_MAP = ImmutableMap.<Object, Object>of(
        "string", "value"
    );
    private static final Map<Object, Object> SIZE_THREE_MAP = ImmutableMap.<Object, Object>of(
        "string", "value", "boolean", true, "int", 123
    );
    private static final Map<Object, Object> NULL_MAP = null;
    
    private static final JSONMapable EMPTY_MAPABLE = new JSONMapable() {
        
        @Override
        public JSONRenderer renderAsMap(JSONRenderer jsonRenderer) {
            return jsonRenderer;
        }
        
    };
    private static final JSONMapable SIZE_ONE_MAPABLE = new JSONMapable() {
        
        @Override
        public JSONRenderer renderAsMap(JSONRenderer json) {
            return json.pairs(SIZE_ONE_MAP);
        }
        
    };
    private static final JSONMapable SIZE_THREE_MAPABLE = new JSONMapable() {
        
        @Override
        public JSONRenderer renderAsMap(JSONRenderer json) {
            return json.pairs(SIZE_THREE_MAP);
        }
        
    };
    private static final JSONMapable NULL_MAPABLE = null;
    
    private static final NoObjectContext EMPTY_NO_OBJECT_CONTEXT = new NoObjectContext() {
        
        @Override
        public void encodeJSON(JSONConstructor json) throws JSONException {
            
        }
        
    };
    private static final NoObjectContext SIZE_ONE_NO_OBJECT_CONTEXT = new NoObjectContext() {
        
        @Override
        public void encodeJSON(JSONConstructor json) throws JSONException {
            for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
                json.key(entry.getKey().toString()).value(entry.getValue());
            }
        }
        
    };
    private static final NoObjectContext SIZE_THREE_NO_OBJECT_CONTEXT = new NoObjectContext() {
        
        @Override
        public void encodeJSON(JSONConstructor json) throws JSONException {
            for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
                json.key(entry.getKey().toString()).value(entry.getValue());
            }
        }
        
    };
    private static final NoObjectContext NULL_NO_OBJECT_CONTEXT = null;
    
    private static final JSONEncoder EMPTY_ENCODER = new JSONEncoder() {
        
        @Override
        public void encodeJSON(JSONConstructor json) throws JSONException {
            json.object().endObject();
        }
    };
    private static final JSONEncoder SIZE_ONE_ENCODER = new JSONEncoder() {
        
        @Override
        public void encodeJSON(JSONConstructor json) throws JSONException {
            json.object();
            for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
                json.key(entry.getKey().toString()).value(entry.getValue());
            }
            json.endObject();
        }
    };
    private static final JSONEncoder SIZE_THREE_ENCODER = new JSONEncoder() {
        
        @Override
        public void encodeJSON(JSONConstructor json) throws JSONException {
            json.object();
            for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
                json.key(entry.getKey().toString()).value(entry.getValue());
            }
            json.endObject();
        }
    };
    private static final JSONEncoder NULL_ENCODER = null;
    
    private static final String EMPTY_OBJECT_PLAIN = new JSONObject().toString();
    private static final String SIZE_ONE_OBJECT_PLAIN = new JSONObject(SIZE_ONE_MAP).toString();
    private static final String SIZE_THREE_OBJECT_PLAIN = new JSONObject(SIZE_THREE_MAP).toString();
    private static final String EMPTY_ARRAY_PLAIN = new JSONArray().toString();
    private static final String SIZE_ONE_ARRAY_PLAIN;
    private static final String SIZE_THREE_ARRAY_PLAIN;
    private static final String INVALID_PLAIN_OBJECT_END_ARRAY = "{]";
    private static final String INVALID_PLAIN_EMPTY = "{]";
    private static final String NULL_PLAIN = null;
    private static final String EMTPY_PLAIN = "";
    private static final String BLANK_PLAIN = "   \t";
    
    static {
        try {
            SIZE_ONE_ARRAY_PLAIN = new JSONArray(SIZE_ONE_ARRAY).toString();
            SIZE_THREE_ARRAY_PLAIN = new JSONArray(SIZE_THREE_ARRAY).toString();
        } catch (JSONException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    private JSONRenderer renderer;
    private JSONWriter writer;
    
    protected abstract JSONRenderer create();
    
    protected abstract String toJSONString(JSONRenderer renderer);
    
    private String toString(JSONRenderer r) throws JSONException {
        return sort(toJSONString(r));
    }
    
    private String toString(JSONWriter writer) throws JSONException {
        return sort(writer.toString());
    }
    
    private String sort(String source) throws JSONException {
        if (source.startsWith("{")) {
            final JSONObject object = new JSONObject(source);
            final Map<String, Object> objectMap = JSON.asMap(object);
            final JSONObject sorted = JSON.createSortedJSONObject(objectMap);
            return sorted.toString();
        } else if (source.startsWith("[")) {
            return new JSONArray(source).toString();
        } else {
            throw new IllegalArgumentException(source + " is no valid json string");
        }
    }
    
    private void assertEquals(JSONWriter expected, JSONRenderer actual) throws JSONException {
        Assert.assertEquals(toString(expected), toString(actual));
    };
    
    private void assertEquals(JSONRenderer expected, JSONRenderer actual) throws JSONException {
        Assert.assertEquals(toString(expected), toString(actual));
    };
    
    private void assertEquals() throws JSONException {
        assertEquals(writer, renderer);
    }
    
    @Before
    public void before() {
        renderer = create();
        writer = new JSONStringer();
    }
    
    @Test
    public void arrayEmpty() throws JSONException {
        writer.array().endArray();
        renderer.array().endArray();
        assertEquals();
    }
    
    @Test
    public void arrayOne() throws JSONException {
        writer.array().
            value("test").endArray();
        renderer.array().
            value("test").endArray();
        assertEquals();
    }
    
    @Test
    public void arrayThree() throws JSONException {
        writer.array().
            value("test").
            value(true).
            value((Object) null).endArray();
        renderer.array().
            value("test").
            value(true).
            value((Object) null).endArray();
        assertEquals();
    }

    @Test
    public void arrayFive() throws JSONException {
        writer.array().
            value("test").
            value(true).
            value((Object) null).
            value(123).
            value(Integer.MAX_VALUE).endArray();
        renderer.array().
            value("test").
            value(true).
            value((Object) null).
            value(123).
            value(Integer.MAX_VALUE).endArray();
        assertEquals();
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
    public void objectEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object().endObject();
        assertEquals();
    }
    
    @Test
    public void objectOne() throws JSONException {
        writer.object().
            key("key").value("value").endObject();
        renderer.object().
            key("key").value("value").endObject();
        assertEquals();
    }
    
    @Test
    public void objectThree() throws JSONException {
        writer.object().
            key("key").value("value").
            key("bool").value(true).
            key("int").value(123).endObject();
        renderer.object().
            key("key").value("value").
            key("bool").value(true).
            key("int").value(123).endObject();
        assertEquals();
    }
    
    @Test
    public void objectFive() throws JSONException {
        writer.object().
            key("key").value("value").
            key("bool").value(true).
            key("int").value(123).
            key("comp").value(String.CASE_INSENSITIVE_ORDER).
            key("null").value((Object) null).endObject();
        renderer.object().
            key("key").value("value").
            key("bool").value(true).
            key("int").value(123).
            key("comp").value(String.CASE_INSENSITIVE_ORDER).
            key("null").value((Object) null).endObject();
        assertEquals();
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
    @Ignore("Until there is a way to fix JSONObject without source modification")
    public void keyDuplicate() throws JSONException {
        writer.object().
            key("key").value("value").
            key("key").value("value").endObject();
        renderer.object().
            key("key").value("value").
            key("key").value("value").endObject();
        assertEquals();
    }
    
    @Test
    public void keyCharSequenceNull() throws JSONException {
        writer.object().
            key("null").value(true).endObject();
        renderer.object().
            key(null).value(true).endObject();
        assertEquals();
    }
    
    @Test
    public void keyObjectNull() throws JSONException {
        writer.object().
            key("null").value(true).endObject();
        renderer.object().
            key((Object) null).value(true).endObject();
        assertEquals();
    }
    
    @Test
    public void keyCharSequenceToString() {
        final Holder<Boolean> holder = Holder.of(false);
        
        final CharSequence key = new AbstractCharSequence() {
            
            @Override
            public String toString() {
                holder.set(true);
                return super.toString();
            }
        };
            
        renderer.object().
                key(key).value(Boolean.TRUE).
            endObject();
        
        Assert.assertTrue("toString of CharSequence has not been called", holder.get());
    }
    
    @Test
    public void keyObjectToString() {
        final Holder<Boolean> holder = Holder.of(false);
        
        final Object key = new Object() {
            
            @Override
            public String toString() {
                holder.set(true);
                return super.toString();
            }
            
        };
        
        renderer.object().
                key(key).value(Boolean.TRUE).
            endObject();
        
        Assert.assertTrue("toString of Object has not been called", holder.get());
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
        writer.array().
            value(null).endArray();
        renderer.array().
            nullValue().endArray();
        assertEquals();
    }
    
    @Test
    public void nullValueArrayThree() throws JSONException {
        writer.array().
            value(null).
            value(null).
            value(null).endArray();
        renderer.array().
            nullValue().
            nullValue().
            nullValue().endArray();
        assertEquals();
    }
    
    @Test
    public void nullValueArrayFive() throws JSONException {
        writer.array().
            value(null).
            value(null).
            value(null).
            value(null).
            value(null).endArray();
        renderer.array().
            nullValue().
            nullValue().
            nullValue().
            nullValue().
            nullValue().endArray();
        assertEquals();
    }
    
    @Test
    public void nullValueObjectOne() throws JSONException {
        writer.object().
            key("k1").value(null).endObject();
        renderer.object().
            key("k1").nullValue().endObject();
        assertEquals();
    }
    
    @Test
    public void nullValueObjectThree() throws JSONException {
        writer.object().
            key("k1").value(null).
            key("k2").value(null).
            key("k3").value(null).endObject();
        renderer.object().
            key("k1").nullValue().
            key("k2").nullValue().
            key("k3").nullValue().endObject();
        assertEquals();
    }
    
    @Test
    public void nullValueObjectFive() throws JSONException {
        writer.object().
            key("k1").value(null).
            key("k2").value(null).
            key("k3").value(null).
            key("k4").value(null).
            key("k5").value(null).endObject();
        renderer.object().
            key("k1").nullValue().
            key("k2").nullValue().
            key("k3").nullValue().
            key("k4").nullValue().
            key("k5").nullValue().endObject();
        assertEquals();
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
    
    @Test
    public void valueObjectNull() throws JSONException {
        writer.array().value(null).endArray();
        renderer.array().value(NULL).endArray();
        assertEquals();
    }
    
    @Test
    public void valueObjectThis() {
        Assert.assertSame(renderer, renderer.array().value(new Object()));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueObjectBeforeFirst() {
        renderer.value(new Object());
    }

    @Test(expected = IllegalStateException.class)
    public void valueObjectAfterObject() {
        renderer.object().value(new Object());
    }

    @Test(expected = IllegalStateException.class)
    public void valueObjectAfterValueInObject() {
        renderer.object().key("key").value(new Object()).value(new Object());
    }

    @Test(expected = IllegalStateException.class)
    public void valueObjectAfterLast() {
        renderer.object().endObject().value(new Object());
    }
    
    /*
     * TODO add more tests for {@link JSONRenderer#value(Object)} here
     */
    
    @Test
    public void valueBooleanTrue() throws JSONException {
        writer.array().value(true).endArray();
        renderer.array().value(true).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBooleanFalse() throws JSONException {
        writer.array().value(false).endArray();
        renderer.array().value(false).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBooleanThis() {
        Assert.assertSame(renderer, renderer.array().value(true));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueBooleanBeforeFirst() {
        renderer.value(true);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBooleanAfterObject() {
        renderer.object().value(true);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBooleanAfterValueInObject() {
        renderer.object().key("key").value(true).value(true);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBooleanAfterLast() {
        renderer.object().endObject().value(true);
    }

    @Test
    public void valueBytePositive() throws JSONException {
        writer.array().value(POSITIVE_BYTE).endArray();
        renderer.array().value(POSITIVE_BYTE).endArray();
        assertEquals();
    }

    @Test
    public void valueByteZero() throws JSONException {
        writer.array().value(ZERO_BYTE).endArray();
        renderer.array().value(ZERO_BYTE).endArray();
        assertEquals();
    }

    @Test
    public void valueByteNegative() throws JSONException {
        writer.array().value(NEGATIVE_BYTE).endArray();
        renderer.array().value(NEGATIVE_BYTE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueByteMax() throws JSONException {
        writer.array().value(Byte.MAX_VALUE).endArray();
        renderer.array().value(Byte.MAX_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueByteMin() throws JSONException {
        writer.array().value(Byte.MIN_VALUE).endArray();
        renderer.array().value(Byte.MIN_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueByteThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_BYTE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueByteBeforeFirst() {
        renderer.value(POSITIVE_BYTE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueByteAfterObject() {
        renderer.object().value(POSITIVE_BYTE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueByteAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_BYTE).value(POSITIVE_BYTE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueByteAfterLast() {
        renderer.object().endObject().value(POSITIVE_BYTE);
    }

    @Test
    public void valueShortPositive() throws JSONException {
        writer.array().value(POSITIVE_SHORT).endArray();
        renderer.array().value(POSITIVE_SHORT).endArray();
        assertEquals();
    }

    @Test
    public void valueShortZero() throws JSONException {
        writer.array().value(ZERO_SHORT).endArray();
        renderer.array().value(ZERO_SHORT).endArray();
        assertEquals();
    }

    @Test
    public void valueShortNegative() throws JSONException {
        writer.array().value(NEGATIVE_SHORT).endArray();
        renderer.array().value(NEGATIVE_SHORT).endArray();
        assertEquals();
    }
    
    @Test
    public void valueShortMax() throws JSONException {
        writer.array().value(Short.MAX_VALUE).endArray();
        renderer.array().value(Short.MAX_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueShortMin() throws JSONException {
        writer.array().value(Short.MIN_VALUE).endArray();
        renderer.array().value(Short.MIN_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueShortThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_SHORT));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueShortBeforeFirst() {
        renderer.value(POSITIVE_SHORT);
    }

    @Test(expected = IllegalStateException.class)
    public void valueShortAfterObject() {
        renderer.object().value(POSITIVE_SHORT);
    }

    @Test(expected = IllegalStateException.class)
    public void valueShortAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_SHORT).value(POSITIVE_SHORT);
    }

    @Test(expected = IllegalStateException.class)
    public void valueShortAfterLast() {
        renderer.object().endObject().value(POSITIVE_SHORT);
    }
    
    @Test
    public void valueCharPositive() throws JSONException {
        writer.array().value(POSITIVE_CHAR).endArray();
        renderer.array().value(POSITIVE_CHAR).endArray();
        assertEquals();
    }

    @Test
    public void valueCharZero() throws JSONException {
        writer.array().value(ZERO_CHAR).endArray();
        renderer.array().value(ZERO_CHAR).endArray();
        assertEquals();
    }

    @Test
    public void valueCharMax() throws JSONException {
        writer.array().value(Character.MAX_VALUE).endArray();
        renderer.array().value(Character.MAX_VALUE).endArray();
        assertEquals();
    }

    @Test
    public void valueCharMin() throws JSONException {
        writer.array().value(Character.MIN_VALUE).endArray();
        renderer.array().value(Character.MIN_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueCharPositiveLiteral() throws JSONException {
        writer.array().value(POSITIVE_CHAR_LITERAL).endArray();
        renderer.array().value(POSITIVE_CHAR_LITERAL).endArray();
        assertEquals();
    }
    
    @Test
    public void valueCharZeroLiteral() throws JSONException {
        writer.array().value(ZERO_CHAR_LITERAL).endArray();
        renderer.array().value(ZERO_CHAR_LITERAL).endArray();
        assertEquals();
    }

    @Test
    public void valueCharThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_CHAR_LITERAL));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueCharBeforeFirst() {
        renderer.value(POSITIVE_CHAR_LITERAL);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueCharAfterObject() {
        renderer.object().value(POSITIVE_CHAR_LITERAL);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueCharAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_CHAR_LITERAL).value(POSITIVE_CHAR_LITERAL);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueCharAfterLast() {
        renderer.object().endObject().value(POSITIVE_CHAR_LITERAL);
    }
    
    @Test
    public void valueIntPositive() throws JSONException {
        writer.array().value(POSITIVE_INT).endArray();
        renderer.array().value(POSITIVE_INT).endArray();
        assertEquals();
    }
    
    @Test
    public void valueIntZero() throws JSONException {
        writer.array().value(ZERO_INT).endArray();
        renderer.array().value(ZERO_INT).endArray();
        assertEquals();
    }
    
    @Test
    public void valueIntNegativ() throws JSONException {
        writer.array().value(NEGATIVE_INT).endArray();
        renderer.array().value(NEGATIVE_INT).endArray();
        assertEquals();
    }
    
    @Test
    public void valueIntMax() throws JSONException {
        writer.array().value(Integer.MAX_VALUE).endArray();
        renderer.array().value(Integer.MAX_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueIntMin() throws JSONException {
        writer.array().value(Integer.MIN_VALUE).endArray();
        renderer.array().value(Integer.MIN_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueIntThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_INT));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueIntBeforeFirst() {
        renderer.value(POSITIVE_INT);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueIntAfterObject() {
        renderer.object().value(POSITIVE_INT);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueIntAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_INT).value(POSITIVE_INT);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueIntAfterLast() {
        renderer.object().endObject().value(POSITIVE_INT);
    }
    
    @Test
    public void valueLongPositive() throws JSONException {
        writer.array().value(POSITIVE_LONG).endArray();
        renderer.array().value(POSITIVE_LONG).endArray();
        assertEquals();
    }
    
    @Test
    public void valueLongZero() throws JSONException {
        writer.array().value(ZERO_LONG).endArray();
        renderer.array().value(ZERO_LONG).endArray();
        assertEquals();
    }
    
    @Test
    public void valueLongNegativ() throws JSONException {
        writer.array().value(NEGATIVE_LONG).endArray();
        renderer.array().value(NEGATIVE_LONG).endArray();
        assertEquals();
    }
    
    @Test
    public void valueLongMax() throws JSONException {
        writer.array().value(Long.MAX_VALUE).endArray();
        renderer.array().value(Long.MAX_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueLongMin() throws JSONException {
        writer.array().value(Long.MIN_VALUE).endArray();
        renderer.array().value(Long.MIN_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueLongThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_LONG));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueLongBeforeFirst() {
        renderer.value(POSITIVE_LONG);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueLongAfterObject() {
        renderer.object().value(POSITIVE_LONG);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueLongAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_LONG).value(POSITIVE_LONG);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueLongAfterLast() {
        renderer.object().endObject().value(POSITIVE_LONG);
    }
    
    @Test
    public void valueFloatPositive() throws JSONException {
        writer.array().value(POSITIVE_LONG).endArray();
        renderer.array().value(POSITIVE_LONG).endArray();
        assertEquals();
    }
    
    @Test
    public void valueFloatZero() throws JSONException {
        writer.array().value(ZERO_FLOAT).endArray();
        renderer.array().value(ZERO_FLOAT).endArray();
        assertEquals();
    }
    
    @Test
    public void valueFloatNegativ() throws JSONException {
        writer.array().value(NEGATIVE_FLOAT).endArray();
        renderer.array().value(NEGATIVE_FLOAT).endArray();
        assertEquals();
    }
    
    @Test
    public void valueFloatMax() throws JSONException {
        writer.array().value(Float.MAX_VALUE).endArray();
        renderer.array().value(Float.MAX_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueFloatMin() throws JSONException {
        writer.array().value(Float.MIN_VALUE).endArray();
        renderer.array().value(Float.MIN_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueFloatThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_FLOAT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueFloatPositiveInfinite() {
        renderer.array().value(Float.POSITIVE_INFINITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueFloatNegativeInfinite() {
        renderer.array().value(Float.NEGATIVE_INFINITY);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void valueFloatNaN() {
        renderer.array().value(Float.NaN);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueFloatBeforeFirst() {
        renderer.value(POSITIVE_FLOAT);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueFloatAfterObject() {
        renderer.object().value(POSITIVE_FLOAT);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueFloatAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_FLOAT).value(POSITIVE_FLOAT);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueFloatAfterLast() {
        renderer.object().endObject().value(POSITIVE_FLOAT);
    }
    
    @Test
    public void valueDoublePositive() throws JSONException {
        writer.array().value(POSITIVE_DOUBLE).endArray();
        renderer.array().value(POSITIVE_DOUBLE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDoubleZero() throws JSONException {
        writer.array().value(ZERO_DOUBLE).endArray();
        renderer.array().value(ZERO_DOUBLE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDoubleNegativ() throws JSONException {
        writer.array().value(NEGATIVE_DOUBLE).endArray();
        renderer.array().value(NEGATIVE_DOUBLE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDoubleMax() throws JSONException {
        writer.array().value(Double.MAX_VALUE).endArray();
        renderer.array().value(Double.MAX_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDoubleMin() throws JSONException {
        writer.array().value(Double.MIN_VALUE).endArray();
        renderer.array().value(Double.MIN_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDoubleThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_DOUBLE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueDoublePositiveInfinite() {
        renderer.array().value(Double.POSITIVE_INFINITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueDoubleNegativeInfinite() {
        renderer.array().value(Double.NEGATIVE_INFINITY);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void valueDoubleNaN() {
        renderer.array().value(Double.NaN);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueDoubleBeforeFirst() {
        renderer.value(POSITIVE_DOUBLE);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueDoubleAfterObject() {
        renderer.object().value(POSITIVE_DOUBLE);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueDoubleAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_DOUBLE).value(POSITIVE_DOUBLE);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueDoubleAfterLast() {
        renderer.object().endObject().value(POSITIVE_DOUBLE);
    }
    
    @Test
    public void valueBigIntegerPositive() throws JSONException {
        writer.array().value(POSITIVE_BIG_INTEGER).endArray();
        renderer.array().value(POSITIVE_BIG_INTEGER).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigIntegerZero() throws JSONException {
        writer.array().value(ZERO_BIG_INTEGER).endArray();
        renderer.array().value(ZERO_BIG_INTEGER).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigIntegerNegative() throws JSONException {
        writer.array().value(NEGATIVE_BIG_INTEGER).endArray();
        renderer.array().value(NEGATIVE_BIG_INTEGER).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigIntegerNull() throws JSONException {
        writer.array().value(NULL_BIG_INTEGER).endArray();
        renderer.array().value(NULL_BIG_INTEGER).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigIntegerThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_BIG_INTEGER));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueBigIntegerBeforeFirst() {
        renderer.value(POSITIVE_BIG_INTEGER);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBigIntegerAfterObject() {
        renderer.object().value(POSITIVE_BIG_INTEGER);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBigIntegerAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_BIG_INTEGER).value(POSITIVE_BIG_INTEGER);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBigIntegerAfterLast() {
        renderer.object().endObject().value(POSITIVE_BIG_INTEGER);
    }
    
    @Test
    public void valueBigDecimalPositive() throws JSONException {
        writer.array().value(POSITIVE_BIG_DECIMAL).endArray();
        renderer.array().value(POSITIVE_BIG_DECIMAL).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigDecimalZero() throws JSONException {
        writer.array().value(ZERO_BIG_DECIMAL).endArray();
        renderer.array().value(ZERO_BIG_DECIMAL).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigDecimalNegative() throws JSONException {
        writer.array().value(NEGATIVE_BIG_DECIMAL).endArray();
        renderer.array().value(NEGATIVE_BIG_DECIMAL).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigDecimalNull() throws JSONException {
        writer.array().value(NULL_BIG_DECIMAL).endArray();
        renderer.array().value(NULL_BIG_DECIMAL).endArray();
        assertEquals();
    }
    
    @Test
    public void valueBigDecimalThis() {
        Assert.assertSame(renderer, renderer.array().value(POSITIVE_BIG_DECIMAL));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueBigDecimalBeforeFirst() {
        renderer.value(POSITIVE_BIG_DECIMAL);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBigDecimalAfterObject() {
        renderer.object().value(POSITIVE_BIG_DECIMAL);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBigDecimalAfterValueInObject() {
        renderer.object().key("key").value(POSITIVE_BIG_DECIMAL).value(POSITIVE_BIG_DECIMAL);
    }

    @Test(expected = IllegalStateException.class)
    public void valueBigDecimalAfterLast() {
        renderer.object().endObject().value(POSITIVE_BIG_DECIMAL);
    }

    @Test
    public void valueDateToday() throws JSONException {
        writer.array().value(DateMode.JAVA.parse(TODAY_DATE)).endArray();
        renderer.array().value(TODAY_DATE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDateEpochStart() throws JSONException {
        writer.array().value(DateMode.JAVA.parse(EPOCH_START_DATE)).endArray();
        renderer.array().value(EPOCH_START_DATE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDateNull() throws JSONException {
        writer.array().value(null).endArray();
        renderer.array().value(NULL_DATE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDateDateModeEquals() throws JSONException {
        final JSONRenderer expected = renderer.array().value(TODAY_DATE, DateMode.JAVA).endArray();
        renderer = create();
        final JSONRenderer actual = renderer.array().value(TODAY_DATE).endArray();
        assertEquals(expected, actual);
    }
    
    @Test
    public void valueDateThis() {
        Assert.assertSame(renderer, renderer.array().value(TODAY_DATE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueDateBeforeFirst() {
        renderer.value(TODAY_DATE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueDateAfterObject() {
        renderer.object().value(TODAY_DATE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueDateAfterValueInObject() {
        renderer.object().key("key").value(TODAY_DATE).value(TODAY_DATE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueDateAfterLast() {
        renderer.object().endObject().value(TODAY_DATE);
    }
    
    @Test
    public void valueDateModeToday() throws JSONException {
        writer.array().value(DateMode.UNIXTIME.parse(TODAY_DATE)).endArray();
        renderer.array().value(TODAY_DATE, DateMode.UNIXTIME).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDateModeEpochStart() throws JSONException {
        writer.array().value(DateMode.UNIXTIME.parse(EPOCH_START_DATE)).endArray();
        renderer.array().value(EPOCH_START_DATE, DateMode.UNIXTIME).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDateModeDateNull() throws JSONException {
        writer.array().value(null).endArray();
        renderer.array().value(NULL_DATE, DateMode.UNIXTIME).endArray();
        assertEquals();
    }
    
    @Test
    public void valueDateModeThis() {
        Assert.assertSame(renderer, renderer.array().value(TODAY_DATE, DateMode.UNIXTIME));
    }
    
    @Test(expected = NullPointerException.class)
    public void valueDateModeModeNull() {
        renderer.array().value(TODAY_DATE, null);
    }
    
    @Test(expected = NullPointerException.class)
    public void valueDateDatenAndModeModeNull() {
        renderer.array().value(NULL_DATE, null);
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueDateModeBeforeFirst() {
        renderer.value(TODAY_DATE, DateMode.UNIXTIME);
    }

    @Test(expected = IllegalStateException.class)
    public void valueDateModeAfterObject() {
        renderer.object().value(TODAY_DATE, DateMode.UNIXTIME);
    }

    @Test(expected = IllegalStateException.class)
    public void valueDateModeAfterValueInObject() {
        renderer.object().key("key").value(TODAY_DATE, DateMode.UNIXTIME).value(TODAY_DATE, DateMode.UNIXTIME);
    }

    @Test(expected = IllegalStateException.class)
    public void valueDateModeAfterLast() {
        renderer.object().endObject().value(TODAY_DATE, DateMode.UNIXTIME);
    }
    
    @Test
    public void valueEnum() throws JSONException {
        writer.array().value(TestEnum.A_VALUE.name()).endArray();
        renderer.array().value(TestEnum.A_VALUE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueEnumNull() throws JSONException {
        writer.array().value(null).endArray();
        renderer.array().value(NULL_ENUM).endArray();
        assertEquals();
    }
    
    @Test
    public void valueEnumThis() {
        Assert.assertSame(renderer, renderer.array().value(TestEnum.A_VALUE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueEnumBeforeFirst() {
        renderer.value(TestEnum.A_VALUE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueEnumAfterObject() {
        renderer.object().value(TestEnum.A_VALUE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueEnumAfterValueInObject() {
        renderer.object().key("key").value(TestEnum.A_VALUE).value(TestEnum.A_VALUE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueEnumAfterLast() {
        renderer.object().endObject().value(TestEnum.A_VALUE);
    }
    
    @Test
    public void valueCharSequenceString() throws JSONException {
        writer.array().value(STRING_CHAR_SEQUENCE).endArray();
        renderer.array().value(STRING_CHAR_SEQUENCE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueCharSequenceStringBuilder() throws JSONException {
        writer.array().value(STRING_BUILDER_CHAR_SEQUENCE).endArray();
        renderer.array().value(STRING_BUILDER_CHAR_SEQUENCE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueCharSequenceStringBuffer() throws JSONException {
        writer.array().value(STRING_BUFFER_CHAR_SEQUENCE).endArray();
        renderer.array().value(STRING_BUFFER_CHAR_SEQUENCE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueCharSequenceNull() throws JSONException {
        writer.array().value(null).endArray();
        renderer.array().value(NULL_CHAR_SEQUENCE).endArray();
        assertEquals();
    }
    
    @Test
    public void valueCharSequenceToString() {
        final Holder<Boolean> holder = Holder.of(false);
        
        final CharSequence sequence = new AbstractCharSequence() {
            
            @Override
            public String toString() {
                holder.set(true);
                return super.toString();
            }
            
        };
        
        renderer.array().value(sequence).endArray();
        
        Assert.assertTrue("CharSequence#toString() has not been called", holder.get());
    }
    
    @Test(expected = IllegalStateException.class)
    public void valueCharSequenceBeforeFirst() {
        renderer.value(STRING_CHAR_SEQUENCE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueCharSequenceAfterObject() {
        renderer.object().value(STRING_CHAR_SEQUENCE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueCharSequenceAfterValueInObject() {
        renderer.object().key("key").value(STRING_CHAR_SEQUENCE).value(STRING_CHAR_SEQUENCE);
    }

    @Test(expected = IllegalStateException.class)
    public void valueCharSequenceAfterLast() {
        renderer.object().endObject().value(STRING_CHAR_SEQUENCE);
    }
    
    @Test
    public void valuesArrayEmpty() throws JSONException {
        writer.array();
        for (Object value : EMPTY_ARRAY) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(EMPTY_ARRAY).endArray();
        assertEquals();
    }

    @Test
    public void valuesArrayOne() throws JSONException {
        writer.array();
        for (Object value : SIZE_ONE_ARRAY) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(SIZE_ONE_ARRAY).endArray();
        assertEquals();
    }

    @Test
    public void valuesArrayThree() throws JSONException {
        writer.array();
        for (Object value : SIZE_THREE_ARRAY) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(SIZE_THREE_ARRAY).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesArrayNull() throws JSONException {
        writer.array().endArray();
        renderer.array().values(NULL_ARRAY).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesArrayThis() {
        Assert.assertSame(renderer, renderer.array().values(SIZE_ONE_ARRAY));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valuesArrayBeforeFirst() {
        renderer.values(SIZE_THREE_ARRAY);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesArrayAfterObject() {
        renderer.object().values(SIZE_THREE_ARRAY);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesArrayAfterValueInObject() {
        renderer.object().key("key").value(new Object()).values(SIZE_THREE_ARRAY);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesArrayAfterLast() {
        renderer.object().endObject().values(SIZE_THREE_ARRAY);
    }
    
    @Test
    public void valuesIterableEmpty() throws JSONException {
        writer.array();
        for (Object value : EMPTY_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(EMPTY_ITERABLE).endArray();
        assertEquals();
    }

    @Test
    public void valuesIterableOne() throws JSONException {
        writer.array();
        for (Object value : SIZE_ONE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(SIZE_ONE_ITERABLE).endArray();
        assertEquals();
    }

    @Test
    public void valuesIterableThree() throws JSONException {
        writer.array();
        for (Object value : SIZE_THREE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(SIZE_THREE_ITERABLE).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesIterableNull() throws JSONException {
        writer.array().endArray();
        renderer.array().values(NULL_ITERABLE).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesIterableThis() {
        Assert.assertSame(renderer, renderer.array().values(SIZE_ONE_ITERABLE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valuesIterableBeforeFirst() {
        renderer.values(SIZE_THREE_ITERABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesIterableAfterObject() {
        renderer.object().values(SIZE_THREE_ITERABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesIterableAfterValueInObject() {
        renderer.object().key("key").value(new Object()).values(SIZE_THREE_ITERABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesIterableAfterLast() {
        renderer.object().endObject().values(SIZE_THREE_ITERABLE);
    }
    
    @Test
    public void valuesIteratorEmpty() throws JSONException {
        writer.array();
        final Iterator<Object> iterator = EMPTY_ITERABLE.iterator();
        while (iterator.hasNext()) {
            writer.value(iterator.next());
        }
        writer.endArray();
        renderer.array().values(EMPTY_ITERABLE.iterator()).endArray();
        assertEquals();
    }

    @Test
    public void valuesIteratorOne() throws JSONException {
        writer.array();
        final Iterator<Object> iterator = SIZE_ONE_ITERABLE.iterator();
        while (iterator.hasNext()) {
            writer.value(iterator.next());
        }
        writer.endArray();
        renderer.array().values(SIZE_ONE_ITERABLE.iterator()).endArray();
        assertEquals();
    }

    @Test
    public void valuesIteratorThree() throws JSONException {
        writer.array();
        final Iterator<Object> iterator = SIZE_THREE_ITERABLE.iterator();
        while (iterator.hasNext()) {
            writer.value(iterator.next());
        }
        writer.endArray();
        renderer.array().values(SIZE_THREE_ITERABLE.iterator()).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesIteratorNull() throws JSONException {
        writer.array().endArray();
        renderer.array().values(NULL_ITERATOR).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesIteratorThis() {
        Assert.assertSame(renderer, renderer.array().values(SIZE_ONE_ITERABLE.iterator()));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valuesIteratorBeforeFirst() {
        renderer.values(SIZE_THREE_ITERABLE.iterator());
    }

    @Test(expected = IllegalStateException.class)
    public void valuesIteratorAfterObject() {
        renderer.object().values(SIZE_THREE_ITERABLE.iterator());
    }

    @Test(expected = IllegalStateException.class)
    public void valuesIteratorAfterValueInObject() {
        renderer.object().key("key").value(new Object()).values(SIZE_THREE_ITERABLE.iterator());
    }

    @Test(expected = IllegalStateException.class)
    public void valuesIteratorAfterLast() {
        renderer.object().endObject().values(SIZE_THREE_ITERABLE.iterator());
    }
    
    @Test
    public void valuesListableEmpty() throws JSONException {
        writer.array();
        for (Object value : EMPTY_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(EMPTY_LISTABLE).endArray();
        assertEquals();
    }

    @Test
    public void valuesListableOne() throws JSONException {
        writer.array();
        for (Object value : SIZE_ONE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(SIZE_ONE_LISTABLE).endArray();
        assertEquals();
    }

    @Test
    public void valuesListableThree() throws JSONException {
        writer.array();
        for (Object value : SIZE_THREE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array().values(SIZE_THREE_LISTABLE).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesListableNull() throws JSONException {
        writer.array().endArray();
        renderer.array().values(NULL_LISTABLE).endArray();
        assertEquals();
    }
    
    @Test
    public void valuesListableThis() {
        Assert.assertSame(renderer, renderer.array().values(SIZE_ONE_LISTABLE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void valuesListableBeforeFirst() {
        renderer.values(SIZE_THREE_LISTABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesListableAfterObject() {
        renderer.object().values(SIZE_THREE_LISTABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesListableAfterValueInObject() {
        renderer.object().key("key").value(new Object()).values(SIZE_THREE_LISTABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void valuesListableAfterLast() {
        renderer.object().endObject().values(SIZE_THREE_LISTABLE);
    }
    
    @Test
    public void arrayArrayEmpty() throws JSONException {
        writer.array();
        for (Object value : EMPTY_ARRAY) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(EMPTY_ARRAY);
        assertEquals();
    }

    @Test
    public void arrayArrayOne() throws JSONException {
        writer.array();
        for (Object value : SIZE_ONE_ARRAY) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(SIZE_ONE_ARRAY);
        assertEquals();
    }

    @Test
    public void arrayArrayThree() throws JSONException {
        writer.array();
        for (Object value : SIZE_THREE_ARRAY) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(SIZE_THREE_ARRAY);
        assertEquals();
    }
    
    @Test
    public void arrayArrayNull() throws JSONException {
        writer.array().endArray();
        renderer.array(NULL_ARRAY);
        assertEquals();
    }
    
    @Test
    public void arrayArraySimiliar() throws JSONException {
        final JSONRenderer expected = renderer.array().values(SIZE_THREE_ARRAY).endArray();
        renderer = create();
        final JSONRenderer actual = renderer.array(SIZE_THREE_ARRAY);
        assertEquals(expected, actual); 
    }
    
    @Test
    public void arrayArrayThis() {
        Assert.assertSame(renderer, renderer.array(SIZE_ONE_ARRAY));
    }
    
    @Test(expected = IllegalStateException.class)
    public void arrayArrayAfterObject() {
        renderer.object().array(SIZE_THREE_ARRAY);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayArrayAfterValueInObject() {
        renderer.object().key("key").value(new Object()).array(SIZE_THREE_ARRAY);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayArrayAfterLast() {
        renderer.object().endObject().array(SIZE_ONE_ARRAY);
    }
    
    @Test
    public void arrayIterableEmpty() throws JSONException {
        writer.array();
        for (Object value : EMPTY_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(EMPTY_ITERABLE);
        assertEquals();
    }

    @Test
    public void arrayIterableOne() throws JSONException {
        writer.array();
        for (Object value : SIZE_ONE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(SIZE_ONE_ITERABLE);
        assertEquals();
    }

    @Test
    public void arrayIterableThree() throws JSONException {
        writer.array();
        for (Object value : SIZE_THREE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(SIZE_THREE_ITERABLE);
        assertEquals();
    }
    
    @Test
    public void arrayIterableNull() throws JSONException {
        writer.array().endArray();
        renderer.array(NULL_ITERABLE);
        assertEquals();
    }
    
    @Test
    public void arrayIterableSimiliar() throws JSONException {
        final JSONRenderer expected = renderer.array().values(SIZE_THREE_ITERABLE).endArray();
        renderer = create();
        final JSONRenderer actual = renderer.array(SIZE_THREE_ITERABLE);
        assertEquals(expected, actual); 
    }
    
    @Test
    public void arrayIterableThis() {
        Assert.assertSame(renderer, renderer.array(SIZE_ONE_ITERABLE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void arrayIterableAfterObject() {
        renderer.object().array(SIZE_THREE_ITERABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayIterableAfterValueInObject() {
        renderer.object().key("key").value(new Object()).array(SIZE_THREE_ITERABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayIterableAfterLast() {
        renderer.object().endObject().array(SIZE_THREE_ITERABLE);
    }
    
    @Test
    public void arrayIteratorEmpty() throws JSONException {
        writer.array();
        final Iterator<Object> iterator = EMPTY_ITERABLE.iterator();
        while (iterator.hasNext()) {
            writer.value(iterator.next());
        }
        writer.endArray();
        renderer.array(EMPTY_ITERABLE.iterator());
        assertEquals();
    }

    @Test
    public void arrayIteratorOne() throws JSONException {
        writer.array();
        final Iterator<Object> iterator = SIZE_ONE_ITERABLE.iterator();
        while (iterator.hasNext()) {
            writer.value(iterator.next());
        }
        writer.endArray();
        renderer.array(SIZE_ONE_ITERABLE.iterator());
        assertEquals();
    }

    @Test
    public void arrayIteratorThree() throws JSONException {
        writer.array();
        final Iterator<Object> iterator = SIZE_THREE_ITERABLE.iterator();
        while (iterator.hasNext()) {
            writer.value(iterator.next());
        }
        writer.endArray();
        renderer.array(SIZE_THREE_ITERABLE.iterator());
        assertEquals();
    }
    
    @Test
    public void arrayIteratorNull() throws JSONException {
        writer.array().endArray();
        renderer.array(NULL_ITERATOR);
        assertEquals();
    }
    
    @Test
    public void arrayIteratorSimiliar() throws JSONException {
        final JSONRenderer expected = renderer.array().values(SIZE_THREE_ITERABLE.iterator()).endArray();
        renderer = create();
        final JSONRenderer actual = renderer.array(SIZE_THREE_ITERABLE.iterator());
        assertEquals(expected, actual); 
    }
    
    @Test
    public void arrayIteratorThis() {
        Assert.assertSame(renderer, renderer.array(SIZE_ONE_ITERABLE.iterator()));
    }
    
    @Test(expected = IllegalStateException.class)
    public void arrayIteratorAfterObject() {
        renderer.object().array(SIZE_THREE_ITERABLE.iterator());
    }

    @Test(expected = IllegalStateException.class)
    public void arrayIteratorAfterValueInObject() {
        renderer.object().key("key").value(new Object()).array(SIZE_THREE_ITERABLE.iterator());
    }

    @Test(expected = IllegalStateException.class)
    public void arrayIteratorAfterLast() {
        renderer.object().endObject().array(SIZE_THREE_ITERABLE.iterator());
    }
    
    @Test
    public void arrayListableEmpty() throws JSONException {
        writer.array();
        for (Object value : EMPTY_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(EMPTY_LISTABLE);
        assertEquals();
    }

    @Test
    public void arrayListableOne() throws JSONException {
        writer.array();
        for (Object value : SIZE_ONE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(SIZE_ONE_LISTABLE);
        assertEquals();
    }

    @Test
    public void arrayListableThree() throws JSONException {
        writer.array();
        for (Object value : SIZE_THREE_ITERABLE) {
            writer.value(value);
        }
        writer.endArray();
        renderer.array(SIZE_THREE_LISTABLE);
        assertEquals();
    }
    
    @Test
    public void arrayListableNull() throws JSONException {
        writer.array().endArray();
        renderer.array(NULL_LISTABLE);
        assertEquals();
    }
    
    @Test
    public void arrayListableSimiliar() throws JSONException {
        final JSONRenderer expected = renderer.array().values(SIZE_THREE_ARRAY).endArray();
        renderer = create();
        final JSONRenderer actual = renderer.array(SIZE_THREE_LISTABLE);
        assertEquals(expected, actual); 
    }
    
    @Test
    public void arrayListableThis() {
        Assert.assertSame(renderer, renderer.array(SIZE_ONE_LISTABLE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void arrayListableAfterObject() {
        renderer.object().array(SIZE_THREE_LISTABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayListableAfterValueInObject() {
        renderer.object().key("key").value(new Object()).array(SIZE_THREE_LISTABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void arrayListableAfterLast() {
        renderer.object().endObject().array(SIZE_THREE_LISTABLE);
    }
    
    @Test
    public void pairsMapEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object().pairs(EMPTY_MAP).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapOne() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object().pairs(SIZE_ONE_MAP).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapThree() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object().pairs(SIZE_THREE_MAP).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapNull() throws JSONException {
        writer.object().endObject();
        renderer.object().pairs(NULL_MAP).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapThis() throws JSONException {
        Assert.assertSame(renderer, renderer.object().pairs(SIZE_THREE_MAP));
    }
    
    @Test(expected = IllegalStateException.class)
    public void pairsMapBeforeFirst() {
        renderer.pairs(SIZE_THREE_MAP);
    }
    
    @Test(expected = IllegalStateException.class)
    public void pairsMapAfterArray() {
        renderer.array().pairs(SIZE_THREE_MAP);
    }

    @Test(expected = IllegalStateException.class)
    public void pairsMapAfterValueInArray() {
        renderer.array().value(new Object()).pairs(SIZE_THREE_MAP);
    }

    @Test(expected = IllegalStateException.class)
    public void pairsMapAfterLast() {
        renderer.object().endObject().pairs(SIZE_THREE_MAP);
    }
    
    @Test
    public void pairsMapableEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object().pairs(EMPTY_MAPABLE).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapableOne() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object().pairs(SIZE_ONE_MAPABLE).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapableThree() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object().pairs(SIZE_THREE_MAPABLE).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapableNull() throws JSONException {
        writer.object().endObject();
        renderer.object().pairs(NULL_MAPABLE).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsMapableThis() throws JSONException {
        Assert.assertSame(renderer, renderer.object().pairs(SIZE_THREE_MAPABLE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void pairsMapableBeforeFirst() {
        renderer.pairs(SIZE_THREE_MAPABLE);
    }
    
    @Test(expected = IllegalStateException.class)
    public void pairsMapableAfterArray() {
        renderer.array().pairs(SIZE_THREE_MAPABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void pairsMapableAfterValueInArray() {
        renderer.array().value(new Object()).pairs(SIZE_THREE_MAPABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void pairsMapableAfterLast() {
        renderer.object().endObject().pairs(SIZE_THREE_MAPABLE);
    }
    
    @Test
    public void pairsNoObjectContextEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object().pairs(EMPTY_NO_OBJECT_CONTEXT).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsNoObjectContextOne() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object().pairs(SIZE_ONE_NO_OBJECT_CONTEXT).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsNoObjectContextThree() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object().pairs(SIZE_THREE_NO_OBJECT_CONTEXT).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsNoObjectContextNull() throws JSONException {
        writer.object().endObject();
        renderer.object().pairs(NULL_NO_OBJECT_CONTEXT).endObject();
        assertEquals();
    }
    
    @Test
    public void pairsNoObjectContextThis() throws JSONException {
        Assert.assertSame(renderer, renderer.object().pairs(SIZE_THREE_NO_OBJECT_CONTEXT));
    }
    
    @Test(expected = IllegalStateException.class)
    public void pairsNoObjectContextBeforeFirst() {
        renderer.pairs(SIZE_THREE_NO_OBJECT_CONTEXT);
    }
    
    @Test(expected = IllegalStateException.class)
    public void pairsNoObjectContextAfterArray() {
        renderer.array().pairs(SIZE_THREE_NO_OBJECT_CONTEXT);
    }

    @Test(expected = IllegalStateException.class)
    public void pairsNoObjectContextAfterValueInArray() {
        renderer.array().value(new Object()).pairs(SIZE_THREE_NO_OBJECT_CONTEXT);
    }

    @Test(expected = IllegalStateException.class)
    public void pairsNoObjectContextAfterLast() {
        renderer.object().endObject().pairs(SIZE_THREE_NO_OBJECT_CONTEXT);
    }
    
    @Test
    public void objectMapEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object(EMPTY_MAP);
        assertEquals();
    }
    
    @Test
    public void objectMapOne() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_ONE_MAP);
        assertEquals();
    }
    
    @Test
    public void objectMapThree() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_THREE_MAP);
        assertEquals();
    }
    
    @Test
    public void objectMapNull() throws JSONException {
        writer.object().endObject();
        renderer.object(NULL_MAP);
        assertEquals();
    }
    
    @Test
    public void objectMapThis() throws JSONException {
        Assert.assertSame(renderer, renderer.object(SIZE_THREE_MAP));
    }
    
    @Test(expected = IllegalStateException.class)
    public void objectMapAfterObject() {
        renderer.object().object(SIZE_THREE_MAP);
    }

    @Test(expected = IllegalStateException.class)
    public void objectMapAfterValueInObject() {
        renderer.object().key("key").value(new Object()).object(SIZE_THREE_MAP);
    }

    @Test(expected = IllegalStateException.class)
    public void objectMapAfterLast() {
        renderer.object().endObject().object(SIZE_THREE_MAP);
    }
    
    @Test
    public void objectMapableEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object(EMPTY_MAPABLE);
        assertEquals();
    }
    
    @Test
    public void objectMapableOne() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_ONE_MAPABLE);
        assertEquals();
    }
    
    @Test
    public void objectMapableThree() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_THREE_MAPABLE);
        assertEquals();
    }
    
    @Test
    public void objectMapableNull() throws JSONException {
        writer.object().endObject();
        renderer.object(NULL_MAPABLE);
        assertEquals();
    }
    
    @Test
    public void objectMapableThis() throws JSONException {
        Assert.assertSame(renderer, renderer.object(SIZE_THREE_MAPABLE));
    }
    
    @Test(expected = IllegalStateException.class)
    public void objectMapableAfterObject() {
        renderer.object().object(SIZE_THREE_MAPABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void objectMapableAfterValueInObject() {
        renderer.object().key("key").value(new Object()).object(SIZE_THREE_MAPABLE);
    }

    @Test(expected = IllegalStateException.class)
    public void objectMapableAfterLast() {
        renderer.object().endObject().object(SIZE_THREE_MAPABLE);
    }
    
    @Test
    public void objectNoObjectContextEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object(EMPTY_NO_OBJECT_CONTEXT);
        assertEquals();
    }
    
    @Test
    public void objectNoObjectContextOne() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_ONE_NO_OBJECT_CONTEXT);
        assertEquals();
    }
    
    @Test
    public void objectNoObjectContextThree() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_THREE_NO_OBJECT_CONTEXT);
        assertEquals();
    }
    
    @Test
    public void objectNoObjectContextNull() throws JSONException {
        writer.object().endObject();
        renderer.object(NULL_NO_OBJECT_CONTEXT);
        assertEquals();
    }
    
    @Test
    public void objectNoObjectContextThis() throws JSONException {
        Assert.assertSame(renderer, renderer.object(SIZE_THREE_NO_OBJECT_CONTEXT));
    }
    
    @Test(expected = IllegalStateException.class)
    public void objectNoObjectContextAfterObject() {
        renderer.object().object(SIZE_THREE_NO_OBJECT_CONTEXT);
    }

    @Test(expected = IllegalStateException.class)
    public void objectNoObjectContextAfterValueInObject() {
        renderer.object().key("key").value(new Object()).object(SIZE_THREE_NO_OBJECT_CONTEXT);
    }

    @Test(expected = IllegalStateException.class)
    public void objectNoObjectContextAfterLast() {
        renderer.object().endObject().object(SIZE_THREE_NO_OBJECT_CONTEXT);
    }
    
    @Test
    public void objectEncoderEmpty() throws JSONException {
        writer.object().endObject();
        renderer.object(EMPTY_ENCODER);
        assertEquals();
    }
    
    @Test
    public void objectEncoderOne() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_ONE_ENCODER);
        assertEquals();
    }
    
    @Test
    public void objectEncoderThree() throws JSONException {
        writer.object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject();
        renderer.object(SIZE_THREE_ENCODER);
        assertEquals();
    }
    
    @Test
    public void objectEncoderNull() throws JSONException {
        writer.object().endObject();
        renderer.object(NULL_ENCODER);
        assertEquals();
    }
    
    @Test
    public void objectEncoderThis() throws JSONException {
        Assert.assertSame(renderer, renderer.object(SIZE_THREE_ENCODER));
    }
    
    @Test(expected = IllegalStateException.class)
    public void objectEncoderAfterObject() {
        renderer.object().object(SIZE_THREE_ENCODER);
    }

    @Test(expected = IllegalStateException.class)
    public void objectEncoderAfterValueInObject() {
        renderer.object().key("key").value(new Object()).object(SIZE_THREE_ENCODER);
    }

    @Test(expected = IllegalStateException.class)
    public void objectEncoderAfterLast() {
        renderer.object().endObject().object(SIZE_THREE_ENCODER);
    }
    
    @Test
    public void plainObjectEmpty() throws JSONException {
        writer.
            array().
                object().endObject().
            endArray();
        renderer.
            array().
                plain(EMPTY_OBJECT_PLAIN).
            endArray();
        assertEquals();
    }
    
    @Test
    public void plainObjectOne() throws JSONException {
        writer.array().object();
        for (Map.Entry<Object, Object> entry : SIZE_ONE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject().endArray();
        renderer.array().plain(SIZE_ONE_OBJECT_PLAIN).endArray();
        assertEquals();
    }
    
    @Test
    public void plainObjectThree() throws JSONException {
        writer.array().object();
        for (Map.Entry<Object, Object> entry : SIZE_THREE_MAP.entrySet()) {
            writer.key(entry.getKey().toString()).value(entry.getValue());
        }
        writer.endObject().endArray();
        renderer.array().plain(SIZE_THREE_OBJECT_PLAIN).endArray();
        assertEquals();
    }
    
    @Test
    public void plainArrayEmpty() throws JSONException {
        writer.array().array().endArray().endArray();
        renderer.array().plain(EMPTY_ARRAY_PLAIN).endArray();
        assertEquals();
    }
    
    @Test
    public void plainArrayOne() throws JSONException {
        writer.array().array();
        for (Object value : SIZE_ONE_ARRAY) {
            writer.value(value);
        }
        writer.endArray().endArray();
        renderer.array().plain(SIZE_ONE_ARRAY_PLAIN).endArray();
        assertEquals();
    }
    
    @Test
    public void plainArrayThree() throws JSONException {
        writer.array().array();
        for (Object value : SIZE_THREE_ARRAY) {
            writer.value(value);
        }
        writer.endArray().endArray();
        renderer.array().plain(SIZE_THREE_ARRAY_PLAIN).endArray();
        assertEquals();
    }

    @Test
    public void plainNull() throws JSONException {
        writer.
            array().
                value(null).
            endArray();
        renderer.
            array().
                plain(NULL_PLAIN).
            endArray();
        assertEquals();
    }

    @Test
    public void plainEmpty() throws JSONException {
        writer.
            array().
                value(null).
            endArray();
        renderer.
            array().
                plain(EMTPY_PLAIN).
            endArray();
        assertEquals();
    }

    @Test
    public void plainBlank() throws JSONException {
        writer.
            array().
                value(null).
            endArray();
        renderer.
            array().
                plain(BLANK_PLAIN).
            endArray();
        assertEquals();
    }

    @Test(expected = IllegalArgumentException.class)
    public void plainInvalidObjectEndArray() throws JSONException {
        renderer.array().plain(INVALID_PLAIN_OBJECT_END_ARRAY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void plainInvalidEmpty() throws JSONException {
        renderer.array().plain(INVALID_PLAIN_EMPTY);
    }
    
    @Test(expected = IllegalStateException.class)
    public void plainObjectAfterObject() {
        renderer.object().plain(SIZE_THREE_OBJECT_PLAIN);
    }
    
    @Test(expected = IllegalStateException.class)
    public void plainObjectAfterValueInObject() {
        renderer.object().key("key").value(new Object()).plain(SIZE_THREE_OBJECT_PLAIN);
    }
    
    @Test(expected = IllegalStateException.class)
    public void plainObjectAfterLast() {
        renderer.object().endObject().plain(SIZE_THREE_OBJECT_PLAIN);
    }
    
    @Test(expected = IllegalStateException.class)
    public void plainArrayAfterObject() {
        renderer.object().plain(SIZE_THREE_ARRAY_PLAIN);
    }
    
    @Test(expected = IllegalStateException.class)
    public void plainArrayAfterValueInObject() {
        renderer.object().key("key").value(new Object()).plain(SIZE_THREE_ARRAY_PLAIN);
    }
    
    @Test(expected = IllegalStateException.class)
    public void plainArrayAfterLast() {
        renderer.object().endObject().plain(SIZE_THREE_ARRAY_PLAIN);
    }
    
}
