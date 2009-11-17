package org.json.extension;

import org.json.JSONException;

/**
 * No documentation here.
 * 
 * @author Willi Schoenborn
 */
public interface JSONEncoder {

    /**
     * Encodes this instance to JSON using the given {@link JSONConstructor}.
     * 
     * @param json the {@link JSONConstructor} to work on
     * @throws JSONException if an error occurs
     */
    void encodeJSON(JSONConstructor json) throws JSONException;
    
}
