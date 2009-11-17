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
