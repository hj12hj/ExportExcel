package com.hj.config;

import com.hj.handle.ExcelByTemplateReturnHandle;
import com.hj.handle.ExcelReturnHandle;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * InjectMvcReturnHandle to solve excel
 */
public class InjectMvcReturnHandle  implements InitializingBean  {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> oldHandlerMethodReturnValueHandlers=requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newHandlerMethodReturnValueHandlers=new ArrayList<>(oldHandlerMethodReturnValueHandlers.size());
        newHandlerMethodReturnValueHandlers.add(new ExcelReturnHandle());
        newHandlerMethodReturnValueHandlers.add(new ExcelByTemplateReturnHandle());
        newHandlerMethodReturnValueHandlers.addAll(oldHandlerMethodReturnValueHandlers);
        requestMappingHandlerAdapter.setReturnValueHandlers(newHandlerMethodReturnValueHandlers);
    }


}
