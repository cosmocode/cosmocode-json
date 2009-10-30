package de.cosmocode.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import de.cosmocode.commons.DateMode;

/**
 * Abstract skeleton implementation of the
 * {@link JSONRenderer} interface.
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
public abstract class AbstractJSONRenderer implements JSONRenderer {

    @Override
    public JSONRenderer key(Object key) {
        return key(key == null ? "null" : key.toString());
    }
    
    @Override
    public JSONRenderer value(Object value) {
        // TODO add all supported types
        
        if (value == null) {
            return nullValue();
        } else if (value instanceof CharSequence) {
            return value(CharSequence.class.cast(value).toString());
        } else if (value instanceof Float) {
            return value(Float.class.cast(value).floatValue());
        } else if (value instanceof Double) {
            return value(Double.class.cast(value).doubleValue());
        } else if (value instanceof Number) {
            return value(Number.class.cast(value).longValue());
        } else {
            return value(value.toString());
        }
    }
    
    
    
    @Override
    public JSONRenderer value(byte value) {
        return value((long) value);
    }

    @Override
    public JSONRenderer value(short value) {
        return value((long) value);
    }

    @Override
    public JSONRenderer value(char value) {
        return value(Character.toString(value));
    }

    @Override
    public JSONRenderer value(int value) {
        return value((long) value);
    }

    @Override
    public JSONRenderer value(float value) {
        return value((double) value);
    }

    @Override
    public JSONRenderer value(BigDecimal value) {
        return value == null ? nullValue() : value(value.toString());
    }

    @Override
    public JSONRenderer value(BigInteger value) {
        return value == null ? nullValue() : value(value.toString());
    }

    @Override
    public JSONRenderer value(Date value) {
        return value == null ? nullValue() : value(value, DateMode.JAVA);
    }

    @Override
    public JSONRenderer value(Date value, DateMode mode) {
        if (value == null || mode == null) return nullValue();
        return value(mode.parse(value));
    }

    @Override
    public <E extends Enum<E>> JSONRenderer value(E value) {
        return value == null ? nullValue() : value(value.name());
    }

    @Override
    public <E> JSONRenderer values(E... values) {
        if (values == null) return this;
        for (E value : values) {
            value(value);
        }
        return this;
    }

    @Override
    public <E> JSONRenderer values(Iterable<E> values) {
        if (values == null) return this;
        for (E value : values) {
            value(value);
        }
        return this;
    }

    @Override
    public <E> JSONRenderer values(Iterator<E> values) {
        if (values == null) return this;
        while (values.hasNext()) {
            value(values.next());
        }
        return this;
    }

    @Override
    public <E> JSONRenderer values(JSONListable values) {
        return values == null ? this : values.render(this);
    }

    @Override
    public <E> JSONRenderer array(E... values) {
        return array().values(values).endArray();
    }

    @Override
    public <E> JSONRenderer array(Iterable<E> values) {
        return array().values(values).endArray();
    }

    @Override
    public <E> JSONRenderer array(Iterator<E> values) {
        return array().values(values).endArray();
    }

    @Override
    public <E> JSONRenderer array(JSONListable values) {
        return array().values(values).endArray();
    }

    @Override
    public <K, V> JSONRenderer pairs(Map<? extends K, ? extends V> pairs) {
        if (pairs == null) return this;
        for (Map.Entry<? extends K, ? extends V> entry : pairs.entrySet()) {
            key(entry.getKey()).value(entry.getValue());
        }
        return this;
    }

    @Override
    public JSONRenderer pairs(JSONMapable pairs) {
        return pairs == null ? this : pairs.render(this);
    }

    @Override
    public <K, V> JSONRenderer object(Map<? extends K, ? extends V> pairs) {
        return object().pairs(pairs).endObject();
    }

    @Override
    public JSONRenderer object(JSONMapable pairs) {
        return object().pairs(pairs).endObject();
    }

}
