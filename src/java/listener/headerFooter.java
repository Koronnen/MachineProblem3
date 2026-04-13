package listener;

import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class headerFooter implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        context.setAttribute("date", new Date());
        
        context.setAttribute("header", "/WEB-INF/include/header.jsp");
        context.setAttribute("footer", "/WEB-INF/include/footer.jsp");
        
        System.out.println("Header, Footer, and Date Loaded");
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context Destroyed");
    }
}
