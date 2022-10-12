package com.example.lengbot.services.structures;

import java.util.HashMap;

/**
 * Структура данных - HashMap, у которого есть возможность для несуществующего ключа задать базовое
 * поведение.
 */
public class DefaultHashMap<K, V> extends HashMap<K, V> {

  private final V defaultValue;

  public DefaultHashMap(V defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * Метод, который возвращает установленное базовое значение, в нашем случае это метод default.
   *
   * @param key проверяемый ключ
   */
  public V getDefault(K key) {
    if (containsKey(key)) {
      return get(key);
    }
    return defaultValue;
  }
}
