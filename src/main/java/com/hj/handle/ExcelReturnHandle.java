package com.hj.handle;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.hj.annotations.ExportExcel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

@Component
public class ExcelReturnHandle implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ExportExcel.class) != null;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws IOException, IllegalAccessException {
        //must set true
        modelAndViewContainer.setRequestHandled(true);
        if (!(o instanceof List<?>)) {
            throw new RuntimeException("the methods return type must be List");
        }
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        ExportExcel methodAnnotation = methodParameter.getMethodAnnotation(ExportExcel.class);
        //here to export excel
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(methodAnnotation.name(), "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        //the list to be export must not be empty!!!
        List<?> list = (List<?>) o;
        if (list.size() == 0) {
            throw new RuntimeException("the list to be export must not be empty!!!!");
        }
        Class<?> aClass = list.get(0).getClass();
        Type genericSuperclass = list.getClass().getGenericSuperclass();
        genericSuperclass.getClass();

        ExportParams exportParams = new ExportParams();
        if (!methodAnnotation.title().equals("")) {
            exportParams.setTitle(methodAnnotation.title());
        }
        if (methodAnnotation.headerHeight()!=0) {
            exportParams.setTitleHeight(methodAnnotation.headerHeight());
        }
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, aClass, list);
        sheets.write(outputStream);
        outputStream.close();
    }
}
