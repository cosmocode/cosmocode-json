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
 * a JSON formatted {@link String}.
 * 
 * <p>
 *   Take a look at <a href="http://www.json.org/">json.org</a>
 *   to get familiar with the grammar JSON is based on.
 * </p>
 *
 * @author Willi Schoenborn <schoenborn@cosmocode.de>
 */
public interface JSONRenderer {

    /**
     * Starts an array structure.
     * 
     * @return this
     * @throws IllegalStateException if array structure is not
     *         allowed at the current position
     */
    JSONRenderer array();
    
    /**
     * Ends an array structure.
     * 
     * @return this
     * @throws IllegalStateException if there is no array structure
     *         to close at the current position
     */
    JSONRenderer endArray();

    /**
     * Starts an object structure.
     * 
     * @return this
     * @throws IllegalStateException if object structure is not
     *         allowed at the current position
     */
    JSONRenderer object();

    /**
     * Ends an object structure.
     * 
     * @return this
     * @throws IllegalStateException if there is no object structure
     *         to close at the current position
     */
    JSONRenderer endObject();

    /**
     * Adds a key.
     * 
     * <p>
     *   If key is null, the {@link String}
     *   {@code "null"} will be used instead.
     * </p>
     * 
     * <p>
     *   The final key will be produced by calling
     *   {@link CharSequence#toString()}.
     * </p>
     * 
     * @param key the key, may be null
     * @return this
     * @throws IllegalStateException if there is no key allowed 
     *         at the current position
     */
    JSONRenderer key(CharSequence key);

    /**
     * Adds a key.
     * 
     * <p>
     *   If key is null, the {@link String}
     *   {@code "null"} will be used instead.
     * </p>
     * 
     * <p>
     *   The final key will be produced by calling
     *   {@link Object#toString()}.
     * </p>
     * 
     * @param key the key, may be null
     * @return this
     * @throws IllegalStateException if there is no key allowed 
     *         at the current position
     */
    JSONRenderer key(Object key);
    
    /**
     * Adds a null value.
     * 
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer nullValue();
    
    /**
     * Adds a generic value.
     * 
     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if value is null.
     * </p>
     * 
     * @param value the value being added, may be null
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(Object value);
    
    /**
     * Adds a boolean value.
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(boolean value);
    
    /**
     * Adds a byte value.
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(byte value);
    
    /**
     * Adds a short value.
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(short value);
    
    /**
     * Adds a char value.
     * 
     * <p>
     *   The char will be transformed into a String by using
     *   {@link Character#toString(char)}.
     * </p>
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(char value);
    
    /**
     * Adds an int value.
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(int value);
    
    /**
     * Adds a long value.
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(long value);
    
    /**
     * Adds a float value.
     * 
     * <p>
     *   Infinity and NaN are not allowed.
     * </p>
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalArgumentException if value is infinite or NaN
     * @throws IllegalStateException if there is no value allowed
     *         at the current position 
     */
    JSONRenderer value(float value);
    
    /**
     * Adds a double value.
     * 
     * <p>
     *   Infinity and NaN are not allowed.
     * </p>
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalArgumentException if value is infinite or NaN 
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(double value);
    
    /**
     * Adds a {@link BigInteger} value.
     * 
     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if value is null.
     * </p>
     * 
     * @param value the value being added, may be null
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(BigInteger value);
    
    /**
     * Adds a {@link BigDecimal} value.
     * 
     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if value is null.
     * </p>
     * 
     * @param value the value being added, may be null
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(BigDecimal value);
    
    /**
     * Adds a {@link Date} value using {@link DateMode#JAVA} for
     * {@link Date} conversion.
     * 
     * <p>
     *   Similiar to:
     *   <pre>
     *     renderer.value(date, DateMode.JAVA);
     *   </pre>
     * </p>
     * 
     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if value is null.
     * </p>
     * 
     * @param value the value being added, may be null
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(Date value);
    
    /**
     * Adds a {@link Date} value using the given mode for
     * {@link Date} conversions.
     * 
     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if either value or mode is null.
     * </p>
     * 
     * @param value the value being added, may be null
     * @param mode the mode being used for {@link Date} conversions, may be null
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(Date value, DateMode mode);
    
    /**
     * Adds an enum value.
     * 
     * <p>
     *   The final value will be a {@link String} return by {@link Enum#name()} method.
     * </p>
     * 
     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if value is null.
     * </p>
     * 
     * @param value the value being added, may be null
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
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
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(CharSequence value);
    
    /**
     * Adds generic values.
     * 
     * <p>
     *   Adds all the values contained in an array by calling
     *   {@link JSONRenderer#value(Object)} for each element.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param <E> the array's element type
     * @param values the values being added, may be null
     * @return this
     * @throws IllegalStateException if there are no values allowed
     *         at the current position
     */
    JSONRenderer values(Object... values);
    
    /**
     * Adds generic values.
     * 
     * <p>
     *   Adds all the values contained in an {@link Iterable} by calling
     *   {@link JSONRenderer#value(Object)} for each element.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param values the values being added, may be null
     * @return this
     * @throws IllegalStateException if there are no values allowed
     *         at the current position
     */
    JSONRenderer values(Iterable<?> values);
    
    /**
     * Adds generic values.
     * 
     * <p>
     *   Adds all the values returned by an {@link Iterator} by calling
     *   {@link JSONRenderer#value(Object)} for each element.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param values the values being added, may be null
     * @return this
     * @throws IllegalStateException if there are no values allowed
     *         at the current position
     */
    JSONRenderer values(Iterator<?> values);

    /**
     * Adds generic values.
     * 
     * <p>
     *   Adds a {@link JSONListable} value by giving the control
     *   to it's {@link JSONListable#renderAsList(JSONRenderer)} method.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param values the values being added, may be null
     * @return this
     * @throws IllegalStateException if there are no values allowed
     *         at the current position
     */
    JSONRenderer values(JSONListable values);
    
    /**
     * Produces an aray containing generic values.
     * 
     * <p>
     *   Starts an array structure, adds all the values contained in an
     *   array by calling {@link JSONRenderer#value(Object)} for each
     *   element and ends the array structure.
     * </p>
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
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    <E> JSONRenderer array(E... values);
    
    /**
     * Produces an array containing generic values.
     * 
     * <p>
     *   Starts an array structure, adds all the values contained in an
     *   {@link Iterable} by calling {@link JSONRenderer#value(Object)} for
     *   each element and ends the array structure.
     * </p>
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
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    JSONRenderer array(Iterable<?> values);
    
    /**
     * Produces an array containing generic values.
     * 
     * <p>
     *   Starts an array structure, adds all the values returned by an
     *   {@link Iterator} by calling {@link JSONRenderer#value(Object)} for
     *   each element and ends the array structure.
     * </p>
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
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    JSONRenderer array(Iterator<?> values);
    
    /**
     * Produces an array containg generic values.
     * 
     * <p>
     *   Starst an array structure, adds a {@link JSONListable} value by
     *   giving the control to it's {@link JSONListable#renderAsList(JSONRenderer)}
     *   method and ends the array structure.
     * </p>
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
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    JSONRenderer array(JSONListable values);
    
    /**
     * Adds generic pairs.
     * 
     * <p>
     *   Adds all the pairs contained in a {@link Map} by calling
     *   {@link JSONRenderer#key(Object)} on each key returned by
     *   {@link Map.Entry#getKey()} and {@link JSONRenderer#value(Object)}
     *   on each value returned by {@link Map.Entry#getValue()}.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if pairs is null.
     * </p>
     * 
     * @param pairs the pairs being added, may be null
     * @return this
     * @throws IllegalStateException if there are no pairs allowed
     *         at the current position
     */
    JSONRenderer pairs(Map<?, ?> pairs);
    
    /**
     * Adds generic pairs.
     * 
     * <p>
     *   Adds a {@link JSONMapable} value by giving the control to
     *   it's {@link JSONMapable#renderAsMap(JSONRenderer)} method.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if pairs is null.
     * </p>
     * 
     * @param pairs the pairs being added, may be null
     * @return this
     * @throws IllegalStateException if there are no pairs allowed
     *         at the current position
     */
    JSONRenderer pairs(JSONMapable pairs);
    
    /**
     * Adds a generic pairs.
     * 
     * <p>
     *   Adds a {@link NoObjectContext} value by giving the control to
     *   it's {@link NoObjectContext#encodeJSON(JSONConstructor)} method.
     * </p>
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
     * @deprecated Only for legacy support. Consider using 
     *             {@link JSONRenderer#pairs(JSONMapable)} instead.
     * 
     * @param pairs the pairs being added, may be null
     * @return this
     * @throws IllegalStateException if there are no pairs allowed
     *         at the current position
     */
    @Deprecated
    JSONRenderer pairs(NoObjectContext pairs);
    
    /**
     * Produces an object containing generic pairs.
     * 
     * <p>
     *   Starts an object structure, adds all pairs contained in a {@link Map}
     *   by calling {@link JSONRenderer#key(Object)} on each key returned by
     *   {@link Map.Entry#getKey()} and {@link JSONRenderer#value(Object)} on
     *   each value returned by {@link Map.Entry#getValue()} and ends the object 
     *   structure.
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
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    JSONRenderer object(Map<?, ?> pairs);
    
    /**
     * Produces an object containging generic pairs.
     * 
     * <p>
     *   Starts an object structure, adds a {@link JSONMapable} value by giving 
     *   control to it's {@link JSONMapable#renderAsMap(JSONRenderer)} method
     *   and end the object structure.
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
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    JSONRenderer object(JSONMapable pairs);

    /**
     * Produces an object containing generic pairs.
     * 
     * <p>
     *   Starts an object structure, adds a {@link NoObjectContext}
     *   value by giving control to it's {@link NoObjectContext#encodeJSON(JSONConstructor)}
     *   method and ends the object structure.
     * </p>
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
     * @deprecated Only for legacy support. Consider using 
     *             {@link JSONRenderer#object(JSONMapable)} instead.
     * 
     * @param pairs the pairs being added as an object, may be null
     * @return this
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    @Deprecated
    JSONRenderer object(NoObjectContext pairs);
    
    /**
     * Produces an object.
     * 
     * <p>
     *   Starts an object structure, adds a {@link JSONEncoder} value by 
     *   giving control to it's {@link JSONEncoder#encodeJSON(JSONConstructor)}
     *   method and ends the object structure.
     * </p>
     * 
     * <p>
     *   <strong>Note:</strong> Using {@link NoObjectContext#encodeJSON(JSONConstructor)}
     *   with a {@link JSONRenderer} requires the use of an {@link Adapter}.
     *   Consider using {@link JSON#asJSONConstructor(JSONRenderer)}.
     * </p>
     * 
     * <p>
     *   Will return {@link JSONRenderer#nullValue()} if object is null.
     * </p>
     * 
     * @deprecated Only for legacy support. Consider using 
     *             {@link JSONRenderer#object(JSONMapable)} instead.
     * 
     * @param object the object being added, may be null
     * @return this
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    @Deprecated
    JSONRenderer object(JSONEncoder object);
    
    /**
     * Allows to directly add a String to the underlying data structure.
     * 
     * @deprecated This method only exists for backwards compatability reasons.
     *             It lacks proper validity checks. Use strongly typed methods instead.
     *             
     * @param json a well-formed JSON string
     * @return this
     * @throws NullPointerException if json is null
     */
    @Deprecated
    JSONRenderer plain(String json);
    
    /**
     * Renders this instance into a valid JSON {@link String}. (optional operation)
     * 
     * <p>
     *   <strong>This operations is optional.</strong>
     *   Implementations are free to decide whether they append their result
     *   e.g. directly into a {@link Writer} or an {@link OutputStream}.
     * </p>
     * 
     * @return String the rendered JSON {@link String}
     * @throws UnsupportedOperationException if the operation is not supported
     *         by this {@link JSONRenderer}
     * @throws IllegalStateException if the underlying structure is not in a renderable
     *         state, e.g. unfinished
     */
    @Override
    String toString();
    
}
