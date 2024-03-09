package ru.inno.task5.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inno.task5.service.AccountRequest;
import ru.inno.task5.service.AccountService;

@RestController
@RequestMapping("corporate-settlement-account/")

public class CorporateSettlementAccount {

    @Autowired
    AccountService accountService;

    @GetMapping("info")
    public AccountRequest info(){
        return new AccountRequest(12);
    }


    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody AccountRequest request){
        return accountService.createAccount(request);
    }
}
