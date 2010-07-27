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

import javax.annotation.concurrent.Immutable;

/**
 * An immutable renderer which is used to render
 * single values into a {@link JSONRenderer}.
 *
 * @deprecated use {@link de.cosmocode.rendering.ValueRenderer} instead
 * @author Willi Schoenborn
 * @param <T> the type this {@link ValueRenderer} is able to render
 */
@Deprecated
@Immutable
public interface ValueRenderer<T> {

    /**
     * Adds the given value to the given {@link JSONRenderer}.
     * 
     * @param value the value being added
     * @param renderer the target {@link JSONRenderer}
     */
    void render(T value, JSONRenderer renderer);
    
}
