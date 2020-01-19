package cn.orangepoet.inaction.spi.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
@Data
@AllArgsConstructor
public class Quote {
    private String currency;
    private LocalDate date;
}
