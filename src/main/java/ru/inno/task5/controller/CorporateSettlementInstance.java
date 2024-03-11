package ru.inno.task5.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inno.task5.service.*;

@RestController
@RequestMapping("corporate-settlement-instance/")

public class CorporateSettlementInstance {
    @Autowired
    private InstanceService instanceService;

    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody InstanceRequest request){
        System.out.println(request);
        return instanceService.process(request);
    }

}
