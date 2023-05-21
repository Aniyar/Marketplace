package com.example.bakery.controller;

import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/becomeSeller")
public class BecomeSellerController {
    private final BecomeSellerRequestRepository BSRrepository;

    @PostMapping
    public ResponseEntity<BecomeSellerRequest> becomeSeller( @RequestBody BecomeSellerRequest request){
        request.setApproved(false);
        return ResponseEntity.ok(BSRrepository.save(request));
    }
}
