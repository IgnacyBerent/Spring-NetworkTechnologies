package edu.lb.spring_networktechnologies.infrastructure.dtos.user;

import org.openapitools.jackson.nullable.JsonNullable;

public class UpdateUserDto {
    private JsonNullable<String> fName;
    private JsonNullable<String> lName;
    private JsonNullable<String> email;


    public UpdateUserDto(JsonNullable<String> fName, JsonNullable<String> lName, JsonNullable<String> email) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    public UpdateUserDto() {
    }

    public JsonNullable<String> getfName() {
        return fName;
    }

    public void setfName(JsonNullable<String> fName) {
        this.fName = fName;
    }

    public JsonNullable<String> getlName() {
        return lName;
    }

    public void setlName(JsonNullable<String> lName) {
        this.lName = lName;
    }

    public JsonNullable<String> getEmail() {
        return email;
    }

    public void setEmail(JsonNullable<String> email) {
        this.email = email;
    }
}
