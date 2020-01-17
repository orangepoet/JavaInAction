package cn.orangepoet.inaction.spi;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
public class ExchangeQuote {
    private final ServiceLoader<QuoteManagerProvider> serviceLoader;

    public ExchangeQuote() {
        serviceLoader = ServiceLoader.load(QuoteManagerProvider.class);
    }

    public List<Quote> getQuotes(boolean needRefresh) {
        if (needRefresh) {
            serviceLoader.reload();
        }

        Iterator<QuoteManagerProvider> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            QuoteManagerProvider next = iterator.next();
            QuoteManager manager = next.getManager();
            List<Quote> quotes = manager.getQuotes("baseCurrency", LocalDate.now());
            return quotes;
        }
        return null;
    }
}
