package de.cosmocode.json;

/**
 * A {@link JSONListable} implementor
 * allows to render an object of any kind as
 * a json array.
 *
 * @author Willi Schoenborn
 */
public interface JSONListable {

    /**
     * Renders this as a json array using
     * the given {@link JSONRenderer}.
     * 
     * <p>
     *   <strong>Note:</strong> It's not allowed to wrap
     *   your structure in {@link JSONRenderer#array()} and
     *   {@link JSONRenderer#endArray()} calls.
     * </p>
     * 
     * @param renderer the {@link JSONRenderer} to render this to
     * @return the given renderer
     */
    JSONRenderer renderAsList(JSONRenderer renderer);

}
