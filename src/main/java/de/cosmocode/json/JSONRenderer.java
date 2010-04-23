/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.json;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import de.cosmocode.commons.DateMode;
import de.cosmocode.patterns.Adapter;

/**
 * A {@link JSONRenderer} is being used to create
 * a JSON formatted {@link String}.
 * 
 * <p>
 *   Take a look at <a href="http://www.json.org/">json.org</a>
 *   to get familiar with the grammar JSON is based on.
 * </p>
 *
 * <pre>
 * object
 *     {}
 *     { members } 
 * members
 *     pair
 *     pair , members
 * pair
 *     string : value
 * array
 *     []
 *     [ elements ]
 * elements
 *     value
 *     value , elements
 * value
 *     string
 *     number
 *     object
 *     array
 *     true
 *     false
 *     null
 * </pre> 
 *
 * @author Willi Schoenborn
 */
public interface JSONRenderer {
    
    RenderLevel DEFAULT_LEVEL = RenderLevel.COMPLETE;

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
     * Adds a generic value using the given {@link ValueRenderer}.
     * 
     * @param <T> the generic parameter type
     * @param value the value being added, may be null
     * @param renderer the {@link ValueRenderer} being used to render the value
     * @return this
     * @throws NullPointerException if renderer is null
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    <T> JSONRenderer value(T value, ValueRenderer<? super T> renderer);
    
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
     * Adds a long value.
     * 
     * @param value the value being added
     * @return this
     * @throws IllegalStateException if there is no value allowed
     *         at the current position
     */
    JSONRenderer value(long value);
    
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
     *   Will return {@link JSONRenderer#nullValue()} if value is null.
     * </p>
     * 
     * @param value the value being added, may be null
     * @param mode the mode being used for {@link Date} conversions
     * @return this
     * @throws NullPointerException if mode is null
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
     * 
     * <p>
     *   The final value will be a {@link String} returned by {@link CharSequence#toString()}.
     * </p>

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
     *   Adds all the values contained in an {@link Iterable} by calling
     *   {@link JSONRenderer#value(Object, ValueRenderer)} for each element.
     * </p>
     * 
     * <p>
     *   Will return this, without adding something, if values is null.
     * </p>
     * 
     * @param <T> the generic parameter type
     * @param values the values being added, may be null
     * @param renderer the {@link ValueRenderer} being used to render the values
     * @return this
     * @throws NullPointerException if renderer is null
     */
    <T> JSONRenderer values(Iterable<? extends T> values, ValueRenderer<? super T> renderer);
    
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
     * @param values the values being added as an array, may be null
     * @return this
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    JSONRenderer array(Object... values);
    
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
     * @param pairs the pairs being added, may be null
     * @return this
     * @throws IllegalStateException if there are no pairs allowed
     *         at the current position
     */
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
     * @param pairs the pairs being added as an object, may be null
     * @return this
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
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
     *   Will produce an empty object if pairs is null.
     * </p>
     * 
     * @param object the object being added, may be null
     * @return this
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    JSONRenderer object(JSONEncoder object);
    
    boolean lt(RenderLevel level);
    
    boolean le(RenderLevel level);
    
    boolean eq(RenderLevel level);
    
    boolean ge(RenderLevel level);
    
    boolean gt(RenderLevel level);
    
    RenderLevel currentLevel();
    
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
