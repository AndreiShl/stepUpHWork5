package ru.inno.task5.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inno.task5.service.AccountRequest;
import ru.inno.task5.service.AccountService;

@RestController
@RequestMapping("corporate-settlement-account/")

public class CorporateSettlementAccount {

    @Autowired
    AccountService accountService;

    @PostMapping(value = "create", consumes = "application/json;charset=UTF-8"
            , produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> create(@Valid @RequestBody AccountRequest request){
        return accountService.createAccount(request);
    }
}
