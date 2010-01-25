package de.cosmocode.json;

/**
 * A level which defines the granularity of a rendered
 * json structure.
 * 
 * <p>
 *   Every level usually contains the direct preceding
 *   level. E.g. {@link RenderLevel#MEDIUM} includes {@link RenderLevel#SHORT}
 *   which itself is a superset of {@link RenderLevel#TINY}.
 * </p>
 *
 * @author Willi Schoenborn
 */
public enum RenderLevel {

    /**
     * Mainly used for auto completions.
     * Should contain the identifier and the name.
     */
    TINY,
    
    /**
     * Information needed for small lists. Usually some
     * boolean/bitset properties used to indicate or group
     * elements.
     */
    SHORT,
  
    /**
     * Meta information like timestamps (createdAt, lastModified, ...)
     * and some basic address and contact data.
     */
    MEDIUM,
    
    /**
     * Contains all direct field data, and usually no or very little
     * referenced object information.
     */
    LONG,
    
    /**
     * Contains all data, including referenced sub object graphs
     * like children, many-to-many relationships and so on. 
     */
    COMPLETE;
    
}
