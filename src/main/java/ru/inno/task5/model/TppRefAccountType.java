package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "tpp_ref_account_type", schema = "public", catalog = "hwork5")
@Data
public class TppRefAccountType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id", nullable = false)
    private Integer internalId;
    @Basic
    @Column(name = "value", nullable = false, length = 100)
    private String value;
}
