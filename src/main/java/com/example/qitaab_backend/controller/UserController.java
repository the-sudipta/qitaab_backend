package com.example.qitaab_backend.controller;


import com.example.qitaab_backend.dto.LoginDTO;
import com.example.qitaab_backend.dto.UserDTO;
import com.example.qitaab_backend.service.BookService;
import com.example.qitaab_backend.service.CustomerService;
import com.example.qitaab_backend.service.SellerService;
import com.example.qitaab_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final SellerService sellerService;
    private final BookService bookService;
    private final CustomerService customerService;


    public UserController(UserService userService, SellerService sellerService, BookService bookService, CustomerService customerService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @GetMapping("/index")
    public String index(){
        return "Relax Developer! Your SpringBoot is working fine!";
    }

    @PostMapping("/login")
    public ResponseEntity<Object> Login(@RequestBody LoginDTO login_dto){
        if(login_dto.getEmail() != null && !login_dto.getEmail().isEmpty() && login_dto.getPassword() != null && !login_dto.getPassword().isEmpty()){
            Long user_id = -1L;
            Boolean decision = userService.getUserByEmailAndPassword(login_dto);
            if(decision){
                user_id = userService.getIDByEmail(login_dto.getEmail());
            }
            return decision? ResponseEntity.ok().body(Collections.singletonMap("token", user_id)) :
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Email and Password did not match"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Email and Password did not provided properly"));
        }
    }

//    Delete Account




}
