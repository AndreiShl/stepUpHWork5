package ru.inno.task5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task5.model.AccountPool;

public interface AccountPoolRepo extends CrudRepository<AccountPool, Integer> {
    AccountPool getFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
            String branchCode,String currencyCode, String mdmCode, String priorityCode, String registryTypeCode );
}
