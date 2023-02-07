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


}
