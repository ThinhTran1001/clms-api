package vn.threeluaclmsapi.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import vn.threeluaclmsapi.dto.request.CreateUserRequest;
import vn.threeluaclmsapi.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();

    List<UserResponse> getAllUsers();

    UserResponse getUserById(String id);

    UserResponse createUser(CreateUserRequest createUserRequest);

    UserResponse updateUser(String id, CreateUserRequest createUserRequest);

    UserResponse activateUser(String id);

    UserResponse inactivateUser(String id);


}
