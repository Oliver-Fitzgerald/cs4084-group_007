package com.college.cs4048_group_007;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class VisitedTracker {
    private static final String PREF_NAME = "visited_prefs";
    private static final String VISITED_KEY = "visited_ids";
    private static final String RESET_KEY = "last_reset";

    public static void markVisited(Context context, String poiId) {
        ensureDailyReset(context);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> visited = new HashSet<>(prefs.getStringSet(VISITED_KEY, new HashSet<>()));
        visited.add(poiId);
        prefs.edit().putStringSet(VISITED_KEY, visited).apply();
    }

    public static boolean isVisited(Context context, String poiId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> visited = prefs.getStringSet(VISITED_KEY, new HashSet<>());
        return visited.contains(poiId);
    }

    public static int getVisitedCount(Context context) {
        // resets if new day
        ensureDailyReset(context);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(VISITED_KEY, new HashSet<>()).size();
    }

    public static void reset(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit().remove(VISITED_KEY).apply();
    }

    private static boolean isSameDay(long time1, long time2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(time1);

        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(time2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    private static void ensureDailyReset(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long lastReset = prefs.getLong(RESET_KEY, 0);
        long now = System.currentTimeMillis();

        if (!isSameDay(now, lastReset)) {
            prefs.edit()
                    .remove(VISITED_KEY)
                    .putLong(RESET_KEY, now)
                    .apply();
        }
    }
}
