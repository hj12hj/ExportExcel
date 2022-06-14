package com.hj.handle;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.hj.annotations.ExportExcelByTemplate;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 通过模板导出excel
 */
@Component
public class ExcelByTemplateReturnHandle implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ExportExcelByTemplate.class) != null;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws IOException, IllegalAccessException {
        //must set true
        modelAndViewContainer.setRequestHandled(true);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        if (!(o instanceof Map)){
            throw new RuntimeException("the return type must be Map");
        }

        ExportExcelByTemplate methodAnnotation = methodParameter.getMethodAnnotation(ExportExcelByTemplate.class);
        String templatePath = methodAnnotation.templatePath();
        String name = methodAnnotation.name();

        //easy poi to export
        TemplateExportParams params = new TemplateExportParams(templatePath);
        if (params!=null){
            Map<String,Object> map = (Map<String,Object>)o;
            Workbook workbook = ExcelExportUtil.exportExcel(params,map);
            export(response, workbook, URLEncoder.encode(name + ".xls", "UTF-8"));
        }
    }

    public  void export(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream());
        workbook.write(bufferedOutPut);
        bufferedOutPut.flush();
        bufferedOutPut.close();
    }
}
