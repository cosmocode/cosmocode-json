package de.cosmocode.json;

/**
 * Marker interface for {@link JSONEncoder} implementations that dont have opening
 * and closing {@link JSONConstructor#object()} and {@link JSONConstructor#endObject()}
 * calls in their {@link JSONEncoder#encodeJSON(JSONConstructor)} method.
 * 
 * @author schoenborn@cosmocode.de
 * 
 */
public interface NoObjectContext {

}
