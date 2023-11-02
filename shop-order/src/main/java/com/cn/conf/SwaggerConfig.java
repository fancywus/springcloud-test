package com.cn.conf;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

// 开启swagger文档
@EnableSwagger2
@SpringBootConfiguration
@Data
public class SwaggerConfig {

    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        // 创建参数构建器
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> params = new ArrayList<>();
        // 构建请求头文档
        Parameter build = parameterBuilder
                // -1.参数名
                .name("gatewayKey")
                // 描述
                .description("网关的认证后传递的统一请求头")
                // 数据引用类型
                .modelRef(new ModelRef("string"))
                // 参数类型
                .parameterType("header")
                // 参数默认值
                .defaultValue("key")
                // 该参数是否必传
                .required(true)
                // -2.参数名
                .name("origin")
                // 描述
                .description("网关的认证后传递的统一请求头，用于sentinel做白名单服务")
                // 数据引用类型
                .modelRef(new ModelRef("string"))
                // 参数类型
                .parameterType("header")
                // 参数默认值
                .defaultValue("fancywu")
                // 该参数是否必传
                .required(true)
                .build();
        params.add(build);

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerProperties.getEnabled())
                .apiInfo(apiInfo())
                .host(swaggerProperties.getHost())
                .select() //通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.cn.controller")) //指定扫描的包名， 如果不指定会扫描整个项目
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().description(swaggerProperties.getDesc())
                .title(swaggerProperties.getTitle())
                .version(swaggerProperties.getVersion())
                .build();
    }
}
