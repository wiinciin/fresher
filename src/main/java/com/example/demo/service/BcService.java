package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UsersRequest;
import com.example.demo.dto.request.BcRequest;
import com.example.demo.dto.response.BcResponse;
import com.example.demo.entity.Users;
import com.example.demo.entity.Bc;
import com.example.demo.repository.BcRepository;


@Service
public class BcService {

    @Autowired
    private BcRepository fresherRepository;

    @Autowired
    private UsersService usersService;


    public BcResponse addBc(BcRequest fresherReqDto) {
        try{
            UsersRequest userReq = new UsersRequest();
            userReq.setUsername(fresherReqDto.getUsername());
            userReq.setPassword(fresherReqDto.getPassword());
            userReq.setName(fresherReqDto.getName());
            userReq.setEmail(fresherReqDto.getEmail());
            userReq.setPhoneNumber(fresherReqDto.getPhoneNumber());
            Users user = usersService.addUsers(userReq);
            Bc fresher = new Bc();
            fresher.setUser(user);
            fresher.setProgrammingLanguage(fresherReqDto.getProgrammingLanguage());
            return convertToDTO(fresherRepository.save(fresher));
        }catch (RuntimeException e) {
            throw new RuntimeException("Nhập thiếu thông tin");
        }
    }
    
    // Lấy tất cả Bcs
    public List<BcResponse> getAllBc() {
        return fresherRepository.findAll().stream()
                .filter(fresher -> fresher.getUser() != null)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Bc getBc(int id) {
        Bc fresher = fresherRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        if (fresher.getUser()==null) {
            fresherRepository.save(fresher);
            throw new RuntimeException("User không tồn tại");
        }
        return fresher;
    }

    // Cập nhật Bc
    public BcResponse updateBc(int id, BcRequest req) {
        Bc fresher = getBc(id);
        fresher.setProgrammingLanguage(req.getProgrammingLanguage());
        return convertToDTO(fresherRepository.save(fresher));
    }

    // Xóa Bc theo ID
    public boolean deleteBc(int id) {
        try{
            fresherRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BcResponse convertToDTO(Bc fresher){
        BcResponse fresherResDto = new BcResponse();
        Users user = fresher.getUser();
        fresherResDto.setId(fresher.getId());
        fresherResDto.setIdUser(user.getId());
        fresherResDto.setUsername(user.getUsername());
        fresherResDto.setName(user.getName());
        fresherResDto.setEmail(user.getEmail());
        fresherResDto.setPhoneNumber(user.getPhoneNumber());
        fresherResDto.setProgrammingLanguage(fresher.getProgrammingLanguage());
        return fresherResDto;
    }
}
