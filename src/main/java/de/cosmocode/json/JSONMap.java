package de.cosmocode.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.json.JSONException;
import org.json.JSONObject;

import de.cosmocode.collections.utility.AbstractUtilityMap;

class JSONMap extends AbstractUtilityMap<String, Object> {

	private final JSONObject json;
	private Set<String> keySet;
	private Collection<Object> values;
	private Set<Map.Entry<String, Object>> entrySet;
	
	private final Transformer keyTransformer = new Transformer() {
		
		@Override
		public Object transform(Object key) {
			return key;
		}
		
	};
	
	private final Transformer valueTransformer = new Transformer() {
		
		@Override
		public Object transform(Object key) {
			return JSONMap.this.get(key);
		}
		
	};
	
	private final Transformer entryTransformer = new Transformer() {
		
		@Override
		public Object transform(Object key) {
			return new Entry(String.class.cast(key), JSONMap.this.get(key));
		}
		
	};
	
	public JSONMap(JSONObject json) {
		if (json == null) throw new IllegalArgumentException("JSONObject can't be null");
		this.json = json;
	}
	
	private void reset() {
		keySet = null;
		values = null;
		entrySet = null;
	}
	
	@Override
	public int size() {
		return json.length();
	}
	
	@Override
	public boolean isEmpty() {
		return json.length() == 0;
	}
	
	@Override
	public boolean containsKey(Object key) {
		return key instanceof String && json.has(String.class.cast(key));
	}
	
	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}
	
	@Override
	public Object get(Object key) {
		return key instanceof String ? json.opt(String.class.cast(key)) : null;
	}
	
	@Override
	public Object put(String key, Object value) {
		try {
			return json.put(key, value);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		} finally {
			reset();
		}
	}
	
	@Override
	public Object remove(Object key) {
		try {
			return key instanceof String ? json.remove(String.class.cast(key)) : null;
		} finally {
			reset();
		}
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		for (Map.Entry<? extends String, ? extends Object> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
		reset();
	}
	
	@Override
	public void clear() {
		for (String key : keySet()) remove(key);
		reset();
	}
	
	@Override
	public Set<String> keySet() {
		if (keySet == null) {
			keySet = new LinkedHashSet<String>();
			CollectionUtils.collect(json.keys(), keyTransformer, keySet);
		}
		return keySet;
	}
	
	@Override
	public Collection<Object> values() {
		if (values == null) {
			values = new LinkedList<Object>();
			CollectionUtils.collect(json.keys(), valueTransformer, values);
		}
		return values;
	} 
	
	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		if (entrySet == null) {
			entrySet = new LinkedHashSet<Map.Entry<String,Object>>();
			CollectionUtils.collect(json.keys(), entryTransformer, entrySet);
		}
		return entrySet;
	}
	
	private static class Entry implements Map.Entry<String, Object> {
		 
		private final String key;
		private Object value;
		 
		public Entry(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String getKey() {
			return key;
		}
		
		@Override
		public Object getValue() {
			return value;
		}
		
		@Override
		public Object setValue(Object value) {
			final Object oldValue = this.value;
			this.value = value;
			return oldValue;
		}
		
		@Override
		public String toString() {
			return key + "=" + value;
		}
		
	}
	
	@Override
	public boolean getBoolean(String key) {
		try {
			return json.getBoolean(key);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return json.optBoolean(key, defaultValue);
	}
	
	@Override
	public byte getByte(String key, byte defaultValue) {
		return containsKey(key) ? Number.class.cast(json.optInt(key)).byteValue() : defaultValue;
	}
	
	@Override
	public short getShort(String key, short defaultValue) {
		return containsKey(key) ? Number.class.cast(json.optInt(key)).shortValue() : defaultValue;
	}
	
	@Override
	public int getInt(String key) throws IllegalArgumentException {
		try {
			return json.getInt(key);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public int getInt(String key, int defaultValue) {
		return json.optInt(key, defaultValue);
	}
	
	@Override
	public long getLong(String key) throws IllegalArgumentException {
		try {
			return json.getLong(key);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public long getLong(String key, long defaultValue) {
		return json.optLong(key, defaultValue);
	}
	
	@Override
	public float getFloat(String key, float defaultValue) {
		return containsKey(key) ? Number.class.cast(json.optDouble(key)).floatValue() : defaultValue;
	}
	
	@Override
	public double getDouble(String key) throws IllegalArgumentException {
		try {
			return json.getDouble(key);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public double getDouble(String key, double defaultValue) {
		return json.optDouble(key, defaultValue);
	}
	
	@Override
	public BigDecimal getBigDecimal(String key) throws IllegalArgumentException {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	@Override
	public BigInteger getBigInteger(String key) throws IllegalArgumentException {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	@Override
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	@Override
	public Date getDate(String key) throws IllegalArgumentException {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	@Override
	public Date getDate(String key, Date defaultValue) {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) throws IllegalArgumentException {
	    // TODO Auto-generated method stub
	    return null;
	}

    @Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType, T defaultValue) {
        // TODO Auto-generated method stub
        return null;
	};
	
	@Override
	public String getString(String key) throws IllegalArgumentException {
		try {
			return json.getString(key);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public String getString(String key, String defaultValue) {
		return json.optString(key, defaultValue);
	}
	
}