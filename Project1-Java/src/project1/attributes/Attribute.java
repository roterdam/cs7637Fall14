package project1.attributes;

/**
 * This interface defines a class as being able to determine how closely
 * it matches another item of this type. The match(T t) will return
 * a percentage value with 100 being a complete match and 0 being a
 * totally incomplete match.
 */

public interface Attribute<T> {
    	
	/**
     * Match to o.
     */
    public int matchTo(T o);
   
}
