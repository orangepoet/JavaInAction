package cn.orangepoet.inaction.spi.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
@Slf4j
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
        List<Quote> quotes = new ArrayList<>();
        while (iterator.hasNext()) {
            QuoteManagerProvider next = iterator.next();

            log.info("provider type: {}", next.getClass().getSimpleName());
            QuoteManager manager = next.getManager();
            List<Quote> quotes0 = manager.getQuotes("baseCurrency", LocalDate.now());
            if (!CollectionUtils.isEmpty(quotes0)) {
                quotes.addAll(quotes0);
            }
        }
        return quotes;
    }
}
