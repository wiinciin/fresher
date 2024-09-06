package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UsersRequest;
import com.example.demo.dto.response.UsersResponse;
import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;



@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    

    // Thêm mới Users
    public Users addUsers(UsersRequest user) {
        try{
            Users newUser = new Users();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPhoneNumber(user.getPhoneNumber());
            return usersRepository.save(newUser);
        }catch (RuntimeException e) {
            throw new RuntimeException("Nhập thiếu thông tin hoặc hoặc username, email đã tồn tại");
        }
    }

    // Lấy tất cả Users dưới dạng userResDto
    public List<UsersResponse> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Users getUser(int id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy User"));
        return user;
    }

    public UsersResponse updateUsers(int id, UsersRequest userDto) {
        try{
            Users user = getUser(id);
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPhoneNumber(userDto.getPhoneNumber());
            return convertToDTO(usersRepository.save(user));
        }catch (RuntimeException e) {
            throw new RuntimeException("Nhập thiếu thông tin hoặc hoặc username, email đã tồn tại");
        }
    }

    // Xóa Users theo ID
    public boolean deleteUsers(int id) {
        try{
            usersRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Phương thức hỗ trợ chuyển đổi từ Users sang AResponse
    public UsersResponse convertToDTO(Users user) {
        UsersResponse dto = new UsersResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }
}

