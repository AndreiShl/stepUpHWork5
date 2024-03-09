package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Data
public class Agreement {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private TppProduct productId;
    @Basic
    @Column(name = "general_agreement_id", nullable = true, length = 50)
    private String generalAgreementId;
    @Basic
    @Column(name = "supplementary_agreement_id", nullable = true, length = 50)
    private String supplementaryAgreementId;
    @Basic
    @Column(name = "arrangement_type", nullable = true, length = 50)
    private String arrangementType;
    @Basic
    @Column(name = "sheduler_job_id", nullable = true)
    private Long shedulerJobId;
    @Basic
    @Column(name = "number", nullable = true, length = 50)
    private String number;
    @Basic
    @Column(name = "opening_date", nullable = true)
    private Timestamp openingDate;
    @Basic
    @Column(name = "closing_date", nullable = true)
    private Timestamp closingDate;
    @Basic
    @Column(name = "cancel_date", nullable = true)
    private Timestamp cancelDate;
    @Basic
    @Column(name = "validity_duration", nullable = true)
    private Long validityDuration;
    @Basic
    @Column(name = "cancellation_reason", nullable = true, length = 100)
    private String cancellationReason;
    @Basic
    @Column(name = "status", nullable = true, length = 50)
    private String status;
    @Basic
    @Column(name = "interest_calculation_date", nullable = true)
    private Timestamp interestCalculationDate;
    @Basic
    @Column(name = "interest_rate", nullable = true, precision = 0)
    private BigInteger interestRate;
    @Basic
    @Column(name = "coefficient", nullable = true, precision = 0)
    private BigInteger coefficient;
    @Basic
    @Column(name = "coefficient_action", nullable = true, length = 50)
    private String coefficientAction;
    @Basic
    @Column(name = "minimum_interest_rate", nullable = true, precision = 0)
    private BigInteger minimumInterestRate;
    @Basic
    @Column(name = "minimum_interest_rate_coefficient", nullable = true, precision = 0)
    private BigInteger minimumInterestRateCoefficient;
    @Basic
    @Column(name = "minimum_interest_rate_coefficient_action", nullable = true, length = 50)
    private String minimumInterestRateCoefficientAction;
    @Basic
    @Column(name = "maximal_interest_rate", nullable = true, precision = 0)
    private BigInteger maximalInterestRate;
    @Basic
    @Column(name = "maximal_interest_rate_coefficient", nullable = true, precision = 0)
    private BigInteger maximalInterestRateCoefficient;
    @Basic
    @Column(name = "maximal_interest_rate_coefficient_action", nullable = true, length = 50)
    private String maximalInterestRateCoefficientAction;
}
