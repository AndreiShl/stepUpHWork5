package ru.inno.task5.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "tpp_ref_product_class", schema = "public", catalog = "hwork5")
@Data
public class TppRefProductClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id", nullable = false)
    private Integer internalId;
    @Basic
    @Column(name = "value", nullable = false, length = 100)
    private String value;
    @Basic
    @Column(name = "gbi_code", length = 50)
    private String gbiCode;
    @Basic
    @Column(name = "gbi_name", length = 100)
    private String gbiName;
    @Basic
    @Column(name = "product_row_code", nullable = true, length = 50)
    private String productRowCode;
    @Basic
    @Column(name = "product_row_name", nullable = true, length = 100)
    private String productRowName;
    @Basic
    @Column(name = "subclass_code", nullable = true, length = 50)
    private String subclassCode;
    @Basic
    @Column(name = "subclass_name", nullable = true, length = 100)
    private String subclassName;
}
