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

/**
 * A {@link JSONMapable} implementor
 * allows to render an object of any kind as
 * a json object.
 *
 * @author Willi Schoenborn
 */
public interface JSONMapable {

    /**
     * Renders this as a json object using
     * the given {@link JSONRenderer}.
     * 
     * <p>
     *   <strong>Note:</strong> It's not allowed to wrap
     *   your structure in {@link JSONRenderer#object()} and
     *   {@link JSONRenderer#endObject()} calls.
     * </p>
     * 
     * @param renderer the {@link JSONRenderer} to render this to
     * @return the given renderer
     */
    JSONRenderer renderAsMap(JSONRenderer renderer);
    
}
