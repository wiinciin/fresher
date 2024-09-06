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

import com.example.demo.dto.request.BcRequest;
import com.example.demo.dto.response.BcResponse;
import com.example.demo.entity.Bc;
import com.example.demo.service.BcService;

@RestController
@RequestMapping("/freshers")
public class BcController {

    @Autowired
    private BcService fresherService;

    // Thêm mới Fresher
    @PostMapping
    public BcResponse createFresher(@RequestBody BcRequest fresherReqDto) {
        return fresherService.addBc(fresherReqDto);
    }

    // Lấy tất cả Freshers
    @GetMapping
    public List<BcResponse> getAllFreshers() {
        return fresherService.getAllBc();
    }

    // Lấy Fresher theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BcResponse> getFresherById(@PathVariable int id) {
        Bc fresher = fresherService.getBc(id);
        if (fresher == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fresherService.convertToDTO(fresher));
    }

    // Cập nhật Fresher
    @PutMapping("/{id}")
    public ResponseEntity<BcResponse> updateFresher(@PathVariable int id, @RequestBody BcRequest req) {
        try {
            BcResponse updatedFresher = fresherService.updateBc(id, req);
            return ResponseEntity.ok(updatedFresher);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa Fresher theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFresher(@PathVariable int id) {
        fresherService.deleteBc(id);
        return ResponseEntity.noContent().build();
    }
}
