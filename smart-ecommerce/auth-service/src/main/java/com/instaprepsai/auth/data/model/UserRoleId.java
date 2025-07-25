package com.instaprepsai.auth.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleId {

    private UUID user;
    private UUID role;

}
