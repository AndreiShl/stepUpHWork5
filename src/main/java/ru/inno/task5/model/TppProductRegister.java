package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tpp_product_register", schema = "public", catalog = "hwork5")
@Data
public class TppProductRegister {

    @Id         @Column(name = "id", nullable = false) @GeneratedValue(strategy = GenerationType.IDENTITY)   private Integer id;
    @Basic      @Column(name = "product_id", nullable = true)                       private Long productId;
    @Basic      @Column(name = "type")                                              private String type;
    @Basic      @Column(name = "account", nullable = true)                          private Long account;
    @Basic      @Column(name = "currency_code", nullable = true, length = 30)       private String currencyCode;
    @Basic      @Column(name = "state", nullable = true, length = 50)               private String state;
    @Basic      @Column(name = "account_number", nullable = true, length = 25)      private String accountNumber;
}
