import cn.orangepoet.inaction.spi.ExchangeQuote;
import cn.orangepoet.inaction.spi.Quote;

import java.util.List;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
public class Application {
    public static void main(String[] args) {
        ExchangeQuote exchangeQuote = new ExchangeQuote();
        List<Quote> quotes = exchangeQuote.getQuotes(true);
        System.out.println(quotes);
    }
}
