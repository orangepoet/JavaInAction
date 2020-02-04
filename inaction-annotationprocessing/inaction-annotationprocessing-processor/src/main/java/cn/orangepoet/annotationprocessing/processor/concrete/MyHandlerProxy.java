package cn.orangepoet.annotationprocessing.processor.concrete;

import cn.orangepoet.annotationprocessing.processor.compatible.ServiceFactory;
import cn.orangepoet.annotationprocessing.processor.compatible.ServiceVersion;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class MyHandlerProxy implements MyHandler {

    private final Map<MyHandler, ServiceVersion> processorMap;

    public MyHandlerProxy(List<MyHandler> processors) {
        this.processorMap = getProcessorMap(processors);
    }

    private Map<MyHandler, ServiceVersion> getProcessorMap(List<MyHandler> myHandlers) {
        if (CollectionUtils.isEmpty(myHandlers)) {
            return Collections.emptyMap();
        }
        Map<MyHandler, ServiceVersion> result = new HashMap<>();
        myHandlers.forEach(processor -> {
            ServiceVersion serviceVersion = processor.getClass().getAnnotation(ServiceVersion.class);
            if (serviceVersion != null) {
                result.put(processor, serviceVersion);
            }
        });
        return result;
    }


    @Override
    public String getName() {
        MyHandler service = ServiceFactory.getService(processorMap);
        return service.getName();
    }

    @Override
    public void sayHi() {
        MyHandler service = ServiceFactory.getService(processorMap);
        service.sayHi();
    }
}
