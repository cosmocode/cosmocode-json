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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

import de.cosmocode.patterns.Factory;

/**
 * A {@link Factory} that creates {@link JSONRenderer}.
 * The implementation may differ between different methods,
 * depending on the underlying framework.
 * 
 * @author Oliver Lorenz
 */
public interface JsonFactory extends Factory<JSONRenderer> {
    
    /**
     * The default encoding for OutputStreams, UTF-8.
     */
    Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

    /**
     * <p> Creates a {@link JSONRenderer}.
     * The returned JsonRenderer must support {@link JSONRenderer#toString() toString()}.
     * </p>
     * <p> <strong>Important: </strong>
     * A call on {@linkplain JSONRenderer#toString() toString()} may close an underlying stream,
     * resulting in the behavior that
     * only toString() can be called after toString() is called -
     * all other methods then throw IllegalStateExceptions.
     * </p>
     * @return a {@link JSONRenderer} that supports toString()
     */
    @Override
    JSONRenderer create();
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link Writer}.
     * All data is directly written to the given Writer 
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param writer a {@link Writer}; the rendered json is written to this Writer
     * @return a new JsonRenderer that writes its output to the given Writer.
     */
    JSONRenderer create(Writer writer);
    
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the {@link #DEFAULT_ENCODING},
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream) throws IOException;
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the given character encoding,
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @param encoding the character encoding to use when writing to the stream
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream, String encoding) throws IOException;
    
    /**
     * <p> Creates a {@link JSONRenderer} that writes the rendered Json into the given {@link OutputStream}.
     * All data is directly written to the given OutputStream, using the given character encoding,
     * and gets automatically flushed after the last endArray() or endObject() call
     * (when the render process is finished).
     * </p>
     * <p> <strong> Attention: </strong> The JsonRenderer that this method returns
     * may not support {@link JSONRenderer#toString() toString()}.
     * </p>
     * @param stream an {@link OutputStream}; the rendered json is written to this OutputStream
     * @param encoding the character encoding to use when writing to the stream
     * @return a new JsonRenderer that writes its output to the given Writer.
     * @throws IOException if the creation fails due to low-level IO exceptions
     */
    JSONRenderer create(OutputStream stream, Charset encoding) throws IOException;
    
}
