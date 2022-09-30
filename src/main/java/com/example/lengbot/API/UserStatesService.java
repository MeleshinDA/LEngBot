package com.example.lengbot.API;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class UserStatesService {
    private Boolean isTesting = false;
    private Boolean isEnteringLvl = false;
}
