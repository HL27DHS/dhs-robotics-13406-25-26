package org.firstinspires.ftc.teamcode.dhs.utils;

import java.util.ArrayList;
import java.util.List;

public class History<T> {
    // TODO: Javadoc
    private final List<T> timeline;
    private final int depth;

    public History(int depth) {
        this.depth = depth;
        this.timeline = new ArrayList<T>();
    }

    public int getDepth() { return depth; }

    public void add(T item) {
        timeline.add(item);

        // Make sure list isn't too large
        if (timeline.size() > depth)
            timeline.subList(depth, timeline.size()).clear();
    }

    /**
     * Reset the kept history back to being empty
     */
    public void clear() {
        timeline.clear();
    }

    public T[] toArray() {
        // TODO: Implement
    }
}
