package de.cosmocode.json;

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
