package cn.orangepoet.inaction.spi.impl.autoservice;

import cn.orangepoet.inaction.spi.api.Quote;
import cn.orangepoet.inaction.spi.api.QuoteManager;

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
        return Collections.singletonList(new Quote(baseCurrency, LocalDate.now()));
    }
}
