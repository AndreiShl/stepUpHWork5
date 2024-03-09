package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.Account;

interface AccountRepo extends CrudRepository<Account, Integer> {
}
