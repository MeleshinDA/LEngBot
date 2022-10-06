package com.example.lengbot.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Модель пользователя бота
 */
@Getter
@Setter
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