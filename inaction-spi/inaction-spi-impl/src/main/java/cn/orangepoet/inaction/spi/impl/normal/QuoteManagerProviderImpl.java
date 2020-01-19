package cn.orangepoet.inaction.spi.impl.normal;

import cn.orangepoet.inaction.spi.api.QuoteManager;
import cn.orangepoet.inaction.spi.api.QuoteManagerProvider;

/**
 * @author chengzhi
 * @date 2019/10/11
 */
public class QuoteManagerProviderImpl implements QuoteManagerProvider {
    @Override
    public QuoteManager getManager() {
        return new QuoteManagerImpl();
    }
}
