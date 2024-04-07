package edu.lb.spring_networktechnologies.infrastructure.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

public class UpdateUserDto {
    @Schema(description = "First name of the user", example = "John", nullable = true)
    private JsonNullable<String> fName;
    @Schema(description = "Last name of the user", example = "Doe", nullable = true)
    private JsonNullable<String> lName;
    @Schema(description = "Email of the user", example = "example@ex.com", nullable = true)
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
