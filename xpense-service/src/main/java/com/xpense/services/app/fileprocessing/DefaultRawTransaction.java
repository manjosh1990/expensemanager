package com.xpense.services.app.fileprocessing;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="xpense_raw_transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="bank",
        discriminatorType = DiscriminatorType.STRING)
public class DefaultRawTransaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="transactionDate")
    private Date transactionDate;
    @Column(name="withdrawalAmt")
    private Double withdrawalAmt;
    @Column(name="depositAmt")
    private Double depositAmt;
    @Column(name="closingBalance")
    private Double closingBalance;
    @Column(name="fkSubXpenseCategory")
    private Integer fkSubXpenseCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultRawTransaction)) return false;

        DefaultRawTransaction that = (DefaultRawTransaction) o;

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
        int result = getTransactionDate() != null ? getTransactionDate().hashCode() : 0;
        result = 31 * result + (getWithdrawalAmt() != null ? getWithdrawalAmt().hashCode() : 0);
        result = 31 * result + (getDepositAmt() != null ? getDepositAmt().hashCode() : 0);
        result = 31 * result + (getClosingBalance() != null ? getClosingBalance().hashCode() : 0);
        return result;
    }
}
