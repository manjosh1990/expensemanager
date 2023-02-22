package com.xpense.services.app.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="xpense_category")
@Data
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
