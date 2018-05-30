package cn.code.chameleon;

import cn.code.chameleon.interceptor.LoginInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
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

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author liumingyu
 * @create 2018-05-03 下午4:13
 */
@EnableSwagger2
@EnableCaching
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableRedisHttpSession
@EnableTransactionManagement
@ConfigurationProperties(prefix = "app")
@MapperScan({"cn.code.chameleon.mapper", "cn.code.chameleon.monitor.mapper"})
@ComponentScan({"cn.code.chameleon.controller", "cn.code.chameleon.service", "cn.code.chameleon.monitor", "cn.code.chameleon.quartz", "cn.code.chameleon.model", "cn.code.chameleon.registry", "cn.code.chameleon.interceptor"})
@EntityScan({"cn.code.chameleon.pojo", "cn.code.chameleon.monitor.pojo"})
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    private static String LANGUAGE = "language";

    @Setter
    private Boolean swaggerEnabled;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println(
                "                           _ooOoo_\n" +
                        "                          o8888888o\n" +
                        "                          88\" . \"88\n" +
                        "                          (| -_- |)\n" +
                        "                          O\\  =  /O\n" +
                        "                       ____/`---'\\____\n" +
                        "                     .'  \\\\|     |//  `.\n" +
                        "                    /  \\\\|||  :  |||//  \\\n" +
                        "                   /  _||||| -:- |||||-  \\\n" +
                        "                   |   | \\\\\\  -  /// |   |\n" +
                        "                   | \\_|  ''\\---/''  |   |\n" +
                        "                   \\  .-\\__  `-`  ___/-. /\n" +
                        "                 ___`. .'  /--.--\\  `. . __\n" +
                        "              .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n" +
                        "             | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                        "             \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n" +
                        "        ======`-.____`-.___\\_____/___.-`____.-'======\n" +
                        "                           `=---='\n" +
                        "        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                        "                     佛祖保佑       永无BUG");
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return configurableEmbeddedServletContainer -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/static/404.html");
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/static/401.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/500.html");
            configurableEmbeddedServletContainer.addErrorPages(error401Page, error404Page, error500Page);
        };
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DruidDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * cookie区域解析器
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Locale.CHINA);
        slr.setCookieMaxAge(3600);
        slr.setCookieName(LANGUAGE);
        return slr;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
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

    @Bean(name="schedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /**
     * 在quartz.properties中的属性被读取并注入后再初始化对象
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("config/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * quartz初始化监听器
     *
     * @return
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /**
     * 通过SchedulerFactoryBean获取Scheduler的实例
     *
     * @return
     * @throws IOException
     */
    @Bean(name="scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/**");
    }

}
