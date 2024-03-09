package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "tpp_ref_product_register_type", schema = "public", catalog = "hwork5")
@Data
public class TppRefProductRegisterType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id", nullable = false)
    private Integer internalId;

    @Basic
    @Column(name = "value", nullable = false, length = 100)
    private String value;

    @Basic
    @Column(name = "register_type_name", nullable = false, length = 100)
    private String registerTypeName;

    @Basic
    @Column(name = "product_class_code", nullable = false)
    private String productClassCode;

    @Basic
    @Column(name = "register_type_start_date", nullable = true)
    private Timestamp registerTypeStartDate;

    @Basic
    @Column(name = "register_type_end_date", nullable = true)
    private Timestamp registerTypeEndDate;

    @Basic
    @Column(name = "account_type" , nullable = false)
    private String accountType;
}
