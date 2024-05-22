package com.example.qitaab_backend.controller;


import com.example.qitaab_backend.dto.*;
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
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final BookService bookService;
    private final UserService userService;
    private final SellerService sellerService;

    public CustomerController(CustomerService customerService, BookService bookService, UserService userService, SellerService sellerService) {
        this.customerService = customerService;
        this.bookService = bookService;
        this.userService = userService;
        this.sellerService = sellerService;
    }



    @PostMapping("/signup")
    public ResponseEntity<Object> Signup(@RequestBody CustomerSignupDTO signup_dto){

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(signup_dto.getEmail());
        userDTO.setPassword(signup_dto.getPassword());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(signup_dto.getName());
        customerDTO.setAge(signup_dto.getAge());
        customerDTO.setPhone(signup_dto.getPhone());


        Long user_id = userService.addUser(userDTO);
        customerDTO.setUser_id(user_id);

        Long customer_id = customerService.addCustomer(customerDTO);
        return customer_id>0? ResponseEntity.ok().body(Collections.singletonMap("token", user_id)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Account Could not be created."));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> GetAllCustomers(){
        List<CustomerDTO> seller_list = customerService.getAll();
        if (!seller_list.isEmpty()) {
            return ResponseEntity.ok().body(seller_list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No seller found"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> ViewProfile(@RequestBody int id){
        if(id >0){
            Long userId = (long) id;
            CustomerDTO customer = customerService.getCustomerBy_user_id(userId);
            if(customer != null){
                return ResponseEntity.ok().body(customer);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Customer information not found"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The user ID cannot be obtained"));
        }
    }


//    View All Books
    @GetMapping("/books")
    public ResponseEntity<Object> ViewAllBooks(){
        List<BookDTO> book_list = bookService.getAll();
        if (!book_list.isEmpty()) {
            return ResponseEntity.ok().body(book_list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No Book found"));
        }
    }


    @GetMapping("/book/{id}")
    public ResponseEntity<Object> GetSellerByID(@PathVariable("id") String id) {
        if (id != null && !id.isEmpty()) {
            Long book_id = Long.parseLong(id);
            BookDTO bookInfo = bookService.getBookByID(book_id);
            if (bookInfo != null) {
                return ResponseEntity.ok().body(bookInfo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Book information not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "The Book ID cannot be obtained from the path"));
        }
    }

//    Buy a Book
@GetMapping("/book/purchase")
public ResponseEntity<Object> GetSellerByID(@RequestBody BookPurchaseDTO book_purchase) {
    if (book_purchase != null) {
        if(book_purchase.getBook_id()>0 && book_purchase.getQuantity()>0){
            double seller_income = sellerService.addAmountByBookID(book_purchase.getBook_id(), book_purchase.getQuantity());
            if(seller_income>0){
                int new_book_quantity = bookService.subtractBookQuantity(book_purchase.getBook_id(), book_purchase.getQuantity());
                if(new_book_quantity > 0){
                    return ResponseEntity.ok().build();
                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Failed to reduce book quantity"));
                }
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Failed to send money to the Seller account"));
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "book_id or quantity is missing"));
        }
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "book_id and quantity must be send through an object"));
    }
}




}
