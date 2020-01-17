package cn.orangepoet.inaction.spi.autoservice;

import cn.orangepoet.inaction.spi.Quote;
import cn.orangepoet.inaction.spi.QuoteManager;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author chengzhi
 * @date 2020/01/17
 */
public class QuoteManagerImpl2 implements QuoteManager {
    @Override
    public List<Quote> getQuotes(String baseCurrency, LocalDate date) {
        return Collections.singletonList(new Quote("RMB", LocalDate.now()));
    }
}
