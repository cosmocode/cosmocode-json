package org.json.extension;

import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Builds a JSONArray or JSONObject, regarding the first method
 * called (either array() or object()).
 * The final untyped construct can obtained using getConstruct()
 * 
 * @author schoenborn
 */
public class JSONBuilder implements JSONConstructor {
	
	protected static final int SIZE = 20;
	
	private JSONArray array;
	private JSONObject object;
	
	private Stack<Object> nodes = new Stack<Object>();
	private String key;
	
	static private boolean secure = true;
	
	static {
		secure = Boolean.parseBoolean(System.getProperty("securejson"));
	}
	
	static public JSONConstructor createJSONBuilder() {
		return secure ? new SecureJSONBuilder() : new JSONBuilder();
	}
	
	protected JSONBuilder() {}

	@Override
	public JSONConstructor array() throws JSONException {
		
		if(array == null && object == null) {
			array = new JSONArray();
		} else {
			JSONArray array = new JSONArray();
			put(array);
			nodes.push(array);
		}
		
		return this;
	}

	@Override
	public JSONConstructor endArray() throws JSONException {
		if(!nodes.isEmpty()) nodes.pop();
		return this;
	}

	@Override
	public JSONConstructor object() throws JSONException {
		
		if(array == null && object == null) {
			object = new JSONObject();
		} else {
			JSONObject object = new JSONObject();
			put(object);
			nodes.push(object);
		}
		
		return this;
	}

	@Override
	public JSONConstructor endObject() throws JSONException {
		if(!nodes.isEmpty()) nodes.pop();
		return this;
	}

	@Override
	public JSONConstructor key(String key) throws JSONException {
		this.key = key;
		return this;
	}

	@Override
	public JSONConstructor plain(String value) throws JSONException {		
		if (value.startsWith("{")) {
			return put(new JSONObject(value));
		} else if (value.startsWith("[")) {
			return put(new JSONArray(value));
		} else {
			//FIXME ?
			return put(value);
		}
	}

	@Override
	public JSONConstructor value(boolean value) throws JSONException {
		return put(value);
	}

	@Override
	public JSONConstructor value(double value) throws JSONException {
		return put(value);
	}

	@Override
	public JSONConstructor value(long value) throws JSONException {
		return put(value);
	}

	@Override
	public JSONConstructor value(Object value) throws JSONException {
		
		if(value instanceof JSONEncoder) {
			((JSONEncoder) value).encodeJSON(this);
		} else {
			put(value);
		}
		
		return this;
	}
	
	protected JSONConstructor put(Object value) throws JSONException {
		
		if(value == null) value = JSONObject.NULL;
			
		if(nodes.isEmpty()) {
			
			if(array != null) {
				array.put(value);
			} else {
				object.put(key, value);
			}

		} else if(nodes.peek() instanceof JSONArray) {
			JSONArray array = (JSONArray) nodes.peek();
			array.put(value);
		} else if(nodes.peek() instanceof JSONObject) {
			JSONObject object = (JSONObject) nodes.peek();
			object.put(key, value);
		}
		
		return this;
	}
	
	public Object getConstruct() {
		return array != null ? array : object;
	}
	
	public String toString(int indentFactor) throws JSONException {
		return array != null ? array.toString(indentFactor) : object.toString(indentFactor);
	}
	
	public String toString() {
		return array != null ? array.toString() : object.toString();
	}
	
	public static void main(String[] args) throws JSONException {
		
		JSONBuilder builder = new JSONBuilder();		
		
		builder.
			object().
				key("string").value("test").
				key("number").value(13).
				key("array").
					array().
						value(true).
						value(123).
						value("hallo").
						array().
							value(false).
							value(123432).
							value("bye").
						endArray().
					endArray().
				key("object").
					object().
						key("key").value("string").
						key("number").value(53).
						key("bool").value(true).
						key("array").value(new String[]{"test", "super", "bla"}).
						key("objec").
							object().
								key("text").value("obj").
								key("number").value(1244123).
								key("bool").value(false).
								key("float").value(123E74).
							endObject().
					endObject().
			endObject();

		System.out.println(builder.toString(3));
		
		builder = new JSONBuilder();
		
		builder.
			array().
				value(true).
				value(false).
				value(null).
				value(123).
				value("string").
				value("test").
				value(null).
				value(new String[] {"eins", "zwei", "drei"}).
				object().
					key("key").value("string").
					key("number").value(53).
					key("bool").value(true).
					key("array").value(new String[]{"test", "super", "bla"}).
					key("object").
						object().
							key("text").value("obj").
							key("number").value(1244123).
							key("bool").value(false).
							key("float").value(123E74).
						endObject().
				endObject().
			endArray();

		System.out.println(builder.toString(3));
		
	}
}