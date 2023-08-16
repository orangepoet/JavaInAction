package cn.orangepoet.inaction.spi.client;

import cn.orangepoet.inaction.spi.api.ExchangeQuote;
import cn.orangepoet.inaction.spi.api.Quote;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("start...");
        ExchangeQuote exchangeQuote = new ExchangeQuote();
        List<Quote> quotes = exchangeQuote.getQuotes(true);
        log.info("quotes: " + quotes);
    }
}
