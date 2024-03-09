package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "tpp_template_register_balance", schema = "public", catalog = "hwork5")
@Data
public class TppTemplateRegisterBalance {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "register_id", nullable = true)
    private Long registerId;
    @Basic
    @Column(name = "amount", nullable = true, precision = 0)
    private BigInteger amount;
    @Basic
    @Column(name = "order", nullable = true, length = 50)
    private String order;
    @Basic
    @Column(name = "last_modify_date", nullable = true)
    private Timestamp lastModifyDate;
}
