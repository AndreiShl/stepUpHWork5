package ru.inno.task5.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigInteger;
import java.time.Instant;

public record InstanceArrangement(
        String generalAgreementId,
        String supplementaryAgreementId,
        @Pattern(regexp = "НСО,ЕЖО,СМО,ДБДС")
        String arrangementType, //TODO Enum, НСО/ЕЖО/СМО/ДБДС и тд, см. актуальную ЛМД
        Long shedulerJobId,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") String number,
        @NotNull(message = "{CorporateSettlementInstance.requiredParameter}") Instant openingDate,
        Instant closingDate,
        Instant cancelDate,
        Long validityDuration,
        String cancellationReason,
        String status,
        Instant interestCalculationDate,
        BigInteger interestRate,
        BigInteger coefficient,
        String coefficientAction,
        BigInteger minimumInterestRate,
        BigInteger minimumInterestRateCoefficient,
        String minimumInterestRateCoefficientAction,
        BigInteger maximalnterestRate,
        BigInteger maximalnterestRateCoefficient,
        String maximalnterestRateCoefficientAction
) {
}
