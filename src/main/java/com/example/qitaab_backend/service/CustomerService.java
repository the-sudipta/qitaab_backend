package com.example.qitaab_backend.service;

import com.example.qitaab_backend.dto.CustomerDTO;
import com.example.qitaab_backend.entity.Customer;
import com.example.qitaab_backend.repository.ICustomerRepository;
import com.example.qitaab_backend.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    private final ICustomerRepository customerRepo;

    private final IUserRepository userRepo;
    private final SellerService sellerService;
    private final MapperService mapper;

    public CustomerService(ICustomerRepository customerRepo, IUserRepository userRepo, SellerService sellerService, MapperService mapper) {
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;
        this.sellerService = sellerService;
        this.mapper = mapper;
    }


    public List<CustomerDTO> getAll(){
        List<Customer> customer_list = customerRepo.findAll();
        List<CustomerDTO> customer_dto_list = mapper.convertToDtoList(customer_list, CustomerDTO.class);

        for (int i = 0; i < customer_dto_list.size(); i++) {
            CustomerDTO customerDTO = customer_dto_list.get(i);
            Customer customer_entity = customer_list.get(i);
            customerDTO.setEmail(customer_entity.getUser().getEmail());
        }
        return customer_dto_list;
    }

    public CustomerDTO getCustomerByID(Long id){
        return mapper.convertToDto((customerRepo.findById(id)),CustomerDTO.class);
    }

    public CustomerDTO getCustomerBy_user_id(Long id){

        return mapper.convertToDto((customerRepo.findCustomerByUser(userRepo.findUserById(id))),CustomerDTO.class);
    }

    public Long addCustomer(CustomerDTO customer_dto){
        Customer saved_customer = customerRepo.save(mapper.convertToEntity(customer_dto, Customer.class));
        return saved_customer.getId();
    }


    public CustomerDTO updateCustomer(CustomerDTO customer_dto){
        Customer saved_customer = customerRepo.findCustomerById(customer_dto.getId());
        if (saved_customer != null) {

            if(!Objects.equals(saved_customer.getName(), customer_dto.getName()) && !Objects.equals(customer_dto.getName(), "") && customer_dto.getName() != null ){
                saved_customer.setName(customer_dto.getName());
            }

            if(saved_customer.getAge() != customer_dto.getAge() && customer_dto.getAge() >= 0 ){
                saved_customer.setAge(customer_dto.getAge());
            }

            if(!Objects.equals(saved_customer.getPhone(), customer_dto.getPhone()) && !Objects.equals(customer_dto.getPhone(), "") && customer_dto.getPhone() != null){
                saved_customer.setPhone(customer_dto.getPhone());
            }

            customerRepo.save(saved_customer);
            return mapper.convertToDto(saved_customer, CustomerDTO.class);
        } else {
            return null;
        }
    }

    public Boolean delete(Long id){
        if (id != null && id >0){
            customerRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Boolean purchaseBook(Long book_id, int book_quantity){




        return false;
    }



}
