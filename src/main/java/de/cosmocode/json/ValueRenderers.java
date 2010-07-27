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

import com.google.common.base.Preconditions;

import de.cosmocode.rendering.Renderer;
import de.cosmocode.rendering.RenderingException;

/**
 * Utility class for {@link ValueRenderer}s.
 *
 * @since 1.1
 * @author Willi Schoenborn
 */
final class ValueRenderers {

    private ValueRenderers() {
        
    }

    /**
     * Adapts the given {@link ValueRenderer} to the {@link de.cosmocode.rendering.ValueRenderer} interface
     * assuming the passed in {@link Renderer} is an instanceof {@link JSONRenderer}.
     * 
     * @since 1.1
     * @param <T> generic parameter value
     * @param valueRenderer the backing value renderer
     * @return a {@link ValueRenderer} backed by a {@link ValueRenderer}
     */
    public static <T> de.cosmocode.rendering.ValueRenderer<T> adapt(final ValueRenderer<? super T> valueRenderer) {
        Preconditions.checkNotNull(valueRenderer, "Renderer");
        return new de.cosmocode.rendering.ValueRenderer<T>() {
            
            @Override
            public void render(T value, de.cosmocode.rendering.Renderer renderer) throws RenderingException {
                Preconditions.checkNotNull(renderer, "Renderer");
                Preconditions.checkArgument(renderer instanceof JSONRenderer, "%s must be a JSONRenderer", renderer);
                JSONRenderer.class.cast(renderer).value(value, valueRenderer);
            };
            
        };
    }
    
}
