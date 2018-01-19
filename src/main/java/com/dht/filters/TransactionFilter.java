package com.dht.filters;

import com.dht.utils.ConnectionContext;
import com.dht.utils.JDBCUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务处理过滤器
 * @author dht925nerd@126.com
 */
@WebFilter(filterName = "TransactionFilter", urlPatterns = "/*")
public class TransactionFilter implements Filter {

    public TransactionFilter() {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            ConnectionContext.getInstance().bind(connection);
            chain.doFilter(request, response);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpServletRequest req = (HttpServletRequest) request;
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        } finally{
            ConnectionContext.getInstance().remove();
            JDBCUtils.release(connection);
        }
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {}

}
