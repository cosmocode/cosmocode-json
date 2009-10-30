package org.json.extension;

import org.json.JSONException;

/**
 * A JSONConstructor provides several methods
 * for bean/pojo-classes to build their
 * own json structure.
 * 
 * @deprecated use JSONRenderer instead or consider using
 *             adapter factory methods in {@link JSON}, if
 *             you face legacy code.
 * 
 * @author schoenborn
 */
@Deprecated
public interface JSONConstructor {

    public JSONConstructor array() throws JSONException;
    public JSONConstructor endArray() throws JSONException;    
    public JSONConstructor endObject() throws JSONException;
    public JSONConstructor key(String key) throws JSONException;
    public JSONConstructor object() throws JSONException;    
    public JSONConstructor plain(String value) throws JSONException;
    public JSONConstructor value(boolean value) throws JSONException;
    public JSONConstructor value(double value) throws JSONException;
    public JSONConstructor value(long value) throws JSONException;
    public JSONConstructor value(Object value) throws JSONException;
    
}
