package com.dht.servlet;

import com.dht.entity.*;
import com.dht.service.AccountService;
import com.dht.service.BookService;
import com.dht.service.UserService;
import com.dht.utils.BookStoreWebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 书籍业务逻辑
 * @author dht925nerd@126.com
 */
@WebServlet(name = "BookServlet", urlPatterns = "*.do")
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookService();
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();
    private static final String PAGE_PATH = "/WEB-INF/views/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0, methodName.length() - 3);

        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * <h3>获得书籍列表</h3>
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNoStr = request.getParameter("pageNo");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");

        int pageNo = 1;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        try {
            pageNo = Integer.parseInt(pageNoStr);
        }catch (NumberFormatException e){}
        try {
            minPrice = Integer.parseInt(minPriceStr);
        }catch (NumberFormatException e){}
        try{
            maxPrice = Integer.parseInt(maxPriceStr);
        }catch (NumberFormatException e){}

        CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
        Page<Book> page = bookService.getPage(criteriaBook);

        request.setAttribute("bookPage", page);

        request.getRequestDispatcher(PAGE_PATH + "books.jsp").forward(request, response);
    }

    /**
     * <h3>获取书籍详细信息</h3>
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void getBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        if (bookId <= 0){
            response.sendRedirect("error.jsp");
        }

        Book book = bookService.getBook(bookId);
        if (book == null){
            response.sendRedirect("error.jsp");
        }

        request.setAttribute("book", book);
        request.setAttribute("pageNo", pageNo);
        request.getRequestDispatcher(PAGE_PATH + "book.jsp").forward(request, response);
    }

    /**
     * 添加商品到购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        boolean flag = false;

        if(id > 0){
            ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);

            flag = bookService.addToCart(id, sc);
        }

        if(flag){
            getBooks(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/error.jsp");
    }

    /**
     * 重定向跳转到指定页面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        request.getRequestDispatcher(PAGE_PATH + page + ".jsp").forward(request, response);
    }

    /**
     * 从购物车中删除指定商品信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        if (id <= 0) {
            response.sendRedirect("error.jsp");
        }

        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        bookService.removeItemFromShoppingCart(sc, id);

        if(sc.isEmpty()){
            request.getRequestDispatcher(PAGE_PATH + "emptyCart.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher(PAGE_PATH + "cart.jsp").forward(request, response);
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        bookService.clearShoppingCart(sc);

        request.getRequestDispatcher(PAGE_PATH + "emptyCart.jsp").forward(request, response);
    }

    /**
     * 利用 Ajax 异步更新购物车页面的信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String quantityStr = request.getParameter("quantity");

        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);

        int id = -1;
        int quantity = -1;

        try {
            id = Integer.parseInt(idStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (Exception e) {
        }

        if (id > 0 && quantity > 0) {
            bookService.updateItemQuantity(sc, id, quantity);
        }

        Map<String, Object> result = new HashMap<>(16);
        result.put("bookNumber", sc.getBookNumber());
        result.put("totalMoney", sc.getTotalMoney());

        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        response.setContentType("text/javascript");
        response.getWriter().print(jsonStr);
    }

    /**
     * 校验账户信息表单
     * @param username
     * @param accountId
     * @return
     */
    private StringBuffer validateFormField(String username, String accountId) {
        StringBuffer errors = new StringBuffer("");

        if (username == null || username.trim().equals("")) {
            errors.append("用户名不能为空<br>");
        }

        if (accountId == null || accountId.trim().equals("")) {
            errors.append("账户不能为空");
        }

        return errors;
    }

    /**
     * 校验用户是否存在
     * @param username
     * @param accountId
     * @return
     */
    private StringBuffer validateUser(String username, String accountId){
        boolean flag = false;
        User user = userService.getUserByUserName(username);
        if(user != null){
            int accountId2 = user.getAccountId();
            if(accountId.trim().equals("" + accountId2)){
                flag = true;
            }
        }

        StringBuffer errors2 = new StringBuffer("");
        if(!flag){
            errors2.append("该用户不存在!");
        }

        return errors2;
    }

    /**
     * 校验书籍库存
     * @param request
     * @return
     */
    private StringBuffer validateBookStoreNumber(HttpServletRequest request){

        StringBuffer errors = new StringBuffer("");
        ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);

        for(ShoppingCartItem sci: cart.getItems()){
            int quantity = sci.getQuantity();
            int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();

            if(quantity > storeNumber){
                errors.append("《" + sci.getBook().getTitle() + "》" + "库存不足!<br>");
            }
        }

        return errors;
    }

    /**
     * 校验账户余额
     * @param request
     * @param accountId
     * @return
     */
    private StringBuffer validateBalance(HttpServletRequest request, String accountId){

        StringBuffer errors = new StringBuffer("");
        ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);

        Account account = accountService.getAccount(Integer.parseInt(accountId));
        if(cart.getTotalMoney() > account.getBalance()){
            errors.append("余额不足!");
        }

        return errors;
    }

    /**
     * 结账
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String accountId = request.getParameter("accountId");

        StringBuffer errors = validateFormField(username, accountId);

        //进行用户及账户信息的校验
        if(errors.toString().equals("")){
            errors = validateUser(username, accountId);
            if(errors.toString().equals("")){
                errors = validateBookStoreNumber(request);
                if(errors.toString().equals("")){
                    errors = validateBalance(request, accountId);
                }
            }
        }

        if(!errors.toString().equals("")){
            request.setAttribute("errors", errors);
            request.getRequestDispatcher( PAGE_PATH +"cash.jsp").forward(request, response);
            return;
        }

        bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId);
        response.sendRedirect(request.getContextPath() + "/success.jsp");
    }
}
