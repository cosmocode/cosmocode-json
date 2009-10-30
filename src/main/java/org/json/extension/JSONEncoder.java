package org.json.extension;

import org.json.JSONException;

/**
 * No documentation here.
 * 
 * @deprecated use {@link JSONListable} or {@link JSONMapable} instead
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
@Deprecated
public interface JSONEncoder {

    /**
     * Encodes this instance to JSON using the given {@link JSONConstructor}.
     * 
     * @param json the {@link JSONConstructor} to work on
     * @throws JSONException if an error occurs
     */
    void encodeJSON(JSONConstructor json) throws JSONException;
    
}
