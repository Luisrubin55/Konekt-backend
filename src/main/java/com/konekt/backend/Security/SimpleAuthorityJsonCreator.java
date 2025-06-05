package com.konekt.backend.Security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleAuthorityJsonCreator {

    @JsonCreator
    public SimpleAuthorityJsonCreator(@JsonProperty("authority") String role){}

}
