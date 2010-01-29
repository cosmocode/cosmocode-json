package org.json.extension;

import de.cosmocode.json.JSONListable;

/**
 * Marker interface for {@link JSONEncoder} implementations that dont have opening
 * and closing {@link JSONConstructor#object()} and {@link JSONConstructor#endObject()}
 * calls in their {@link JSONEncoder#encodeJSON(JSONConstructor)} method.
 * 
 * @deprecated use {@link JSONMapable} or {@link JSONListable} instead
 * 
 * @author schoenborn@cosmocode.de
 */
@Deprecated
public interface NoObjectContext extends JSONEncoder {

}
