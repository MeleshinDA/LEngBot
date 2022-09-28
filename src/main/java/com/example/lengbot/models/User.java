package com.example.lengbot.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String id;
    private String lvl;

    public User()
    {

    }
    public User(String id, String lvl)
    {
        this.id = id;
        this.lvl = lvl;
    }

}