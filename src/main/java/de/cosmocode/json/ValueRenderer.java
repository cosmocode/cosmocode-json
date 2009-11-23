package de.cosmocode.json;

import de.cosmocode.patterns.Immutable;

/**
 * An immutable renderer which is used to render
 * single values into a {@link JSONRenderer}.
 *
 * @author Willi Schoenborn
 * @param <T> the type this {@link ValueRenderer} is able to render
 */
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
