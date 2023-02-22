package com.xpense.services.app.fileprocessing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "hdfc")
public class HdfcRawTransaction extends DefaultRawTransaction {
    @Column(name="narration")
    private String narration;
    @Column(name="chqRefNo")
    private String chqRefNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HdfcRawTransaction)) return false;
        if (!super.equals(o)) return false;

        HdfcRawTransaction that = (HdfcRawTransaction) o;

        if (getNarration() != null ? !getNarration().equals(that.getNarration()) : that.getNarration() != null)
            return false;
        if (getChqRefNo() != null ? getChqRefNo().equals(that.getChqRefNo()) : that.getChqRefNo() == null)
            return false;
        if (getTransactionDate() != null ? !getTransactionDate().equals(that.getTransactionDate()) : that.getTransactionDate() != null)
            return false;
        if (getWithdrawalAmt() != null ? !getWithdrawalAmt().equals(that.getWithdrawalAmt()) : that.getWithdrawalAmt() != null)
            return false;
        if (getDepositAmt() != null ? !getDepositAmt().equals(that.getDepositAmt()) : that.getDepositAmt() != null)
            return false;
        return getClosingBalance() != null ? getClosingBalance().equals(that.getClosingBalance()) : that.getClosingBalance() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getNarration() != null ? getNarration().hashCode() : 0);
        result = 31 * result + (getChqRefNo() != null ? getChqRefNo().hashCode() : 0);
        result = 31 * result + (getTransactionDate() != null ? getTransactionDate().hashCode() : 0);
        result = 31 * result + (getWithdrawalAmt() != null ? getWithdrawalAmt().hashCode() : 0);
        result = 31 * result + (getDepositAmt() != null ? getDepositAmt().hashCode() : 0);
        result = 31 * result + (getClosingBalance() != null ? getClosingBalance().hashCode() : 0);
        return result;
    }
}
