package org.firstinspires.ftc.teamcode.dhs.utils;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class History<T> implements Cloneable {
    /** The ArrayList containing each point in history */
    private List<T> points;

    /** The ArrayList containing the difference in time (ms) between each point in history */
    private List<Long> timeline;

    /** The ElapsedTime that keeps track of the time between each point in history */
    private ElapsedTime timer;

    /** How many points in history the timeline keeps */
    private int depth;

    /**
     * Create an object of the History class
     * @param depth the "depth" of the kept history, how many points it will keep track of
     */
    public History(int depth) {
        this.depth = depth;

        this.points = new ArrayList<T>();
        this.timeline = new ArrayList<Long>();

        this.timer = new ElapsedTime();
        timer.reset();
    }

    /**
     * Add another item to the history
     * @param item the item to add
     */
    public void add(T item) {
        points.add(0, item);

        timeline.add(0, timer.time(TimeUnit.MILLISECONDS));
        timer.reset();

        trim();
    }

    /**
     * Reset the kept history back to being empty
     */
    public void clear() {
        points.clear();
        timeline.clear();
        timer.reset();
    }

    /**
     * Trim the history down to fit the depth
     */
    public void trim() {
        if (points.size() > depth)
            points.subList(depth, points.size()).clear();

        if (timeline.size() > depth - 1)
            timeline.subList(depth - 1, timeline.size()).clear();
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Get the depth of the history, which is how many points it tracks
     * @return the depth of the history
     */
    public int getDepth() { return depth; }

    /**
     * Get the points in history, stored from newest to oldest
     * @return the list
     */
    public List<T> getPointsList() { return points; }

    /**
     * Get the stored points in history as an array, sorted from newest to oldest
     * @return the array
     */
    @SuppressWarnings("unchecked") // In this case, the unchecked cast is fine because we don't
                                   // actually do anything with the array we created.
    public T[] getPointsArray() { return points.toArray( (T[]) new Object[0] ); }

    /**
     * Get the time between the points in history as a list,
     * where timeline[i] is the time difference (ms) between points[i+1] and points[i]
     * @return the list
     */
    public List<Long> getTimelineList() { return timeline; }

    /**
     * Get the time between the points in history as an array,
     * where timeline[i] is the time difference (ms) between points[i+1] and points[i]
     * @return the array
     */
    public Long[] getTimelineArray() { return timeline.toArray( new Long[0] ); }

    /**
     * Sets this history's depth
     * <p>NOTE: This will trim the history down if the history's points don't conform to the new depth
     * @param newDepth the new depth to replace the old one with
     */
    protected void setDepth(int newDepth) {
        this.depth = newDepth;
        trim();
    }

    /**
     * Sets this history's points
     * <p>NOTE: This will trim the history down and discard extra points if the passed in points list is too large
     * @param newPoints a list containing the new points
     */
    protected void setPointsList(List<T> newPoints) {
        this.points = newPoints;
        trim();
    }

    /**
     * Sets this history's points
     * <p>NOTE: This will trim the history down and discard extra points if the passed in points list is too large
     * @param newPoints an array containing the new points
     */
    protected void setPointsArray(T[] newPoints) {
        this.points = Arrays.asList(newPoints);
        trim();
    }

    /**
     * Sets this history's timeline
     * <p>NOTE: This will trim the history down and discard extra timeline points if the passed in timeline list is too large
     * @param newTimeline a list containing the new timeline
     */
    protected void setTimelineList(List<Long> newTimeline) {
        this.timeline = newTimeline;
        trim();
    }

    /**
     * Sets this history's timeline
     * <p>NOTE: This will trim the history down and discard extra timeline points if the passed in timeline list is too large
     * @param newTimeline an array containing the new timeline
     */
    protected void setTimelineArray(Long[] newTimeline) {
        this.timeline = Arrays.asList(newTimeline);
        trim();
    }
}
