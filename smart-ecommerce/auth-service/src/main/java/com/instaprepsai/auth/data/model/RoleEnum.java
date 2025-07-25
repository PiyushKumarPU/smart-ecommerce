package com.instaprepsai.auth.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {

    ROLE_STUDENT("ROLE_STUDENT", "Role_Student", "Represents a learner or participant in the system"),
    ROLE_TEACHER("ROLE_TEACHER", "Role_Teacher", "Represents an educator or instructor with access to teaching tools and resources."),
    ROLE_PARTNER("ROLE_PARTNER", "Role_Partner", "Represents a business partner or collaborator with specific access to partner-related features."),
    ROLE_ADMIN("ROLE_ADMIN", "Role_Admin", "Represents a super administrator with unrestricted access to all system features and settings."),
    ROLE_SALES("ROLE_SALES", "Role_Sales", "Represents an administrator with access to sales-related features, such as managing leads, tracking sales, and generating reports."),
    ROLE_OPERATIONS("ROLE_OPERATIONS", "ROLE_OPERATIONS", "Represents an administrator with access to operational features, such as managing workflows, overseeing processes, and handling day-to-day operations.");

    private final String name;
    private final String displayName;
    private final String description;
}
