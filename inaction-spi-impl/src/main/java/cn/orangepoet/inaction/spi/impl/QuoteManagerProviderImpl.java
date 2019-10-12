package cn.orangepoet.inaction.spi.impl;

import cn.orangepoet.inaction.spi.QuoteManager;
import cn.orangepoet.inaction.spi.QuoteManagerProvider;

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
