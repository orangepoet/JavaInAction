package cn.orangepoet.annotationprocessing.processor.compatible;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * 多版本服务工厂
 */
public class ServiceFactory {
    private final VersionContext versionContext;

    private ServiceFactory(VersionContext versionContext) {
        this.versionContext = versionContext;
    }

    /**
     * 从ProcessorMap中查找适合当前版本的Processor并执行
     *
     * @param serviceMap
     * @return
     */
    public <TService> TService getService(Map<TService, ServiceVersion> serviceMap) {
        Optional<TService> service = serviceMap.entrySet()
                .stream()
                .filter(entry -> StringUtils.isNotBlank(entry.getValue().floor()))
                .sorted((entry1, entry2) -> -1 * versionContext.compare(entry1.getValue().floor(), entry2.getValue().floor()))
                .filter(entry -> versionContext.compare(versionContext.getCurrentVersion(), entry.getValue().floor()) >= 0)
                .map(Map.Entry::getKey)
                .findFirst();
        if (service.isPresent()) {
            return service.get();
        }
        throw new ServiceVersionNoMatchedException(String.format("target version [%s] not matched", versionContext.getCurrentVersion()) );
    }

}