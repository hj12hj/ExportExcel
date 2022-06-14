package com.hj.config;

import org.springframework.context.annotation.Bean;

public class AutoExportExcelConfig {

    @Bean
    InjectMvcReturnHandle getInjectMvcReturnHandle(){
        return new InjectMvcReturnHandle();
    }

}
