package swagger;
import static swagger.Swagger2.CONF;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taole.member.utils.ReturnCodeDefine;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration(value = CONF)
@EnableSwagger2
public class Swagger2 {
    public static final String CONF = "Swagger2-conf";
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public Docket createRestApi() {
        return builderDocket(builderDocket());
    }

    private String initContextInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("会员管理程序。");
        return sb.toString();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("会员管理系统").description(initContextInfo())
                .contact(new Contact("", "", ""))
                .version("V1.0")
                .build();
    }

    public Docket createRestApi4User() {
        return builderDocket(builderDocket());
    }

    public Docket builderDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    public Docket builderDocket(Docket docket) {
        List<ResponseMessage> builders = new ArrayList<ResponseMessage>();
        List<Object> codes = new CBP<Object>().norm();
        List<ResponseMessage> list = new ArrayList<ResponseMessage>();
        try {
            codesFor:
            {
                for (int i = codes.size() - 1; i >= 0; i--) {
                    Object o = codes.get(i);
                    Field[] fields = o.getClass().getFields();
                    for (int i1 = fields.length - 1; i1 >= 0; i1--) {
                        Field field = fields[i1];
                        field.setAccessible(true);
                        String[] strings = (String[]) field.get(o);
                        builders.add(new ResponseMessageBuilder()
                                .code(Integer.parseInt(strings[0]))
                                .message(strings[1])
                                .responseModel(new ModelRef(null))
                                .build());
                    }
                }
            }
            docketCodeBuilder:
            {
                docket.useDefaultResponseMessages(false);
                return docket.globalResponseMessage(RequestMethod.GET,
                		list).globalResponseMessage(RequestMethod.POST,
                		list).globalResponseMessage(RequestMethod.DELETE,
                		list).globalResponseMessage(RequestMethod.PUT,
                		list);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公共code构建、过滤
     * CodeBuilderProtocol
     *
     * @return
     */
    static class CBP<T> {
        List norm(T t, Filter c) {
            return c.filter(norm(), t);
        }

        List norm(Filter c) {
            return c.filter(norm(), null);
        }

        List norm() {
            return getCodes();
        }

        private List getCodes() {
            return new myCodes().addV2(new ReturnCodeDefine());
        }

        class myCodes extends ArrayList {
            public myCodes addV2(Object o) {
                super.add(o);
                return this;
            }
        }
        interface Filter<T> {
            List filter(List list, T t);
        }
    }

}