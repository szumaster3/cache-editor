package com.alex.util.whirlpool;

/**
 * An implementation of the {@code djb2} hash function.
 *
 * @author Graham
 * @author `Discardedx2
 */
public final class Djb2 {

    /**
     * Default private constructor to prevent instantiation.
     */
    private Djb2() {

    }

    /**
     * An implementation of Dan Bernstein's {@code djb2} hash function
     * which is slightly modified. Instead of the initial hash being 5381, it
     * is zero.
     *
     * @param str The string to hash.
     * @return The hash code.
     */
    public static int djb2(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + ((hash << 5) - hash);
        }
        return hash;
    }

}
