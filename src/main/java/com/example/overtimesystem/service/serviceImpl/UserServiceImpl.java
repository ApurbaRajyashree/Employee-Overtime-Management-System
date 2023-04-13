package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.entity.User;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return new UserDto(user);
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Invalid user id")
        );
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setDesignation(userDto.getDesignation());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setPassword(userDto.getPassword());
        User updatedUser = userRepository.save(user);
        return new UserDto(updatedUser);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(x -> new UserDto(x)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDto(user);
        } else {
            throw new RuntimeException("Given email does not exist");
        }
    }

    @Override
    public UserDto getUserByName(String name) {
        User user = userRepository.findByFullName(name);
        if (user != null) {
            return new UserDto(user);
        } else {
            throw new RuntimeException("Given user name does not exist");
        }
    }

    @Override
    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "User has been successfully deleted";
    }
}
