package com.dht.servlet;

import com.dht.entity.User;
import com.dht.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户业务逻辑
 * @author dht925nerd@126.com
 */
@WebServlet(name = "UserServlet", urlPatterns = "/userServlet")
public class UserServlet extends HttpServlet {

    private static final String PAGE_PATH = "/WEB-INF/views/";
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        User user = userService.getUserWithTrades(username);

        if(user == null){
            response.sendRedirect(request.getServletPath() + "/error.jsp");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher(PAGE_PATH + "trades.jsp").forward(request, response);
    }
}
