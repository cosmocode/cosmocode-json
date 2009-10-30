package de.cosmocode.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.acl.NotOwnerException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.extension.JSONEncoder;

import de.cosmocode.commons.DateMode;

/**
 * A {@link JSONRenderer} is being used to create
 * JSON strings.
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
public interface JSONRenderer {

    JSONRenderer array();
    
    JSONRenderer endArray();
    
    JSONRenderer object();
    
    JSONRenderer endObject();
    
    
    
    JSONRenderer key(CharSequence key);
    
    JSONRenderer key(Object key);
    
    
    JSONRenderer nullValue();
    
    JSONRenderer value(Object value);
    
    
    JSONRenderer value(boolean value);
    
    JSONRenderer value(byte value);
    
    JSONRenderer value(short value);
    
    JSONRenderer value(char value);
    
    JSONRenderer value(int value);
    
    JSONRenderer value(long value);
    
    JSONRenderer value(float value);
    
    JSONRenderer value(double value);
    
    JSONRenderer value(BigInteger value);
    
    JSONRenderer value(BigDecimal value);
    
    JSONRenderer value(Date value);
    
    /**
     * TODO
     * 
     * Will produce "null" if either date or mode is null (or both).
     * 
     * @param value
     * @param mode
     * @return
     */
    JSONRenderer value(Date value, DateMode mode);
    
    <E extends Enum<E>> JSONRenderer value(E value);
    
    JSONRenderer value(CharSequence value);
    
    
    
    <E> JSONRenderer values(E... values);
    
    <E> JSONRenderer values(Iterable<E> values);
    
    <E> JSONRenderer values(Iterator<E> values);

    <E> JSONRenderer values(JSONListable values);
    
    
    
    <E> JSONRenderer array(E... values);
    
    <E> JSONRenderer array(Iterable<E> values);
    
    <E> JSONRenderer array(Iterator<E> values);
    
    <E> JSONRenderer array(JSONListable values);
    
    
    
    <K, V> JSONRenderer pairs(Map<? extends K, ? extends V> pairs);
    
    JSONRenderer pairs(JSONMapable pairs);
    
    /**
     * 
     * @deprecated use {@link JSONMapable} instead
     * 
     * @param <T>
     * @param pairs
     * @return
     */
    @Deprecated
    <T extends JSONEncoder & NoObjectContext> JSONRenderer pairs(T pairs);
    
    
    
    <K, V> JSONRenderer object(Map<? extends K, ? extends V> pairs);
    
    JSONRenderer object(JSONMapable pairs);
    
    /**
     * 
     * @deprecated use {@link JSONMapable} instead
     * 
     * @param <T>
     * @param pairs
     * @return
     */
    @Deprecated
    <T extends JSONEncoder & NoObjectContext> JSONRenderer object(T pairs);
    
    /**
     * This method only exists for backwards compatability reasons.
     * See {@link JSONWriter#plain(String)}. 
     * 
     * @deprecated use strongly typed methods instead
     * 
     * @param json a well-formed JSON string
     * @return this
     */
    @Deprecated
    JSONRenderer plain(String json);
    
    
    @Override
    public String toString();
    
}
