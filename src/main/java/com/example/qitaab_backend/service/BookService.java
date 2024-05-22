package com.example.qitaab_backend.service;

import com.example.qitaab_backend.dto.BookDTO;
import com.example.qitaab_backend.dto.SellerDTO;
import com.example.qitaab_backend.entity.Book;
import com.example.qitaab_backend.entity.Seller;
import com.example.qitaab_backend.repository.IBookRepository;
import com.example.qitaab_backend.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {


    private final IBookRepository bookRepo;
    private final IUserRepository userRepo;
    private final MapperService mapper;


    public BookService(IBookRepository bookRepo, IUserRepository userRepo, MapperService mapper) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    public List<BookDTO> getAll(){
        List<Book> book_list = bookRepo.findAll();
        List<BookDTO> book_dto_list = mapper.convertToDtoList(book_list, BookDTO.class);
        return book_dto_list;
    }

    public BookDTO getBookByID(Long id){
        return mapper.convertToDto((bookRepo.findById(id)),BookDTO.class);
    }

    public List<BookDTO> getAllBooksBy_user_id(Long id){
        List<Book> books = bookRepo.findAllByUser(userRepo.findUserById(id));
        return mapper.convertToDtoList(books, BookDTO.class);
    }

    public Long addBook(BookDTO book_dto){
        Book saved_book = bookRepo.save(mapper.convertToEntity(book_dto, Book.class));
        return saved_book.getId()>0? saved_book.getId() : -1;
    }

    public BookDTO updateBook(BookDTO book_dto){
        Book saved_book = bookRepo.findBookById(book_dto.getId());
        if (saved_book != null) {

            if(!Objects.equals(saved_book.getName(), book_dto.getName()) && !Objects.equals(book_dto.getName(), "") && book_dto.getName() != null ){
                saved_book.setName(book_dto.getName());
            }

            if(saved_book.getPrice() != book_dto.getPrice() && book_dto.getPrice() >= 0 ){
                saved_book.setPrice(book_dto.getPrice());
            }

            if(saved_book.getQuantity() != book_dto.getQuantity() && book_dto.getQuantity() >= 0 ){
                saved_book.setQuantity(book_dto.getQuantity());
            }

            if(!Objects.equals(saved_book.getType(), book_dto.getType()) && !Objects.equals(book_dto.getType(), "") && book_dto.getType() != null){
                saved_book.setType(book_dto.getType());
            }

            if(!Objects.equals(saved_book.getAuthor(), book_dto.getAuthor()) && !Objects.equals(book_dto.getAuthor(), "") && book_dto.getAuthor() != null ){
                saved_book.setAuthor(book_dto.getAuthor());
            }

            bookRepo.save(saved_book);
            return mapper.convertToDto(saved_book, BookDTO.class);
        } else {
            return null;
        }
    }

    public Boolean delete(Long id){
        if (id != null && id >0){
            bookRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }


    public int addBookQuantity(Long id, int quantity){
        Book saved_book = bookRepo.findBookById(id);
        int previous_quantity = saved_book.getQuantity();
        saved_book.setQuantity(previous_quantity+quantity);
        bookRepo.save(saved_book);
        return (previous_quantity+quantity);
    }

    public int subtractBookQuantity(Long id, int quantity){
        Book saved_book = bookRepo.findBookById(id);
        int previous_quantity = saved_book.getQuantity();
        if(previous_quantity > quantity){
            saved_book.setQuantity(previous_quantity-quantity);
            bookRepo.save(saved_book);
            return (previous_quantity-quantity);
        }else {
            return -1;
        }
    }

}
