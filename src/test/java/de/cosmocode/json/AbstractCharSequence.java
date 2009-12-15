package de.cosmocode.json;

/**
 * An abstract dummy implementation of
 * the {@link CharSequence} interface allowing
 * it to easily create a {@link CharSequence}
 * on the fly without the need to implement every method.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractCharSequence implements CharSequence {

    @Override
    public char charAt(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        throw new UnsupportedOperationException();
    }

}
