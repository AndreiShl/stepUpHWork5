package ru.inno.task5.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;


public record InstanceRequest(
        Integer instanceId,
//        @Pattern(regexp = "НСО|СМО|ЕЖО|ДБДС|договор")
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") String productType,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") String productCode,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") String registerType,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") String mdmCode,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") String contractNumber,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") Instant contractDate,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") Integer priority,
        Float interestRatePenalty,
        Float minimalBalance,
        Float thresholdAmount,
        String accountingDetails,
        @Pattern(regexp = "0|1")
        String rateType,
        Float taxPercentageRate,
        Float technicalOverdraftLimitAmount,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") Integer contractId,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") String branchCode,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}")
        @Size(min = 3, max = 3, message = "{CorporateSettlementInstance.currencyCode}")
        String isoCurrencyCode,
        @Pattern(regexp = "00", message = "") String urgencyCode,
        Integer referenceCode,
        List<AdditionalProperties> additionalProperties,
        List<InstanceArrangement> instanceArrangements
) {
}
