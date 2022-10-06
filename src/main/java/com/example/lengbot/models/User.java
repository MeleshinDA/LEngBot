package com.example.lengbot.models;


import lombok.Getter;

/**
 * Модель пользователя бота
 */
@Getter
public class User {

  /**
   * Идентификатор чата в телеграмме
   */
  private String id;
  /**
   * Уровень английского языка пользователя
   */
  private String lvl;

  public User() {

  }

  public User(String id, String lvl) {
    this.id = id;
    this.lvl = lvl;
  }

}