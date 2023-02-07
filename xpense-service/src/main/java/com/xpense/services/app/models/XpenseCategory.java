package com.xpense.services.app.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="xpense_category")
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class XpenseCategory {
    @Id
    private Integer id;

    @Column(name = "fk_category")
    private Integer category;

    @Column(name = "sub_category")
    private String subCategory;
}