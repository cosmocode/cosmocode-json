package de.cosmocode.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Multimap;

import de.cosmocode.commons.DateMode;

/**
 * Abstract skeleton implementation of the
 * {@link JSONRenderer} interface.
 * 
 * @author Willi Schoenborn
 */
public abstract class AbstractJSONRenderer implements JSONRenderer {

    private final DateMode dateMode;
    
    /**
     * Creates a new {@link AbstractJSONRenderer} using the
     * given {@link DateMode} instance for {@link Date} conversions.
     * 
     * @param dateMode the  {@link DateMode} to use for Date conversions
     * @throws NullPointerException if dateMode is null
     */
    public AbstractJSONRenderer(DateMode dateMode) {
        if (dateMode == null) throw new NullPointerException("DateMode must not be null");
        this.dateMode = dateMode;
    }
    
    @Override
    public JSONRenderer key(Object key) {
        return key(key == null ? "null" : key.toString());
    }
    
    @Override
    public JSONRenderer value(Object value) {
        if (value == null) {
            return nullValue();
        } else if (value instanceof CharSequence) {
            return value(CharSequence.class.cast(value).toString());
        } else if (value instanceof JSONRenderable) {
            return value(JSONRenderable.class.cast(value));
        } else if (value instanceof Boolean) {
            return value(Boolean.class.cast(value).booleanValue());
        } else if (value instanceof Number) {
            return value(Number.class.cast(value));
        } else if (value instanceof Date) {
            return value(Date.class.cast(value), dateMode);
        } else if (value instanceof Calendar) {
            return value(Calendar.class.cast(value).getTime(), dateMode);
        } else if (value.getClass().isEnum()) {
            return value(Enum.class.cast(value));
        } else {
            return compoundValue(value);
        }
    }
    
    /**
     * Groups all {@link JSONRenderable}, therefore
     * custom JSON specific values.
     * 
     * @param value the {@link JSONRenderable} value
     * @return this
     */
    private JSONRenderer value(JSONRenderable value) {
        if (value instanceof JSONListable) {
            return array(JSONListable.class.cast(value));
        } else if (value instanceof JSONMapable) {
            return object(JSONMapable.class.cast(value));
        } else {
            return unknownValue(value);
        }
    }
    
    /**
     * Groups all {@link Number} values.
     * 
     * @param value the {@link Number} value
     * @return this
     */
    private JSONRenderer value(Number value) {
        if (value instanceof Byte) {
            return value(Byte.class.cast(value).byteValue());
        } else if (value instanceof Short) {
            return value(Short.class.cast(value).shortValue());
        } else if (value instanceof Integer) {
            return value(Integer.class.cast(value).intValue());
        } else if (value instanceof Long) {
            return value(Long.class.cast(value).longValue());
        } else if (value instanceof Float) {
            return value(Float.class.cast(value).floatValue());
        } else if (value instanceof Double) {
            return value(Double.class.cast(value).doubleValue());
        } else {
            return unknownValue(value);
        }
    }
    
    /**
     * Groups all kind of collection style values.
     * 
     * @param value the untyped value
     * @return this
     */
    private JSONRenderer compoundValue(Object value) {
        if (value.getClass().isArray()) {
            return array(Object[].class.cast(value));
        } else if (value instanceof Iterable<?>) {
            return array(Iterable.class.cast(value));
        } else if (value instanceof Iterator<?>) {
            return array(Iterator.class.cast(value));
        } else if (value instanceof Map<?, ?>) {
            return object(Map.class.cast(value));
        } else if (value instanceof Multimap<?, ?>) {
            return object(Multimap.class.cast(value).asMap());
        } else {
            return unknownValue(value);
        }
    }
    
    /**
     * A hook allowing sub classes to add behaviour to 
     * the {@link AbstractJSONRenderer#value(Object)} method.
     * 
     * <p>
     *   This implementation uses {@link Object#toString()}.
     * </p>
     * 
     * @param value the value of an unknown type, is never null
     * @return this
     */
    protected JSONRenderer unknownValue(Object value) {
        return value(value.toString());
    }
    
    @Override
    public JSONRenderer value(Date value) {
        return value == null ? nullValue() : value(value, dateMode);
    }

    @Override
    public JSONRenderer value(Date value, DateMode mode) {
        if (mode == null) throw new NullPointerException("DateMode must not be null");
        if (value == null) return nullValue();
        return value(mode.parse(value));
    }

    @Override
    public JSONRenderer value(Enum<?> value) {
        return value == null ? nullValue() : value(value.name());
    }

    @Override
    public JSONRenderer values(Object... values) {
        if (values == null) return this;
        for (Object value : values) {
            value(value);
        }
        return this;
    }

    @Override
    public JSONRenderer values(Iterable<?> values) {
        if (values == null) return this;
        for (Object value : values) {
            value(value);
        }
        return this;
    }

    @Override
    public JSONRenderer values(Iterator<?> values) {
        if (values == null) return this;
        while (values.hasNext()) {
            value(values.next());
        }
        return this;
    }

    @Override
    public JSONRenderer values(JSONListable values) {
        return values == null ? this : values.renderAsList(this);
    }

    @Override
    public <E> JSONRenderer array(E... values) {
        return array().values(values).endArray();
    }

    @Override
    public JSONRenderer array(Iterable<?> values) {
        return array().values(values).endArray();
    }

    @Override
    public JSONRenderer array(Iterator<?> values) {
        return array().values(values).endArray();
    }

    @Override
    public JSONRenderer array(JSONListable values) {
        return array().values(values).endArray();
    }

    @Override
    public JSONRenderer pairs(Map<?, ?> pairs) {
        if (pairs == null) return this;
        for (Map.Entry<?, ?> entry : pairs.entrySet()) {
            key(entry.getKey()).value(entry.getValue());
        }
        return this;
    }

    @Override
    public JSONRenderer pairs(JSONMapable pairs) {
        return pairs == null ? this : pairs.renderAsMap(this);
    }

    @Override
    public JSONRenderer object(Map<?, ?> pairs) {
        return object().pairs(pairs).endObject();
    }

    @Override
    public JSONRenderer object(JSONMapable pairs) {
        return object().pairs(pairs).endObject();
    }
    
    @Override
    public abstract String toString();

}
