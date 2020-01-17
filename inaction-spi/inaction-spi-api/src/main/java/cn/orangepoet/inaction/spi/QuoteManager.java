package cn.orangepoet.inaction.spi;

import java.time.LocalDate;
import java.util.List;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
public interface QuoteManager {
    List<Quote> getQuotes(String baseCurrency, LocalDate date);
}
