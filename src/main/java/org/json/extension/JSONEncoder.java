package org.json.extension;

import org.json.JSONException;

public interface JSONEncoder {

	public void encodeJSON (JSONConstructor json) throws JSONException;
	
}