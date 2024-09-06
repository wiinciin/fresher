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

import com.example.demo.dto.request.FresherRequest;
import com.example.demo.dto.response.FresherResponse;
import com.example.demo.entity.Fresher;
import com.example.demo.service.FresherService;

@RestController
@RequestMapping("/freshers")
public class FresherController {

    @Autowired
    private FresherService fresherService;

    // Thêm mới Fresher
    @PostMapping
    public FresherResponse createFresher(@RequestBody FresherRequest fresherReqDto) {
        return fresherService.addFresher
    (fresherReqDto);
    }

    // Lấy tất cả Freshers
    @GetMapping
    public List<FresherResponse> getAllFreshers() {
        return fresherService.getAllFresher
    ();
    }

    // Lấy Fresher theo ID
    @GetMapping("/{id}")
    public ResponseEntity<FresherResponse> getFresherById(@PathVariable int id) {
        Fresher fresher = fresherService.getFresher
    (id);
        if (fresher == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fresherService.convertToDTO(fresher));
    }

    // Cập nhật Fresher
    @PutMapping("/{id}")
    public ResponseEntity<FresherResponse> updateFresher(@PathVariable int id, @RequestBody FresherRequest req) {
        try {
            FresherResponse updatedFresher = fresherService.updateFresher
        (id, req);
            return ResponseEntity.ok(updatedFresher);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa Fresher theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFresher(@PathVariable int id) {
        fresherService.deleteFresher
    (id);
        return ResponseEntity.noContent().build();
    }
}
