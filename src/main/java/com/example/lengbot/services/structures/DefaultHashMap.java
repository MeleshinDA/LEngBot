package com.example.lengbot.services.structures;

import java.util.HashMap;

public class DefaultHashMap<K, V> extends HashMap<K, V> {

  private final V defaultValue;

  public DefaultHashMap(V defaultValue) {
    this.defaultValue = defaultValue;
  }

  public V getDefault(K key) {
    if (containsKey(key)) {
      return get(key);
    }
    return defaultValue;
  }
}
