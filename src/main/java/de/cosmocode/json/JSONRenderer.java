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
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.json.extension.JSONConstructor;
import org.json.extension.JSONEncoder;
import org.json.extension.NoObjectContext;

import de.cosmocode.patterns.Adapter;
import de.cosmocode.rendering.Renderable;
import de.cosmocode.rendering.Renderer;
import de.cosmocode.rendering.RenderingException;
import de.cosmocode.rendering.RenderingLevel;

/**
 * A {@link JSONRenderer} is being used to create
 * a JSON formatted {@link String}.
 * 
 * @author Willi Schoenborn
 */
public interface JSONRenderer extends Renderer {

    @Override
    JSONRenderer key(CharSequence key);

    @Override
    JSONRenderer key(Object key);
    
    @Override
    JSONRenderer nullValue();

    @Override
    JSONRenderer value(Object value);

    @Override
    JSONRenderer value(boolean value);
    
    @Override
    JSONRenderer value(long value);
    
    @Override
    JSONRenderer value(double value);

    @Override
    JSONRenderer value(CharSequence value);

    @Override
    JSONRenderer values(Object... values);
    
    @Override
    JSONRenderer values(Iterable<?> values);
    
    @Override
    JSONRenderer values(Iterator<?> values);
    
    @Override
    JSONRenderer value(@Nonnull Object... values) throws RenderingException;

    @Override
    JSONRenderer value(@Nonnull Iterable<?> values) throws RenderingException;

    @Override
    JSONRenderer value(@Nonnull Iterator<?> values) throws RenderingException;

    @Override
    JSONRenderer value(@Nullable  Map<?, ?> pairs) throws RenderingException;
    
    @Override
    JSONRenderer pairs(Map<?, ?> pairs);
    
    @Override
    String build() throws RenderingException;
    
    /**
     * Starts an array structure.
     * 
     * @deprecated use {@link #list()}
     * @return this
     * @throws IllegalStateException if array structure is not
     *         allowed at the current position
     */
    @Deprecated
    JSONRenderer array();
    
    /**
     * Ends an array structure.
     * 
     * @deprecated use {@link #endList()}
     * @return this
     * @throws IllegalStateException if there is no array structure
     *         to close at the current position
     */
    @Deprecated
    JSONRenderer endArray();

    /**
     * Starts an object structure.
     * 
     * @deprecated use {@link #map()}
     * @return this
     * @throws IllegalStateException if object structure is not
     *         allowed at the current position
     */
    @Deprecated
    JSONRenderer object();

    /**
     * Ends an object structure.
     * 
     * @deprecated use {@link #endMap()}
     * @return this
     * @throws IllegalStateException if there is no object structure
     *         to close at the current position
     */
    @Deprecated
    JSONRenderer endObject();

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
     * @deprecated use {@link #value(Object...)}
     * @param values the values being added as an array, may be null
     * @return this
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    @Deprecated
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
     * @deprecated use {@link #value(Iterable)}
     * @param values the values being added as an array, may be null
     * @return this
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    @Deprecated
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
     * @deprecated use {@link #value(Iterator)}
     * @param values the values being added as an array, may be null
     * @return this
     * @throws IllegalStateException if there is no array allowed
     *         at the current position
     */
    @Deprecated
    JSONRenderer array(Iterator<?> values);
    
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
     * @deprecated use {@link #pairs(Renderable, RenderingLevel)}
     * @param pairs the pairs being added, may be null
     * @return this
     * @throws IllegalStateException if there are no pairs allowed
     *         at the current position
     */
    @Deprecated
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
     * @deprecated use {@link #pairs(Renderable, RenderingLevel))}
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
     * @deprecated use {@link #value(Map)}
     * @param pairs the pairs being added as an object, may be null
     * @return this
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    @Deprecated
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
     * @deprecated use {@link #value(Renderable, RenderingLevel)}
     * @param pairs the pairs being added as an object, may be null
     * @return this
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    @Deprecated
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
     * @deprecated use {@link #value(Renderable, RenderingLevel)}
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
     *   Will produce an empty object if pairs is null.
     * </p>
     * 
     * @deprecated use {@link #value(Renderable, RenderingLevel)}
     * @param object the object being added, may be null
     * @return this
     * @throws IllegalStateException if there is no object allowed
     *         at the current position
     */
    @Deprecated
    JSONRenderer object(JSONEncoder object);

    /**
     * Renders this instance into a valid JSON {@link String}. (optional operation)
     * 
     * <p>
     *   <strong>This operations is optional.</strong>
     *   Implementations are free to decide whether they append their result
     *   e.g. directly into a {@link Writer} or an {@link OutputStream}.
     * </p>
     * 
     * @deprecated use {@link #build()}
     * @return String the rendered JSON {@link String}
     * @throws UnsupportedOperationException if the operation is not supported
     *         by this {@link JSONRenderer}
     * @throws IllegalStateException if the underlying structure is not in a renderable
     *         state, e.g. unfinished
     */
    @Deprecated
    @Override
    String toString();
    
}
