package com.example.UserService.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDto
{

    @JsonProperty("user_id")
    @NonNull
    private String userId;

    @JsonProperty("first_name")
    @NonNull
    private String firstName;

    @JsonProperty("last_name")
    @NonNull
    private String lastName;

    @JsonProperty("phonenumber")
    @NonNull
    private Long phonenumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("profile_pic")
    private String profilePic;

    public UserInfo transformToUserInfo() {

        return UserInfo.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userId(userId)
                .email(email)
                .profilePic(profilePic)
                .phonenumber(phonenumber).build();
    }

}