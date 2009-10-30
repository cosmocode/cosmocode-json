package de.cosmocode.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import de.cosmocode.commons.DateMode;

/**
 * A {@link JSONRenderer} is being used to create
 * one (!) JSON string.
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
public interface JSONRenderer {

    /**
     * Starts an array structure.
     * 
     * @throws IllegalStateException if array structure is not
     *         allowed at the current position
     * @return this
     */
    JSONRenderer array();
    
    /**
     * Ends an array structure.
     * 
     * @throws IllegalStateException if there is no array structure
     *         to close at the current position
     * @return this
     */
    JSONRenderer endArray();

    /**
     * Starts an object structure.
     * 
     * @throws IllegalStateException if object structure is not
     *         allowed at the current position
     * @return this
     */
    JSONRenderer object();

    /**
     * Ends an object structure.
     * 
     * @throws IllegalStateException if there is no object structure
     *         to close at the current position
     * @return this
     */
    JSONRenderer endObject();

    /**
     * Adds a key. If key is null, the {@link String}
     * {@code "null"} will be used instead.
     * 
     * @param key the key value, may be null
     * @return this
     */
    JSONRenderer key(CharSequence key);

    /**
     * Adds a key. If key is null, the {@link String}
     * {@code "null"} will be used instead.
     * 
     * @param key the key value, may be null
     * @return this
     */
    JSONRenderer key(Object key);
    
    /**
     * Adds a null value.
     * 
     * @return this
     */
    JSONRenderer nullValue();
    
    /**
     * Adds an untyped value.
     * 
     * Will return {@link JSONRenderer#nullValue()} if value is null.
     * 
     * @param value the value, may be null
     * @return this
     */
    JSONRenderer value(Object value);
    
    /**
     * Adds a boolean value.
     * 
     * @param value the value being added
     * @return this
     */
    JSONRenderer value(boolean value);
    
    /**
     * Adds a byte value.
     * 
     * @param value the value being added
     * @return this
     */
    JSONRenderer value(byte value);
    
    /**
     * Adds a short value.
     * 
     * @param value the value being added
     * @return this
     */
    JSONRenderer value(short value);
    
    /**
     * Adds a char value.
     * 
     * @param value the value being added
     * @return this
     */
    JSONRenderer value(char value);
    
    /**
     * Adds an int value.
     * 
     * @param value the value being added
     * @return this
     */
    JSONRenderer value(int value);
    
    /**
     * Adds a long value.
     * 
     * @param value the value being added
     * @return this
     */
    JSONRenderer value(long value);
    
    /**
     * Adds a float value. Infinity and NaN are
     * not allowed.
     * 
     * @param value the value being added
     * @throws IllegalArgumentException if value is infinite or NaN 
     * @return this
     */
    JSONRenderer value(float value);
    
    /**
     * Adds a double value. Infinity and NaN are
     * not allowed.
     * 
     * @param value the value being added
     * @throws IllegalArgumentException if value is infinite or NaN 
     * @return this
     */
    JSONRenderer value(double value);
    
    /**
     * Adds a {@link BigInteger} value.
     * 
     * Will return {@link JSONRenderer#nullValue()} if value is null.
     * 
     * @param value the value being added, may be null
     * @return this
     */
    JSONRenderer value(BigInteger value);
    
    /**
     * Adds a {@link BigDecimal} value.
     * 
     * Will return {@link JSONRenderer#nullValue()} if value is null.
     * 
     * @param value the value being added, may be null
     * @return this
     */
    JSONRenderer value(BigDecimal value);
    
    /**
     * Adds a {@link Date} value. Uses {@link DateMode#JAVA}
     * for {@link Date} conversion.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.value(date, DateMode.JAVA);
     *   </pre>
     * </p>
     * 
     * Will return {@link JSONRenderer#nullValue()} if value is null.
     * 
     * @param value the value being added, may be null
     * @return this
     */
    JSONRenderer value(Date value);
    
    /**
     * Adds a {@link Date} value using the given mode for
     * {@link Date} conversions.
     * 
     * Will return {@link JSONRenderer#nullValue()} if either value or mode is null.
     * 
     * @param value the value being added
     * @param mode the mode being used for {@link Date} conversions
     * @return this
     */
    JSONRenderer value(Date value, DateMode mode);
    
    /**
     * Adds an enum value by using its {@link Enum#name()} method.
     * 
     * Will return {@link JSONRenderer#nullValue()} if value is null.
     * 
     * @param value the value being added, may be null
     * @return this
     */
    JSONRenderer value(Enum<?> value);
    
    /**
     * Adds a {@link CharSequence} value.

     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if value is null.
     * </p>
     *  
     * @param value the value being added, may be null
     * @return this
     */
    JSONRenderer value(CharSequence value);
    
    /**
     * Adds all the values contained in an array by calling
     * {@link JSONRenderer#value(Object)} for each element.
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param <E> the array's element type
     * @param values the values being added, may be null
     * @return this
     */
    <E> JSONRenderer values(E... values);
    
    /**
     * Adds all the values contained in an {@link Iterable} by calling
     * {@link JSONRenderer#value(Object)} for each element.
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param values the values being added, may be null
     * @return this
     */
    JSONRenderer values(Iterable<?> values);
    
    /**
     * Adds all the values returned by an {@link Iterator} by calling
     * {@link JSONRenderer#value(Object)} for each element.
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param values the values being added, may be null
     * @return this
     */
    JSONRenderer values(Iterator<?> values);

    /**
     * Adds a {@link JSONListable} value by giving the control
     * to it's {@link JSONListable#renderAsList(JSONRenderer)} method.
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param values the values being added, may be null
     * @return this
     */
    JSONRenderer values(JSONListable values);
    
    /**
     * Starts an array structure, adds all the values contained in an
     * array by calling {@link JSONRenderer#value(Object)} for each
     * element and ends the array structure.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.array().values(values).endArray();
     *   </pre>
     * </p>
     * 
     * <p>
     *   Will produce an empty array if values is null.
     * </p>
     * 
     * @param <E> the array's element types
     * @param values the values being added as an array, may be null
     * @return this
     */
    <E> JSONRenderer array(E... values);
    
    /**
     * Starts an array structure, adds all the values contained in an
     * {@link Iterable} by calling {@link JSONRenderer#value(Object)} for
     * each element and ends the array structure.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.array().values(values).endArray();
     *   </pre>
     * </p>
     * 
     * <p>
     *   Will produce an empty array if values is null.
     * </p>
     * 
     * @param values the values being added as an array, may be null
     * @return this
     */
    JSONRenderer array(Iterable<?> values);
    
    /**
     * Starts an array structure, adds all the values returned by an
     * {@link Iterator} by calling {@link JSONRenderer#value(Object)} for
     * each element and ends the array structure.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.array().values(values).endArray();
     *   </pre>
     * </p>
     * 
     * <p>
     *   Will produce an empty array if values is null.
     * </p>
     * 
     * 
     * @param values the values being added as an array, may be null
     * @return this
     */
    JSONRenderer array(Iterator<?> values);
    
    /**
     * Starst an array structure, adds a {@link JSONListable} value by
     * giving the control to it's {@link JSONListable#renderAsList(JSONRenderer)}
     * method and ends the array structure.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.array().values(values).endArray();
     *   </pre>
     * </p>
     * 
     * <p>
     *   Will produce an empty array if values is null.
     * </p>
     * 
     * 
     * @param values the values being added as an array, may be null
     * @return this
     */
    JSONRenderer array(JSONListable values);
    
    /**
     * Adds all the pairs contained in a {@link Map} by calling
     * {@link JSONRenderer#key(Object)} on each key returned by
     * {@link Map.Entry#getKey()} and {@link JSONRenderer#value(Object)}
     * on each value returned by {@link Map.Entry#getValue()}.
     * 
     * <p>
     *   Will return this, without adding something, if pairs is null.
     * </p>
     * 
     * @param pairs the pairs being added, may be null
     * @return this
     */
    JSONRenderer pairs(Map<?, ?> pairs);
    
    /**
     * Adds a {@link JSONMapable} value by giving the control to
     * it's {@link JSONMapable#renderAsMap(JSONRenderer)} method.
     * 
     * <p>
     *   Will return this, without adding something, if pairs is null.
     * </p>
     * 
     * @param pairs the pairs being added, may be null
     * @return this
     */
    JSONRenderer pairs(JSONMapable pairs);
    
    /**
     * Adds a {@link NoObjectContext} value by giving the control to
     * it's {@link NoObjectContext#encodeJSON(JSONConstructor)} method.
     *
     * <p>
     *   <strong>Note:</strong> Using {@link NoObjectContext#encodeJSON(JSONConstructor)}
     *   with a {@link JSONRenderer} requires the use of an {@link Adapter}.
     *   Consider using {@link JSON#asJSONConstructor(JSONRenderer)}.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if pairs is null.
     * </p>
     * 
     * @param pairs the pairs being added, may be null
     * @return this
     * 
     * @deprecated Only for legacy support. Consider using 
     *             {@link JSONRenderer#pairs(JSONMapable)} instead.
     */
    @Deprecated
    JSONRenderer pairs(NoObjectContext pairs);
    
    /**
     * Starts an object structure, adds all pairs contained in a {@link Map}
     * by calling {@link JSONRenderer#key(Object)} on each key returned by
     * {@link Map.Entry#getKey()} and {@link JSONRenderer#value(Object)} on
     * each value returned by {@link Map.Entry#getValue()} and ends the object 
     * structure.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.object().pairs(pairs).endObject();
     *   </pre>
     * <p>
     * 
     * <p>
     *   Will produce an empty object if pairs is null.
     * </p>
     * 
     * @param pairs the pairs being added as an object, may be null
     * @return this
     */
    JSONRenderer object(Map<?, ?> pairs);
    
    /**
     * Starts an object structure, adds a {@link JSONMapable} value by giving 
     * control to it's {@link JSONMapable#renderAsMap(JSONRenderer)} method
     * and end the object structure.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.object().pairs(pairs).endObject();
     *   </pre>
     * <p>
     * 
     * <p>
     *   Will produce an empty object if pairs is null.
     * </p>
     * 
     * @param pairs the pairse being added as an object, may be null
     * @return this
     */
    JSONRenderer object(JSONMapable pairs);

    /**
     * Starts an object structure, adds a {@link NoObjectContext}
     * value by giving control to it's {@link NoObjectContext#encodeJSON(JSONConstructor)}
     * method and ends the object structure.
     * 
     * <p>
     *   <strong>Note:</strong> Using {@link NoObjectContext#encodeJSON(JSONConstructor)}
     *   with a {@link JSONRenderer} requires the use of an {@link Adapter}.
     *   Consider using {@link JSON#asJSONConstructor(JSONRenderer)}.
     * </p>
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.object().pairs(pairs).endObject();
     *   </pre>
     * <p>
     * 
     * <p>
     *   Will produce an empty object if pairs is null.
     * </p>
     * 
     * @param pairs the pairs being added as an object, may be null
     * @return this
     * 
     * @deprecated Only for legacy support. Consider using 
     *             {@link JSONRenderer#object(JSONMapable)} instead.
     */
    @Deprecated
    JSONRenderer object(NoObjectContext pairs);
    
    /**
     * Starts an object structure, adds a {@link JSONEncoder} value by 
     * giving control to it's {@link JSONEncoder#encodeJSON(JSONConstructor)}
     * method and ends the object structure.
     * 
     * <p>
     *   <strong>Note:</strong> Using {@link NoObjectContext#encodeJSON(JSONConstructor)}
     *   with a {@link JSONRenderer} requires the use of an {@link Adapter}.
     *   Consider using {@link JSON#asJSONConstructor(JSONRenderer)}.
     * </p>
     * 
     * <p>
     *   Will produce an empty object if pairs is null.
     * </p>
     * 
     * @param object the object being added
     * @return this
     * 
     * @deprecated Only for legacy support. Consider using 
     *             {@link JSONRenderer#object(JSONMapable)} instead.
     */
    @Deprecated
    JSONRenderer object(JSONEncoder object);
    
    /**
     * This method only exists for backwards compatability reasons.
     * 
     * @param json a well-formed JSON string
     * @throws NullPointerException if json is null
     * @return this
     * 
     * @deprecated Use strongly typed methods instead.
     */
    @Deprecated
    JSONRenderer plain(String json);
    
    /**
     * Renders this instance into a valid JSON {@link String}.
     * 
     * <p>
     *   <strong>This operations is optional.</strong>
     *   Different implementations are
     *   free to decide whether they append their result
     *   e.g. directly into a {@link Writer} or an {@link OutputStream}.
     * </p>
     * 
     * @return String the rendered JSON {@link String}
     */
    @Override
    String toString();
    
}
