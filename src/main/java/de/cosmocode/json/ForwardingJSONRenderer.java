package de.cosmocode.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import de.cosmocode.commons.DateMode;

public abstract class ForwardingJSONRenderer implements JSONRenderer {

    private final JSONRenderer renderer;

    public ForwardingJSONRenderer(JSONRenderer renderer) {
        if (renderer == null) throw new NullPointerException("JSONRenderer must not be null");
        this.renderer = renderer;
    }

    @Override
    public JSONRenderer array() {
        return renderer.array();
    }

    @Override
    public <E> JSONRenderer array(E... values) {
        return renderer.array(values);
    }

    @Override
    public JSONRenderer array(Iterable<?> values) {
        return renderer.array(values);
    }

    @Override
    public JSONRenderer array(Iterator<?> values) {
        return renderer.array(values);
    }

    @Override
    public JSONRenderer array(JSONListable values) {
        return renderer.array(values);
    }

    @Override
    public JSONRenderer endArray() {
        return renderer.endArray();
    }

    @Override
    public JSONRenderer endObject() {
        return renderer.endObject();
    }

    @Override
    public JSONRenderer key(CharSequence key) {
        return renderer.key(key);
    }

    @Override
    public JSONRenderer key(Object key) {
        return renderer.key(key);
    }

    @Override
    public JSONRenderer nullValue() {
        return renderer.nullValue();
    }

    @Override
    public JSONRenderer object() {
        return renderer.object();
    }

    @Override
    public JSONRenderer object(JSONEncoder object) {
        return renderer.object(object);
    }

    @Override
    public JSONRenderer object(JSONMapable pairs) {
        return renderer.object(pairs);
    }

    @Override
    public JSONRenderer object(Map<?, ?> pairs) {
        return renderer.object(pairs);
    }

    @Override
    public JSONRenderer object(NoObjectContext pairs) {
        return renderer.object(pairs);
    }

    @Override
    public JSONRenderer pairs(JSONMapable pairs) {
        return renderer.pairs(pairs);
    }

    @Override
    public JSONRenderer pairs(Map<?, ?> pairs) {
        return renderer.pairs(pairs);
    }

    @Override
    public JSONRenderer pairs(NoObjectContext pairs) {
        return renderer.pairs(pairs);
    }

    @Override
    public JSONRenderer plain(String json) {
        return renderer.plain(json);
    }

    @Override
    public String toString() {
        return renderer.toString();
    }

    @Override
    public JSONRenderer value(BigDecimal value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(BigInteger value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(boolean value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(byte value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(char value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(CharSequence value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(Date value, DateMode mode) {
        return renderer.value(value, mode);
    }

    @Override
    public JSONRenderer value(Date value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(double value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(Enum<?> value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(float value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(int value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(long value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(Object value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer value(short value) {
        return renderer.value(value);
    }

    @Override
    public JSONRenderer values(Iterable<?> values) {
        return renderer.values(values);
    }

    @Override
    public JSONRenderer values(Iterator<?> values) {
        return renderer.values(values);
    }

    @Override
    public JSONRenderer values(JSONListable values) {
        return renderer.values(values);
    }

    @Override
    public JSONRenderer values(Object... values) {
        return renderer.values(values);
    }
    
}
