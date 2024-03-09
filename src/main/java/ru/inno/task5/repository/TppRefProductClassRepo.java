package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.TppRefProductClass;

public interface TppRefProductClassRepo extends CrudRepository <TppRefProductClass,Integer> {
    TppRefProductClass getFirstByValue(String value);
}
