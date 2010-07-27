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

package org.json.extension;

import de.cosmocode.json.JSONListable;
import de.cosmocode.json.JSONMapable;

/**
 * Marker interface for {@link JSONEncoder} implementations that dont have opening
 * and closing {@link JSONConstructor#object()} and {@link JSONConstructor#endObject()}
 * calls in their {@link JSONEncoder#encodeJSON(JSONConstructor)} method.
 * 
 * @deprecated use {@link JSONMapable} or {@link JSONListable} instead
 * 
 * @author schoenborn@cosmocode.de
 */
@Deprecated
public interface NoObjectContext extends JSONEncoder {

}
