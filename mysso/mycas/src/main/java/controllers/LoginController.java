package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cas.server.Constants;
import database.DB;
import domains.ServiceTicket;
import domains.User;

@WebServlet(value="/login.do")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String LOCAL_SERVICE=request.getParameter("LOCAL_SERVICE");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				//验证是否已经登录过CAS
				if (cookie.getName().equals(Constants.CAS_TGS)) {
					System.out.println("曾经登录过");
					// 为简化设计TGS=ST
					String CAS_TGS = cookie.getValue();
					String CAS_ST = CAS_TGS;
					ServiceTicket st = DB.findServiceTicketbySt(CAS_ST);
					//如果已经登录过,通过验证st,来验证登录是否还有效
					//即实现了在CAS的main页面跳转到其他系统的功能
					//只要CAS注销,所有的系统都不能再登录
					if (st != null) {
						System.out.println("登录仍有效,st= "+st);
						if(LOCAL_SERVICE!=null&& !LOCAL_SERVICE.equals("")) {
						response.sendRedirect(LOCAL_SERVICE + "?"
								+ Constants.CAS_ST + "=" + CAS_ST + "&"
								+ Constants.LOCAL_SERVICE + "=" + LOCAL_SERVICE);
						return;
						}
						else {
							System.out.println("未指定服务,,,main,,"+request.getContextPath());
							request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
							return;
						}
					}
					// 有cookie
				}
			}
		}
		request.setAttribute(Constants.LOCAL_SERVICE, LOCAL_SERVICE);
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String id=request.getParameter("id");	
	String pwd=request.getParameter("pwd");	
	HttpSession session = request.getSession();
	String LOCAL_SERVICE=request.getParameter("LOCAL_SERVICE");	

	System.out.println("Local_serve:"+LOCAL_SERVICE);
	session.setAttribute("id", id);
	session.setAttribute("pwd", pwd);
	User user =DB.findUser(id,pwd);
	if (user != null) {
		System.out.println("密码正确");
		session.setAttribute("user", user);
		String CAS_ST = user.getId() + System.nanoTime();
		// 为简化设计TGS=ST
		Cookie cookie = new Cookie(Constants.CAS_TGS, CAS_ST);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		DB.addServiceTicket(user,CAS_ST);
		if (LOCAL_SERVICE != null && !LOCAL_SERVICE.equals("")) {
			response.sendRedirect(LOCAL_SERVICE + "?"
					+ Constants.CAS_ST + "=" + CAS_ST + "&"
					+ Constants.LOCAL_SERVICE + "=" + LOCAL_SERVICE);
		} else
			response.sendRedirect(request.getContextPath()+"/main.do");
	} else {
		System.out.println("密码错误");
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}
	//request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
	}
	

}