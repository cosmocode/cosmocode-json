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

import java.io.Writer;

/**
 * <p> A {@link JsonFactory} that creates JsonRenderer implementations
 * that are backed by the org.json package.
 * Namely: JsonStringer and JsonWriter.
 * </p>
 * 
 * @author Oliver Lorenz
 */
public class OrgJsonFactory extends AbstractJsonFactory implements JsonFactory {
    
    @Override
    public JSONRenderer create() {
        return JSON.createJSONRenderer();
    }

    @Override
    public JSONRenderer create(Writer writer) {
        return JSON.createJSONRenderer(writer);
    }

}
