package com.example.qitaab_backend.controller;


import com.example.qitaab_backend.dto.BookDTO;
import com.example.qitaab_backend.dto.SellerDTO;
import com.example.qitaab_backend.dto.SellerSignupDTO;
import com.example.qitaab_backend.dto.UserDTO;
import com.example.qitaab_backend.service.BookService;
import com.example.qitaab_backend.service.CustomerService;
import com.example.qitaab_backend.service.SellerService;
import com.example.qitaab_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private final UserService userService;
    private final SellerService sellerService;
    private final BookService bookService;
    private final CustomerService customerService;


    public SellerController(UserService userService, SellerService sellerService, BookService bookService, CustomerService customerService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @GetMapping("/index")
    public String index(){
        return "Relax Developer! Your SpringBoot is working fine!";
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> Signup(@RequestBody SellerSignupDTO signup_dto){

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(signup_dto.getEmail());
        userDTO.setPassword(signup_dto.getPassword());

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName(signup_dto.getName());
        sellerDTO.setAddress(signup_dto.getAddress());
        sellerDTO.setAmount(signup_dto.getAmount());
        sellerDTO.setPhone(signup_dto.getPhone());


        Long user_id = userService.addUser(userDTO);
        sellerDTO.setUser_id(user_id);

        Long seller_id = sellerService.addSeller(sellerDTO);
        return seller_id>0? ResponseEntity.ok().body(Collections.singletonMap("token", user_id)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Account Could not be created."));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> GetAllSellers(){
        List<SellerDTO> seller_list = sellerService.getAll();
        if (!seller_list.isEmpty()) {
            return ResponseEntity.ok().body(seller_list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No seller found"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> GetSellerByID(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            Long seller_id = Long.parseLong(id);
            SellerDTO sellerInfo = sellerService.getSellerByID(seller_id);
            if (sellerInfo != null) {
                return ResponseEntity.ok().body(sellerInfo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Seller information not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The seller ID cannot be obtained from the path"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> ViewProfile(@RequestBody int id){
        if(id >0){
            Long userId = (long) id;
            SellerDTO seller = sellerService.getSellerBy_user_id(userId);
            if(seller != null){
                return ResponseEntity.ok().body(seller);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Seller information not found"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The user ID cannot be obtained"));
        }
    }

    @GetMapping("/books")
    public ResponseEntity<Object> ViewMyBooks(@RequestBody int id){
       if(id>0){
           Long userId = (long) id;
           List<BookDTO> book_list = bookService.getAllBooksBy_user_id(userId);
           if (!book_list.isEmpty()) {
               return ResponseEntity.ok().body(book_list);
           } else {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No Book found"));
           }
       }else{
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The user ID cannot be obtained"));
       }
    }

    @GetMapping("/total_income")
    public ResponseEntity<Object> TotalIncome(@RequestBody int id){
        if(id>0){
            Long userId = (long) id;
            double total_income = sellerService.getSellerAmountBy_user_id(userId);
            if(total_income>=0){
                return ResponseEntity.ok().body(total_income);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Seller amount not found"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The user ID cannot be obtained"));
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Object> GetSingleBookInfo(@PathVariable("id") String id){
        if(id != null){
            Long book_id = Long.parseLong(id);
            BookDTO book = bookService.getBookByID(book_id);
            if(book != null){
                return ResponseEntity.ok().body(book);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Book information not found"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The Book ID cannot be obtained"));
        }
    }

    @PostMapping("/books/add")
    private ResponseEntity<Object> AddBook(@RequestBody BookDTO book_dto){
        if(book_dto != null && book_dto.getUser_id() > 0){
            Long book_id = bookService.addBook(book_dto);
            if(book_id > 0){
                return ResponseEntity.ok().body(Collections.singletonMap("token", book_id));
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Book Could not be Added"));
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Some book information's are missing"));
        }
    }

    @PutMapping("/book/update")
    private ResponseEntity<Object> UpdateBook(@RequestBody BookDTO book_dto){
        if(book_dto != null && book_dto.getUser_id() > 0){
            BookDTO updated_book = bookService.updateBook(book_dto);
            if(updated_book != null){
                return ResponseEntity.ok().body(updated_book);
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Book Could not be Updated"));
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Some book information's are missing"));
        }
    }

    @DeleteMapping("/book/delete/{id}")
    private ResponseEntity<Object> DeleteBook(@PathVariable("id") String id){
        if(id != null){
            Long book_id = Long.parseLong(id);
            Boolean decision = bookService.delete(book_id);
            if(decision){
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Book could not be deleted"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The Book ID cannot be obtained"));
        }
    }

    @PutMapping("/book/update/quantity")
    private ResponseEntity<Object> AddBookQuantity(@RequestBody Long book_id, int quantity){
        if(book_id > 0 && quantity>0){
            int updated_quantity = bookService.addBookQuantity(book_id,quantity);
            if(updated_quantity > 0){
                return ResponseEntity.ok().body(Collections.singletonMap("token", updated_quantity));
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Failed to add book quantity"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "book_id or quantity is missing"));
        }
    }





}
