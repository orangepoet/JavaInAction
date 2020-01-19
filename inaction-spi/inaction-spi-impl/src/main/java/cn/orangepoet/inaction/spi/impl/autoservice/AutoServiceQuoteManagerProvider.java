package cn.orangepoet.inaction.spi.impl.autoservice;

import cn.orangepoet.inaction.spi.api.QuoteManager;
import cn.orangepoet.inaction.spi.api.QuoteManagerProvider;
import com.google.auto.service.AutoService;

/**
 * @author chengzhi
 * @date 2020/01/17
 */
@AutoService(QuoteManagerProvider.class)
public class AutoServiceQuoteManagerProvider implements QuoteManagerProvider {
    @Override
    public QuoteManager getManager() {
        return new QuoteManagerImpl2();
    }
}
