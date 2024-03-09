package ru.inno.task5.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.inno.task5.service.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("corporate-settlement-instance/")

public class CorporateSettlementInstance {
    @Autowired
    private InstanceService instanceService;

    @GetMapping("info")
    public ResponseEntity<?> get(){
        InstanceRequest instanceRequest = new InstanceRequest(
                1,
                "03.012.002_47533_ComSoLd",
                "productCode",
                "registerType",
                "mdmCode",
                "contractNumber",
                Instant.now(),
                5,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "0021",
                null,
                null,
                null,
                List.of(new AdditionalProperties("key1","value1","name1"),new AdditionalProperties("key1","value1","name1")),
                List.of(
                        new InstanceArrangement(
                        "545",
                        "778",
                        "НСО",
                        null,
                        "555",
                        Instant.now(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                        )
                ,
                        new InstanceArrangement(
                                "546",
                                "111",
                                "НСО",
                                null,
                                "222",
                                Instant.now(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                                )
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(instanceRequest);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody InstanceRequest request){
        System.out.println(request);
        return instanceService.process(request);
    }

}
