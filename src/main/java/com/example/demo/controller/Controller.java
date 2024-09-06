package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.UsersRequest;
import com.example.demo.dto.response.UsersResponse;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;

@RestController
@RequestMapping("/users")
public class Controller {

    @Autowired
    private UsersService usersService;

    // Thêm mới Users
    @PostMapping
    public Users createUsers(@RequestBody UsersRequest newUsersReq) {
        return usersService.addUsers(newUsersReq);
    }

    // Lấy tất cả Users
    @GetMapping
    public List<UsersResponse> getAllUsers() {
        return usersService.getAllUsers();
    }

    // Lấy Users theo ID
    @GetMapping("/{id}")
    public ResponseEntity<UsersResponse> getUsersById(@PathVariable int id) {
        try {
            Users user = usersService.getUser(id);
            return ResponseEntity.ok(usersService.convertToDTO(user));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Cập nhật Users
    @PutMapping("/{id}")
    public ResponseEntity<UsersResponse> updateUsers(@PathVariable int id, @RequestBody UsersRequest user) {
        // try {
            UsersResponse updatedUsers = usersService.updateUsers(id, user);
            return ResponseEntity.ok(updatedUsers);
        // } catch (RuntimeException e) {
        //     return ResponseEntity.notFound().build();
        // }
    }

    // Xóa Users theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable int id) {
        usersService.deleteUsers(id);
        return ResponseEntity.noContent().build();
    }
}
