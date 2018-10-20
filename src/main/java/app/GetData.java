package app;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

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
@WebServlet("/GetData")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetData() {
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
		
		request.setCharacterEncoding("UTF-8");
		//we use extensively the parameter named data
		String data = request.getParameter("data");
		String friendName = request.getParameter("friendName");
				
		ArrayList<FileImage> imageList = new ArrayList<FileImage>();

		
		if((data != null || data != "") && friendName == null)
		{
			String myGalleries = "";
			String myFriends = "";
			
			if(data.equals("getFriends"))
			{
				//Create getData Object
				Get getData = new Get();
				myFriends = getData.getFriends(data);
					ArrayList<String> listdata = new ArrayList<String>();     
					ArrayList<String> listdata1 = new ArrayList<String>();     
					
				try {
					if(myFriends != null && myFriends != "") {
						//create json object from the result of the RPC call
					JSONObject jsonObj = new JSONObject(myFriends);
					
					JSONArray jsonArray = jsonObj.getJSONArray("result");
					
					if(jsonArray!=null) {

						for(int i=0;i<jsonArray.length();i++){
							listdata1.add(jsonArray.getJSONObject(i).getString("friendname"));
						}
					}
				}
				
				}catch (JSONException e) {
					e.printStackTrace();					
			}
				request.setAttribute("myFriends", listdata1);
				//Send the object to jsp 
				request.getRequestDispatcher("MyFriends.jsp").forward(request, response);

			}
			if(data.equals("mygalleries"))
			{
				
				Get getData = new Get();
				 myGalleries = getData.getAllGalleries(data);
				
					   
					HashMap<String, String> params = new HashMap<String,String>();

				try {
					if(myGalleries != null && myGalleries != "") {
					JSONObject jsonObj = new JSONObject(myGalleries);
					
					if((jsonObj.getString("error") != null) && (jsonObj.getString("error") != ""))
					{
						response.sendRedirect("NoGalleries.jsp");
						return;
					}
					
					JSONArray jsonArray = jsonObj.getJSONArray("result");
					
					if(jsonArray!=null) {

						for(int i=0;i<jsonArray.length();i++){
							
							params.put(jsonArray.getJSONObject(i).getString("galleryid"), jsonArray.getJSONObject(i).getString("galleryname"));
						}
					}
				}
				
				}catch (JSONException e) {
					e.printStackTrace();					
			}
				request.setAttribute("galleryEntries", params);
				
				request.getRequestDispatcher("Galleries.jsp").forward(request, response);

			}

			if(data.equals("mygallery"))
			{
				
				Get getData = new Get();
				String galleryId = request.getParameter("galleryid");
				
				String mygallery = getData.getGallery(galleryId);
				


				try {
					JSONObject jsonObj = new JSONObject(mygallery);
					
					JSONArray jsonArray = jsonObj.getJSONArray("result");
					
					if(jsonArray!=null) {

						
						ArrayList<Comment> commented = new ArrayList<Comment>();

						for(int i=0;i<jsonArray.length();i++){
							JSONArray comments = jsonArray.getJSONObject(i).getJSONArray("comments");
							if(comments != null) {
								for(int j=0;j<comments.length();j++){
									Comment comment = new Comment(comments.getJSONObject(j).getString("text"), comments.getJSONObject(j).getString("commenter"), comments.getJSONObject(j).getString("timestamp"));
								}
								}
							
							imageList.add(new FileImage(jsonArray.getJSONObject(i).getString("imageURL"), commented, jsonArray.getJSONObject(i).getString("id"), jsonArray.getJSONObject(i).getString("timestamp"), galleryId));

							}
							
						}
					}catch (JSONException e) {
						e.printStackTrace();						
					}
				request.setAttribute("images", imageList);
				
				
				request.getRequestDispatcher("Gallery.jsp").forward(request, response);

			}
			if(data.equals("image"))
			{
				
				Get getData = new Get();
				String imageId = request.getParameter("image");
				String imageURL = request.getParameter("imageURL");
				
				ArrayList<Comment> comments = getData.getComments(imageId);

				request.setAttribute("image", imageURL);
				request.setAttribute("comments", comments);
						
				request.getRequestDispatcher("Image.jsp").forward(request, response);
			
			}
			

		}
		else if( (data != null || data != "") && (friendName != null || friendName != "") )
		{
			System.out.println("Into the friendGalleries");
			String friendGalleries = "";

			Get getData = new Get();
			 friendGalleries = getData.getFriendGalleries(data, friendName);
			
				HashMap<String, String> params = new HashMap<String,String>();
    
			try {
				if(friendGalleries != null && friendGalleries != "") {
				JSONObject jsonObj = new JSONObject(friendGalleries);
				
				JSONArray jsonArray = jsonObj.getJSONArray("result");
				
				if(jsonArray!=null) {

					for(int i=0;i<jsonArray.length();i++){
						params.put(jsonArray.getJSONObject(i).getString("galleryid"), jsonArray.getJSONObject(i).getString("galleryname"));

					}
				}
			}
			
			}catch (JSONException e) {
				e.printStackTrace();				
		}
			request.setAttribute("galleryEntries", params);
			
			request.getRequestDispatcher("Galleries.jsp").forward(request, response);
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

