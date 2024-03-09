package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.TppRefAccountType;

public interface TppRefAccountTypeRepo extends CrudRepository<TppRefAccountType, Integer> {
    TppRefAccountType getFirstByValue(String value);
}
