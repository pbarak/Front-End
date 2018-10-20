package app;

import zookeeper.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

//import zookeeper.Configuration;

import java.security.*;
import java.util.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.charset.Charset;

//import zookeeper.Configuration;

/**
 * Servlet implementation class WebInterface
 */
@MultipartConfig
@WebServlet("/Interface")
public class WebInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebInterface() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String token = request.getParameter("token");
		if(token == null || token == "")
		{
			String callback = Configuration.getInstance().getMyIP();
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//Select the first available Authentication System
			response.sendRedirect(Configuration.getInstance().getAvailableAuthSystems().get(0).get("URL").toString() + Configuration.getInstance().getMyIdentifier() + "&callback=" + callback );

		}
		else
		{
			
			try {
					Token.setVariable(token);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			}

			catch (JSONException e) {
				  e.printStackTrace();
				
			}
		}
	
	}
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
