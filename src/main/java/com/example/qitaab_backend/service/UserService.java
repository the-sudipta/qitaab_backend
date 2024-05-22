package com.example.qitaab_backend.service;

import com.example.qitaab_backend.dto.LoginDTO;
import com.example.qitaab_backend.dto.UserDTO;
import com.example.qitaab_backend.entity.User;
import com.example.qitaab_backend.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final IUserRepository userRepo;
    private final MapperService mapper;

    public UserService(IUserRepository userRepo, MapperService mapper) {
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    public Boolean getUserByEmailAndPassword(LoginDTO login){
        User valid_user = userRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
        return valid_user != null;
    }

    public List<UserDTO> getAll(){
        List<User> user_list = userRepo.findAll();
        return mapper.convertToDtoList(user_list, UserDTO.class);
    }

    public UserDTO getByID(Long id){
        return mapper.convertToDto(userRepo.findById(id), UserDTO.class);
    }

    public Long getIDByEmail(String email){
        return (userRepo.findByEmail(email)).getId();
    }


    public Long addUser(UserDTO user_dto){
        User saved_user = userRepo.save(mapper.convertToEntity(user_dto, User.class));
        return saved_user.getId();
    }

    public UserDTO updatePassword(LoginDTO newData){
        User user = userRepo.findByEmail(newData.getEmail());
        if (user != null) {
            user.setPassword(newData.getPassword());
            userRepo.save(user);
            return mapper.convertToDto(user, UserDTO.class);
        } else {
            return null;
        }
    }

    public Boolean delete(Long id){
        if (id != null && id >0){
            userRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }





}
