package com.example.keycloak_project.service;


import com.example.keycloak_project.exception.CustomNotfoundException;
import com.example.keycloak_project.model.dto.UserDto;
import com.example.keycloak_project.model.request.ResetPasswordRequest;
import com.example.keycloak_project.model.request.UserRegisterRequest;
import com.example.keycloak_project.model.request.UserUpdateRequest;
import com.example.keycloak_project.security.Credentials;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.security.Principal;

public interface KeycloakService {
    UserDto addUser(UserRegisterRequest request);
    UserDto getUserByName(String username);
    UserDto getCurrentUserData(String name);
    UserDto updateUser(String userId, UserUpdateRequest userRequest);
    void deleteUser(String userId);
    void resetPassword(String userId, ResetPasswordRequest request);
    UserDto getUserById(String userId);
}
