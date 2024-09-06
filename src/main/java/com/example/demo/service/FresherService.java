package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UsersRequest;
import com.example.demo.dto.request.FresherRequest;
import com.example.demo.dto.response.FresherResponse;
import com.example.demo.entity.Users;
import com.example.demo.entity.Fresher;
import com.example.demo.repository.FresherRepository;


@Service
public class FresherService {

    @Autowired
    private FresherRepository fresherRepository;

    @Autowired
    private UsersService usersService;


    public FresherResponse addFresher(FresherRequest fresherReqDto) {
        try{
            UsersRequest userReq = new UsersRequest();
            userReq.setUsername(fresherReqDto.getUsername());
            userReq.setPassword(fresherReqDto.getPassword());
            userReq.setName(fresherReqDto.getName());
            userReq.setEmail(fresherReqDto.getEmail());
            userReq.setPhoneNumber(fresherReqDto.getPhoneNumber());
            Users user = usersService.addUsers(userReq);
            Fresher fresher = new Fresher();
            fresher.setUser(user);
            fresher.setProgrammingLanguage(fresherReqDto.getProgrammingLanguage());
            return convertToDTO(fresherRepository.save(fresher));
        }catch (RuntimeException e) {
            throw new RuntimeException("Nhập thiếu thông tin");
        }
    }
    
    // Lấy tất cả Freshers
    public List<FresherResponse> getAllFresher() {
        return fresherRepository.findAll().stream()
                .filter(fresher -> fresher.getUser() != null)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Fresher getFresher(int id) {
        Fresher fresher = fresherRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        if (fresher.getUser()==null) {
            fresherRepository.save(fresher);
            throw new RuntimeException("User không tồn tại");
        }
        return fresher;
    }

    // Cập nhật Fresher
    public FresherResponse updateFresher(int id, FresherRequest req) {
        Fresher fresher = getFresher(id);
        fresher.setProgrammingLanguage(req.getProgrammingLanguage());
        return convertToDTO(fresherRepository.save(fresher));
    }

    // Xóa Fresher theo ID
    public boolean deleteFresher(int id) {
        try{
            fresherRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public FresherResponse convertToDTO(Fresher fresher){
        FresherResponse fresherResDto = new FresherResponse();
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
