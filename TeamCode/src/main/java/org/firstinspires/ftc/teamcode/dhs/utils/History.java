package org.firstinspires.ftc.teamcode.dhs.utils;

import java.util.ArrayList;
import java.util.List;

public class History<T> {
    /** The ArrayList containing each point in history */
    private final List<T> points;

    /** How many points in history the timeline keeps */
    private final int depth;

    /**
     * Create an object of the History class
     * @param depth the "depth" of the kept history, how many points it will keep track of
     */
    public History(int depth) {
        this.depth = depth;
        this.points = new ArrayList<T>();
    }

    /**
     * Get the depth of the history, which is how long it tracks
     * @return the depth of the history
     */
    public int getDepth() { return depth; }

    /**
     * Add another item to the history
     * @param item the item to add
     */
    public void add(T item) {
        points.add(item);

        // Make sure list isn't too large
        if (points.size() > depth)
            points.subList(depth, points.size()).clear();
    }

    /**
     * Reset the kept history back to being empty
     */
    public void clear() {
        points.clear();
    }

    /**
     * Get the points in history, stored from newest to oldest
     * @return the list
     */
    public List<T> toList() { return points; }

    /**
     * Get the stored points in history as an array, sorted from newest to oldest
     * @return the array
     */
    @SuppressWarnings("unchecked") // In this case, the unchecked cast is fine because we don't
                                   // actually do anything with the array we created.
    public T[] toArray() { return points.toArray( (T[]) new Object[0] ); }
}
