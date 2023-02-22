package com.xpense.services.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="desc_category_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescCategoryMapping {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "search_string")
    private String searchString;
    @Column(name="fkSubXpenseCategory")
    private Integer fkSubCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescCategoryMapping)) return false;

        DescCategoryMapping that = (DescCategoryMapping) o;

        if (getSearchString() != null ? !getSearchString().equals(that.getSearchString()) : that.getSearchString() != null)
            return false;
        return getFkSubCategory() != null ? getFkSubCategory().equals(that.getFkSubCategory()) : that.getFkSubCategory() == null;
    }

    @Override
    public int hashCode() {
        int result = getSearchString() != null ? getSearchString().hashCode() : 0;
        result = 31 * result + (getFkSubCategory() != null ? getFkSubCategory().hashCode() : 0);
        return result;
    }
}
