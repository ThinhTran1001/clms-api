package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.CreateUserRequest;
import vn.threeluaclmsapi.dto.response.UserResponse;
import vn.threeluaclmsapi.exception.CommonException;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.User;
import vn.threeluaclmsapi.repository.UserRepository;
import vn.threeluaclmsapi.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found!"));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }

    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .dob(user.getDob())
                .gender(user.getGender())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .status(user.isStatus())
                .build();
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new CommonException("Email already in use.");
        }
        User user = convertToEntity(createUserRequest);
        user.setStatus(true);
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(String id, CreateUserRequest createUserRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        updateUserEntity(existingUser, createUserRequest);
        User updatedUser = userRepository.save(existingUser);
        return convertToResponse(updatedUser);
    }

    @Override
    public UserResponse activateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (user.isStatus()) {
            throw new CommonException("User is already activated");
        }
        user.setStatus(true);
        User activatedUser = userRepository.save(user);
        return convertToResponse(activatedUser);
    }

    @Override
    public UserResponse inactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (!user.isStatus()) {
            throw new CommonException("User is already inactivated");
        }
        user.setStatus(false);
        User inactivatedUser = userRepository.save(user);
        return convertToResponse(inactivatedUser);
    }

    private void updateUserEntity(User existingUser, CreateUserRequest createUserRequest) {
        existingUser.setPassword(createUserRequest.getPassword());
        existingUser.setEmail(createUserRequest.getEmail());
        existingUser.setDob(createUserRequest.getDob());
        existingUser.setGender(createUserRequest.getGender());
        existingUser.setFullName(createUserRequest.getFullName());
        existingUser.setAddress(createUserRequest.getAddress());
    }

    private User convertToEntity(CreateUserRequest createUserRequest) {
        return User.builder()
                .password(createUserRequest.getPassword())
                .email(createUserRequest.getEmail())
                .dob(createUserRequest.getDob())
                .gender(createUserRequest.getGender())
                .fullName(createUserRequest.getFullName())
                .address(createUserRequest.getAddress())
                .build();
    }
}
