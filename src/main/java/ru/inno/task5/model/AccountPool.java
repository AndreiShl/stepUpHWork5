package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "account_pool", schema = "public", catalog = "hwork5")
@Data
public class AccountPool {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "branch_code", nullable = true, length = 50)
    private String branchCode;
    @Basic
    @Column(name = "currency_code", nullable = true, length = 30)
    private String currencyCode;
    @Basic
    @Column(name = "mdm_code", nullable = true, length = 50)
    private String mdmCode;
    @Basic
    @Column(name = "priority_code", nullable = true, length = 30)
    private String priorityCode;
    @Basic
    @Column(name = "registry_type_code", nullable = true, length = 50)
    private String registryTypeCode;
}
