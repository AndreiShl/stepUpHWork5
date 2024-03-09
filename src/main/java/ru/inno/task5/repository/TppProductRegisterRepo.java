package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.TppProductRegister;

public interface TppProductRegisterRepo extends CrudRepository<TppProductRegister, Integer> {
    TppProductRegister getFirstByProductIdAndType(long productId, String type);
}
