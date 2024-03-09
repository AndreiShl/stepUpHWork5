package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "account_pool_id")
    private AccountPool accountPoolId;
    @Basic
    @Column(name = "account_number", length = 25)
    private String accountNumber;
    @Basic
    @Column(name = "bussy")
    private Boolean bussy;
}
