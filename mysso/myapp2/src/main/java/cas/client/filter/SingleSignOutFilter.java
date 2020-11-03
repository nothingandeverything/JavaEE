package cas.client.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cas.client.Constants;
import cas.client.SessionMap;

/**
 * Servlet Filter implementation class SingleSignOutFilter
 */
@WebFilter(filterName = "F1", urlPatterns = "/*")
public class SingleSignOutFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public SingleSignOutFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		//HttpServletResponse httpResponse=(HttpServletResponse)response;
		//HttpSession session=httpRequest.getSession();
		String logout=httpRequest.getParameter("logout");
		String sessionId=httpRequest.getParameter("sessionId");
		System.out.println("F1,sessionID:"+sessionId);
		if(logout!=null)
		{
			System.out.println("logout...");
			//String sessionId=httpRequest.getParameter("sessionId");
			SessionMap.invalidate(sessionId);
			response.getWriter().println("succeed");
		}else{
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
