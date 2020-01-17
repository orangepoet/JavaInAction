package cn.orangepoet.inaction.spi.autoservice;

import cn.orangepoet.inaction.spi.QuoteManager;
import cn.orangepoet.inaction.spi.QuoteManagerProvider;
import com.google.auto.service.AutoService;

/**
 * @author chengzhi
 * @date 2020/01/17
 */
@AutoService(QuoteManagerProvider.class)
public class AutoServiceExchangeQuote implements QuoteManagerProvider {
    @Override
    public QuoteManager getManager() {
        return new QuoteManagerImpl2();
    }
}
