package servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.taole.framework.service.GenerateLocalReturnCode;
import com.taole.framework.service.GenerateLocalReturnCode.NORM;

/**
 *<p>自动生成返回码页面</p>
 *<p>Company: doctorplus1</p>
 * @author zhangqianlong
 * @date 2017年11月13日下午5:48:53  
 */
public class GenerateReturnCodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Value(value = "#{configProperties['generate.return.code.path']}")
    private String generateReturnCodePath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateReturnCodeServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            WebApplicationContextUtils
                    .getWebApplicationContext(getServletContext())
                    .getAutowireCapableBeanFactory().autowireBean(this);
            
            String path = config.getServletContext().getRealPath("/");
            String xmlPath = path + File.separator + "WEB-INF" + File.separator + "service-servlet.xml";
            
            new GenerateLocalReturnCode<String, String>(xmlPath, generateReturnCodePath, new NORM<String, String>() {
                public String call(String pm) {
                    return strategy(pm);
                }
                
            }).generateReturnCode();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {

        }
    }

    /**
     * 通过service-servlet.xml中的component-scan，结合写死的包名，分析返回码类所在的全路径
     *
     * @param pg 在service-servlet.xml中配置的component-scan
     * @return 获取到返回码的类名全路径
     */
    private String strategy(String pg) {
        return pg.split("service")[0] + "utils.ReturnCodeDefine";
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
