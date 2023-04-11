package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(int id, UserDto userDto);

    List<UserDto> getAllUser();

    UserDto getUserByEmail(String email);

    UserDto getUserByName(String name);

    String deleteUser(int id);
}
