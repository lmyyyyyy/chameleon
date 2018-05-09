/*
package cn.code.chameleon.config;

import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

*/
/**
 * @author liumingyu
 * @create 2018-05-09 下午2:22
 *//*

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Setter
    private Boolean swaggerEnabled;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.code.chameleon.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(getApiInfo())
                .produces(newHashSet(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .globalResponseMessage(RequestMethod.PATCH, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("chameleon")
                .termsOfServiceUrl("www.chameleon.com")
                .contact(new Contact("liumingyu", "https://github.com/lmyyyyyy/chameleon", "532033837@qq.com"))
                .version("v0.1.0")
                .build();
    }

    private List<ResponseMessage> responseMessages() {

        Map<String, Header> headers = newHashMap();
        headers.put("Location", new Header("Location", "登录URL", new ModelRef("")));

        return newArrayList(
                new ResponseMessageBuilder()
                        .code(400)
                        .message("请求参数没填好")
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("未授权，需要重新登录")
                        .headersWithDescription(headers)
                        .build(),
                new ResponseMessageBuilder()
                        .code(403)
                        .message("无权限!")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("请求路径没有或页面跳转路径不对")
                        .build(),
                new ResponseMessageBuilder()
                        .code(500)
                        .message("服务器端错误")
                        .responseModel(new ModelRef("Errors"))
                        .build());
    }
}
*/
