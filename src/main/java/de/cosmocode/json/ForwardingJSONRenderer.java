package de.cosmocode.json;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import com.google.common.collect.ForwardingObject;

import de.cosmocode.commons.DateMode;

/**
 * Forwards all calls to an underlying {@link JSONRenderer}
 * allowing sub classes to hook into executions.
 *
 * @author Willi Schoenborn
 */
public abstract class ForwardingJSONRenderer extends ForwardingObject implements JSONRenderer {

    @Override
    protected abstract JSONRenderer delegate();

    @Override
    public JSONRenderer array() {
        delegate().array();
        return this;
    }

    @Override
    public JSONRenderer array(Object... values) {
        delegate().array(values);
        return this;
    }

    @Override
    public JSONRenderer array(Iterable<?> values) {
        delegate().array(values);
        return this;
    }

    @Override
    public JSONRenderer array(Iterator<?> values) {
        delegate().array(values);
        return this;
    }

    @Override
    public JSONRenderer array(JSONListable values) {
        delegate().array(values);
        return this;
    }

    @Override
    public JSONRenderer endArray() {
        delegate().endArray();
        return this;
    }

    @Override
    public JSONRenderer endObject() {
        delegate().endObject();
        return this;
    }

    @Override
    public JSONRenderer key(CharSequence key) {
        delegate().key(key);
        return this;
    }

    @Override
    public JSONRenderer key(Object key) {
        delegate().key(key);
        return this;
    }

    @Override
    public JSONRenderer nullValue() {
        delegate().nullValue();
        return this;
    }

    @Override
    public JSONRenderer object() {
        delegate().object();
        return this;
    }

    @Override
    public JSONRenderer object(JSONEncoder object) {
        delegate().object(object);
        return this;
    }

    @Override
    public JSONRenderer object(JSONMapable pairs) {
        delegate().object(pairs);
        return this;
    }

    @Override
    public JSONRenderer object(Map<?, ?> pairs) {
        delegate().object(pairs);
        return this;
    }

    @Override
    public JSONRenderer object(NoObjectContext pairs) {
        delegate().object(pairs);
        return this;
    }

    @Override
    public JSONRenderer pairs(JSONMapable pairs) {
        delegate().pairs(pairs);
        return this;
    }

    @Override
    public JSONRenderer pairs(Map<?, ?> pairs) {
        delegate().pairs(pairs);
        return this;
    }

    @Override
    public JSONRenderer pairs(NoObjectContext pairs) {
        delegate().pairs(pairs);
        return this;
    }

    @Override
    public String toString() {
        return delegate().toString();
    }

    @Override
    public JSONRenderer value(boolean value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(CharSequence value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(Date value, DateMode mode) {
        delegate().value(value, mode);
        return this;
    }

    @Override
    public JSONRenderer value(Date value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(double value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(Enum<?> value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(long value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer value(Object value) {
        delegate().value(value);
        return this;
    }

    @Override
    public JSONRenderer values(Iterable<?> values) {
        delegate().values(values);
        return this;
    }

    @Override
    public JSONRenderer values(Iterator<?> values) {
        delegate().values(values);
        return this;
    }

    @Override
    public JSONRenderer values(JSONListable values) {
        delegate().values(values);
        return this;
    }

    @Override
    public JSONRenderer values(Object... values) {
        delegate().values(values);
        return this;
    }
    
}
