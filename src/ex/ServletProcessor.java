package ex;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor {

    public void process(Request request,Response response) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(Constants.WEB_ROOT);
        String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
        urls[0] = new URL(null, repository, streamHandler); //第三个参数
        loader = new URLClassLoader(urls);

        Class<?> className = loader.loadClass(servletName);
        Servlet servlet = (Servlet)className.newInstance();
        servlet.service((HttpServletRequest)request,(HttpServletResponse)response);
    }
}
