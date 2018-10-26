package app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;



/**
 * Servlet implementation class GetData
 */
@WebServlet("/SetData")
public class SetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws UnsupportedEncodingException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		request.setCharacterEncoding("UTF-8");
		String data = request.getParameter("data");
		String galleryName = request.getParameter("New"); //we have value="New" in NoGalleries.jsp 
		ArrayList<FileImage> imageList = new ArrayList<FileImage>();

		
		if((data != null || data != "") && (galleryName == null || galleryName == ""))
		{
			
		}
		
		if((galleryName != null || galleryName != "") && (data == null || data == ""))
		{//new gallery
				
				Set newsetter = new Set();
				newsetter.setGallery(galleryName);				
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
