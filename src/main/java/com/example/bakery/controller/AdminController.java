package com.example.bakery.controller;

import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.SellerNotFoundException;
import com.example.bakery.exceptionHandler.SellerRequestNotFoundException;
import com.example.bakery.exceptionHandler.UserNotFoundException;
import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import com.example.bakery.request.*;
import com.example.bakery.response.SellerResponse;
import com.example.bakery.service.AdminService;
import com.example.bakery.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductCategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    private final BecomeSellerRequestRepository BSRrepository;
    private final AdminService adminService;
    private final OrderRepository orderRepository;

    @GetMapping("/category")
    public ResponseEntity<Iterable<ProductCategory>> getCategories(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping("/category/add")
    public ResponseEntity<ProductCategory> addCategory(@RequestBody CategoryRequest request){
        return ResponseEntity.ok(adminService.addCategory(request));
    }

    @PutMapping("/category/edit")
    public ResponseEntity<ProductCategory> editCategory(@RequestBody CategoryRequest request) throws CategoryNotFoundException {
        return ResponseEntity.ok(adminService.editCategory(request));
    }

    @DeleteMapping ("/category/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id) throws CategoryNotFoundException {
        return ResponseEntity.ok(adminService.deleteCategory(id));
    }

    @GetMapping("/sellerRequests")
    public ResponseEntity<Iterable<BecomeSellerRequest>> getAllSellerRequests(){
        return ResponseEntity.ok(BSRrepository.findAll());
    }

    @GetMapping("/sellerRequests/unapproved")
    public ResponseEntity<Iterable<BecomeSellerRequest>> getWaitingSellerRequests(){
        return ResponseEntity.ok(BSRrepository.findAllUnapproved());
    }

    @GetMapping("/sellerRequests/approved")
    public ResponseEntity<Iterable<BecomeSellerRequest>> getCompletedSellerRequests(){
        return ResponseEntity.ok(BSRrepository.findAllApproved());
    }

    @PutMapping("/sellerRequests/{id}/approve")
    public ResponseEntity<SellerResponse> approveSellerRequest(@PathVariable Integer id) throws SellerRequestNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(adminService.approveSellerRequest(id));
    }

    @GetMapping("/seller")
    public ResponseEntity<Iterable<SellerResponse>> getSellers(){
        return ResponseEntity.ok(adminService.getAllSellers());
    }

    @DeleteMapping ("/seller/delete/{id}")
    public ResponseEntity deleteSeller(@PathVariable Integer id) throws SellerNotFoundException {
        adminService.deleteSeller(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getAllOrders(){
        Iterable<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

}
