package com.example.keycloak_project.service.serviceImpl;

import com.example.keycloak_project.config.GetCurrentUser;
import com.example.keycloak_project.exception.CustomNotfoundException;
import com.example.keycloak_project.model.dto.UserDto;
import com.example.keycloak_project.model.request.ResetPasswordRequest;
import com.example.keycloak_project.model.request.UserRegisterRequest;
import com.example.keycloak_project.model.request.UserUpdateRequest;
import com.example.keycloak_project.security.Credentials;
import com.example.keycloak_project.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;
    private final ModelMapper modelMapper;
    private final GetCurrentUser getCurrentUser;
    @Value("${keycloak.realm}")
    private String realm;

    private UserRepresentation credential(UserRegisterRequest request){
        CredentialRepresentation credential = Credentials.createPasswordCredentials(request.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCredentials(Collections.singletonList(credential));
        return user;
    }
    private UserRepresentation updateCredential(UserUpdateRequest request){

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }
    @Override
    public UserDto addUser(UserRegisterRequest request){
        UserRepresentation user = credential(request);
        user.setEnabled(true);
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.create(user);
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    public UserDto getUserByName(String username){
        UsersResource usersResource = keycloak.realm(realm).users();
        UserRepresentation userRepresentation = usersResource.search(username,true).get(0);
        return modelMapper.map(userRepresentation, UserDto.class);
    }
    @Override
    public UserDto getCurrentUserData(String name) {
        UsersResource usersResource = keycloak.realm(realm).users();
        UserRepresentation userRepresentation = usersResource.get(name).toRepresentation();
        return modelMapper.map(userRepresentation, UserDto.class);
    }
    @Override
    public UserDto updateUser(String userId, UserUpdateRequest userRequest) {
        UserRepresentation user = updateCredential(userRequest);
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).update(user);
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).remove();
    }
    @Override
    public void resetPassword(String userId, ResetPasswordRequest request) {
        if(!userId.equals(getCurrentUser.currentUserId())){
            throw new CustomNotfoundException("you don't have permission");
        }
        CredentialRepresentation credentialRepresentation = Credentials.createPasswordCredentials(request.getPassword());
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).resetPassword(credentialRepresentation);
    }
    @Override
    public UserDto getUserById(String userId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();
        if (userRepresentation == null) {
            throw new CustomNotfoundException("User not found with ID: " + userId);
        }
        return modelMapper.map(userRepresentation, UserDto.class);
    }

}
