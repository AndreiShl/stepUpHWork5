package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.TppRefProductRegisterType;

import java.util.List;

public interface TppRefProductRegisterTypeRepo extends CrudRepository<TppRefProductRegisterType, Integer> {
    TppRefProductRegisterType getFirstByAccountType(String accountType);
    List<TppRefProductRegisterType> getAllByProductClassCodeAndAccountType
            (String productClass, String accountType);

}
