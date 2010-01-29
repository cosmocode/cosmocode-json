package org.json.extension;

import org.json.JSONException;

import de.cosmocode.json.JSONListable;
import de.cosmocode.json.JSONMapable;

/**
 * No documentation here.
 * 
 * @deprecated use {@link JSONMapable} or {@link JSONListable} instead
 * 
 * @author Willi Schoenborn
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
