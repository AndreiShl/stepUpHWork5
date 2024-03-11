package ru.inno.task5.service;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.task5.model.TppProductRegister;
import ru.inno.task5.model.TppRefProductRegisterType;
import ru.inno.task5.repository.AccountPoolRepo;
import ru.inno.task5.repository.TppProductRegisterRepo;
import ru.inno.task5.repository.TppRefAccountTypeRepo;
import ru.inno.task5.repository.TppRefProductRegisterTypeRepo;

@Service
public class AccountService {
    @Autowired
    TppProductRegisterRepo tppProductRegisterRepo;
    @Autowired
    TppRefProductRegisterTypeRepo tppRefProductRegisterTypeRepo;
    @Autowired
    AccountPoolRepo accountPoolRepo;
    @Autowired
    TppRefAccountTypeRepo tppRefAccountTypeRepo;

    @Transactional
    public ResponseEntity<?> createAccount(AccountRequest request) {
        var tppProd = tppProductRegisterRepo.getFirstByProductIdAndType(request.instanceId(), request.registryTypeCode());
        if (tppProd != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            String.format("Параметр registryTypeCode тип регистра %s уже существует для ЭП с ИД  %d.",
                                    request.registryTypeCode(),
                                    request.instanceId())
                    );
        }

        var type = tppRefProductRegisterTypeRepo.getFirstByAccountType(request.accountType()
        );

        var annotation = TppRefProductRegisterType.class.getAnnotation(Table.class);
        if (type == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            String.format("Код Продукта %s не найдено в Каталоге продуктов " +
                                            "<%s.%s> для данного типа Регистра",
                                    request.accountType(),
                                    annotation.schema(),
                                    annotation.name()
                            )
                    );
        }

        var pool = accountPoolRepo.getFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
                request.branchCode(),
                request.currencyCode(),
                request.mdmCode(),
                request.priorityCode(),
                request.registryTypeCode());

        if (pool == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Account pool не найден");
        }

        TppProductRegister tppProductRegister = new TppProductRegister();
        tppProductRegister.setProductId(request.instanceId().longValue());
        tppProductRegister.setType(type.getValue());
        tppProductRegister.setAccount(pool.getId().longValue());
        tppProductRegister.setCurrencyCode(request.currencyCode());
        tppProductRegister.setState("открыт");

        var tppProductRegisterSaved = tppProductRegisterRepo.save(tppProductRegister);

        return ResponseEntity.status(HttpStatus.CREATED).body(tppProductRegisterSaved.getId());
    }
}
