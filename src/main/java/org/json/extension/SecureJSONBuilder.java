package org.json.extension;

import org.json.JSONException;
import org.json.JSONStringer;

/**
 * 
 * Provides error-handling for the JSONBuilder
 * for use in a development environment.
 * 
 * @author schoenborn
 */
public class SecureJSONBuilder extends JSONBuilder {

	private static final byte OBJECT = 0x01;
	private static final byte ARRAY  = 0x02;
	private static final byte KEY    = 0x04;
	
	private byte[] stack = new byte[SIZE+1];
	private int peek = -1;
	
	protected SecureJSONBuilder() {}

	@Override
	public JSONConstructor array() throws JSONException {
		
		if(peek > 0 && (stack[peek] & (KEY|ARRAY)) == 0) {
			throw new JSONException("Misplaced array.");
		}

		super.array();
		
		stack[++peek] = ARRAY;
		return this;
	}

	@Override
	public JSONConstructor endArray() throws JSONException {

		if((peek < 0) || (stack[peek--] != ARRAY)) {
			throw new JSONException("Misplaced endArray.");
		}
		
		return super.endArray();
	}

	@Override
	public JSONConstructor object() throws JSONException {
		
		if(peek > 0 && (stack[peek] & (KEY|ARRAY)) == 0) {
			throw new JSONException("Misplaced object.");
		}

		super.object();
		
		stack[++peek] = OBJECT;
		
		return this;
	}

	@Override
	public JSONConstructor endObject() throws JSONException {

		if((peek < 0) || stack[peek--] != OBJECT) {
			throw new JSONException("Misplaced endObject.");
		}
		
		return super.endObject();
	}

	@Override
	public JSONConstructor key(String key) throws JSONException {
		
		if(peek+1 > SIZE) throw new JSONException("Nesting too deep.");
		
		if(key == null || key.equals("")) {
			throw new JSONException("Null or empty key.");
		}
		
		if(peek < 0 || stack[peek] != OBJECT) {
			throw new JSONException("Misplaced key.");
		}
		
		stack[++peek] = KEY;
		
		return super.key(key);
	}

	@Override
	protected JSONConstructor put(Object value) throws JSONException {
		
		if(peek+1 > SIZE) throw new JSONException("Nesting too deep.");
		
		if(peek < 0 || (stack[peek] & (ARRAY|KEY)) == 0) {
			throw new JSONException("Misplaced value.");
		}
	
		if(stack[peek] == KEY) peek--;
		
		return super.put(value);
	}

	public static void main(String[] args) throws JSONException {
		
		SecureJSONBuilder builder = new SecureJSONBuilder();
		JSONStringer stringer = new JSONStringer();

		String s1 = encode(builder);
		String s2 = encode(stringer);
		
		System.out.println(s1);
		System.out.println("\n");
		System.out.println(s2);
		System.out.println("\n");
		System.out.println("Equals: " + s1.equals(s2));
		
	}
	
	private static String encode(JSONConstructor json) throws JSONException {
		
		json.
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
			array().
				value(123).
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray().
			endArray();

		return json.toString();
	}
}