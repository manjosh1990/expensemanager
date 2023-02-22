package com.xpense.services.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "xpense_transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class XpenseTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;

    private String description;
    @Column(name = "fk_xpense_category")
    private Integer xpenseCategory;
    private Double debit;
    private Double credit;

    private String type;
    @Column(name = "fk_accounts")
    private Integer fkAccounts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getXpenseCategory() {
        return xpenseCategory;
    }

    public void setXpenseCategory(Integer xpenseCategory) {
        this.xpenseCategory = xpenseCategory;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Integer getFkAccounts() {
        return fkAccounts;
    }

    public void setFkAccounts(Integer fkAccounts) {
        this.fkAccounts = fkAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XpenseTransactions)) return false;

        XpenseTransactions that = (XpenseTransactions) o;

        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getXpenseCategory() != null ? !getXpenseCategory().equals(that.getXpenseCategory()) : that.getXpenseCategory() != null)
            return false;
        if (getDebit() != null ? !getDebit().equals(that.getDebit()) : that.getDebit() != null) return false;
        if (getCredit() != null ? !getCredit().equals(that.getCredit()) : that.getCredit() != null) return false;
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        return getFkAccounts() != null ? getFkAccounts().equals(that.getFkAccounts()) : that.getFkAccounts() == null;
    }

    @Override
    public int hashCode() {
        int result = getDate() != null ? getDate().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getXpenseCategory() != null ? getXpenseCategory().hashCode() : 0);
        result = 31 * result + (getDebit() != null ? getDebit().hashCode() : 0);
        result = 31 * result + (getCredit() != null ? getCredit().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getFkAccounts() != null ? getFkAccounts().hashCode() : 0);
        return result;
    }
}
