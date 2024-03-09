package ru.inno.task5.service;

import jakarta.validation.constraints.*;

public record AccountRequest(
        @NotNull(message = "{CorporateSettlementAccount.instanceId}")
        Integer instanceId,
        String registryTypeCode,
        String accountType,
        @Size(min = 3, max = 3, message = "{CorporateSettlementAccount.currencyCode}")
        String currencyCode,
        String branchCode,

        @Pattern(regexp = "00")
        String priorityCode,
        String mdmCode,
        String clientCode,
        @Pattern(regexp = "РЖД|ФПК")
        String trainRegion,
        String counter,
        String salesCode
) {
    public AccountRequest(@NotNull Integer instanceId) {
        this(instanceId, null, null, null, null, null, null, null, null, null, null);
    }
}
