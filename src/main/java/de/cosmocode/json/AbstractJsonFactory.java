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
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * <p> An abstract implementation of {@link JsonFactory}. 
 * It provides default redirects for every method except
 * {@link #create(Writer, RenderLevel)} and {@link #create(RenderLevel)}.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public abstract class AbstractJsonFactory implements JsonFactory {
    
    @Override
    public final JSONRenderer create(OutputStream stream) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, DEFAULT_ENCODING);
        return create(writer);
    }
    
    @Override
    public JSONRenderer create(OutputStream stream, String encoding) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, encoding);
        return create(writer);
    }
    
    @Override
    public JSONRenderer create(OutputStream stream, Charset encoding) throws IOException {
        final Writer writer = new OutputStreamWriter(stream, encoding);
        return create(writer);
    }
    
}
