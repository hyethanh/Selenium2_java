package com.auto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class User {
    private String username;
    private String password;
}
