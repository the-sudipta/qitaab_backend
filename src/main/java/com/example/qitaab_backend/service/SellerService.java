package com.example.qitaab_backend.service;

import com.example.qitaab_backend.dto.SellerDTO;
import com.example.qitaab_backend.entity.Book;
import com.example.qitaab_backend.entity.Seller;
import com.example.qitaab_backend.repository.IBookRepository;
import com.example.qitaab_backend.repository.ISellerRepository;
import com.example.qitaab_backend.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SellerService {

    private final ISellerRepository sellerRepo;
    private final IUserRepository userRepo;
    private final IBookRepository bookRepo;
    private final MapperService mapper;


    public SellerService(ISellerRepository sellerRepo, IUserRepository userRepo, IBookRepository bookRepo, MapperService mapper) {
        this.sellerRepo = sellerRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.mapper = mapper;
    }

    public List<SellerDTO> getAll(){
        List<Seller> seller_list = sellerRepo.findAll();
        List<SellerDTO> seller_dto_list = mapper.convertToDtoList(seller_list, SellerDTO.class);

        for (int i = 0; i < seller_dto_list.size(); i++) {
            SellerDTO sellerDTO = seller_dto_list.get(i);
            Seller seller_entity = seller_list.get(i);
            sellerDTO.setEmail(seller_entity.getUser().getEmail());
        }
        return seller_dto_list;
    }

    public SellerDTO getSellerByID(Long id){
        return mapper.convertToDto((sellerRepo.findById(id)),SellerDTO.class);
    }

    public SellerDTO getSellerBy_user_id(Long id){

        return mapper.convertToDto((sellerRepo.findSellerByUser(userRepo.findUserById(id))),SellerDTO.class);
    }

    public Long addSeller(SellerDTO seller_dto){
        Seller saved_seller = sellerRepo.save(mapper.convertToEntity(seller_dto, Seller.class));
        return saved_seller.getId();
    }


    public SellerDTO updateSeller(SellerDTO seller_dto){
        Seller saved_seller = sellerRepo.findSellerById(seller_dto.getId());
        if (saved_seller != null) {

            if(!Objects.equals(saved_seller.getName(), seller_dto.getName()) && !Objects.equals(seller_dto.getName(), "") && seller_dto.getName() != null ){
                saved_seller.setName(seller_dto.getName());
            }

            if(saved_seller.getAmount() != seller_dto.getAmount() && seller_dto.getAmount() >= 0 ){
                saved_seller.setAmount(seller_dto.getAmount());
            }

            if(!Objects.equals(saved_seller.getPhone(), seller_dto.getPhone()) && !Objects.equals(seller_dto.getPhone(), "") && seller_dto.getPhone() != null){
                saved_seller.setPhone(seller_dto.getPhone());
            }

            if(!Objects.equals(saved_seller.getAddress(), seller_dto.getAddress()) && !Objects.equals(seller_dto.getAddress(), "") && seller_dto.getAddress() != null ){
                saved_seller.setAddress(seller_dto.getAddress());
            }

            sellerRepo.save(saved_seller);
            return mapper.convertToDto(saved_seller, SellerDTO.class);
        } else {
            return null;
        }
    }

    public double updateAmountByID(Long id, double new_amount){

        if (id >0 && new_amount>=0) {
            Seller saved_seller = sellerRepo.findSellerById(id);
            if(saved_seller != null){
                saved_seller.setAmount(new_amount);
                sellerRepo.save(saved_seller);
                return saved_seller.getAmount();
            }else{
                return -1;
            }
        } else {
            return -1;
        }
    }

    public double updateAmountBy_user_id(Long user_id, double new_amount){

        if (user_id >0 && new_amount>=0) {
            Seller saved_seller = sellerRepo.findSellerByUser(userRepo.findUserById(user_id));
            if(saved_seller != null){
                saved_seller.setAmount(new_amount);
                sellerRepo.save(saved_seller);
                return saved_seller.getAmount();
            }else{
                return -1;
            }
        } else {
            return -1;
        }
    }

    public double addAmountByBookID(Long book_id, int book_quantity){

        if (book_id >0 && book_quantity>0) {

            Book saved_book = bookRepo.findBookById(book_id);
            double new_amount = saved_book.getPrice() *  book_quantity;
            Seller saved_seller = sellerRepo.findSellerById(saved_book.getUser().getId());
            if(saved_seller != null){
                double previous_amount = saved_seller.getAmount();
                saved_seller.setAmount(previous_amount+new_amount);
                sellerRepo.save(saved_seller);
                return saved_seller.getAmount();
            }else{
                return -1;
            }
        } else {
            return -1;
        }
    }

    public Boolean delete(Long id){
        if (id != null && id >0){
            sellerRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public double getSellerAmountBy_user_id(Long user_id){
        if (user_id >0) {
            Seller saved_seller = sellerRepo.findSellerByUser(userRepo.findUserById(user_id));
            if(saved_seller != null){
                return saved_seller.getAmount();
            }else{
                return -1;
            }
        } else {
            return -1;
        }
    }



}
