package com.example.onur.wiredapp;
import java.util.*;
public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> crunchifySortMap(final Map<K, V> mapToSort) {
        List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(mapToSort.size());

        entries.addAll(mapToSort.entrySet());

        // Sorts the specified list according to the order induced by the specified comparator
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(final Map.Entry<K, V> entry1, final Map.Entry<K, V> entry2) {
                // Compares this object with the specified object for order
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        Map<K, V> sortedCrunchifyMap = new LinkedHashMap<K, V>();

        // The Map.entrySet method returns a collection-view of the map
        for (Map.Entry<K, V> entry : entries) {
            sortedCrunchifyMap.put(entry.getKey(), entry.getValue());
        }

        return sortedCrunchifyMap;
    }

}
