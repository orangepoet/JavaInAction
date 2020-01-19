package cn.orangepoet.inaction.spi.impl.normal;

import cn.orangepoet.inaction.spi.api.Quote;
import cn.orangepoet.inaction.spi.api.QuoteManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
public class QuoteManagerImpl implements QuoteManager {
    @Override
    public List<Quote> getQuotes(String baseCurrency, LocalDate date) {
        return Arrays.asList(new Quote(baseCurrency, date));
    }
}
