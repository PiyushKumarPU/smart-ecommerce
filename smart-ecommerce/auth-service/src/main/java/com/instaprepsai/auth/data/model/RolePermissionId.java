package com.instaprepsai.auth.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class RolePermissionId {

    private UUID role;
    private UUID permission;

    public RolePermissionId() {
    }

}
