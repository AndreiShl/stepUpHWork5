package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.Agreement;

public interface AgreementRepo extends CrudRepository<Agreement, Integer> {
    Agreement findFirstByNumber(String number);
}
