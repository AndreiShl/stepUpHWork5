package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.TppProduct;

public interface TppProductRepo extends CrudRepository<TppProduct, Integer> {
    TppProduct findFirstByNumber(String number);
}
