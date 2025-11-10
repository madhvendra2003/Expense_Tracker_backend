package com.example.UserService.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class UserInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
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

}
