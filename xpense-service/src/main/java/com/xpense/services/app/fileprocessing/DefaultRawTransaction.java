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
}
