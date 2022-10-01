package com.example.lengbot.API;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

/**
 * Состояния пользователя
 */
@Getter
@Setter
@Scope("prototype")
public class UserStatesService {
    private Boolean isTesting = false;
    private Boolean isEnteringLvl = false;
}
