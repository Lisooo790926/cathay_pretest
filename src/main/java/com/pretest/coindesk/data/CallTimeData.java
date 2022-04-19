package com.pretest.coindesk.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallTimeData {

    private Date updated;
    private Date updatedISO;
    private Date updateduk;
}
