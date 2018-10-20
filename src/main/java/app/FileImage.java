package app;

import java.util.ArrayList;

public class FileImage {
	
	FileImage(String imageFile, ArrayList comments, String imageId, String timestamp, String gallerId)
	{
		this.galleryId = galleryId;
		this.imageFile = imageFile;
		this.comments = comments;
		this.imageId = imageId;
		this.timestamp = timestamp;
	}
	
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private String imageFile = new String();
	private String imageId = new String();
	private String timestamp = new String();
	private String galleryId = new String();
	
	public String  getGalleryId()
	{
		return this.galleryId;
	}
	
	public void setGalleryId(String galleryId)
	{
		this.galleryId = galleryId;
		
	}
	
	public String  getTimestamp()
	{
		return this.timestamp;
	}
	
	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
		
	}
	
	public ArrayList<Comment> getComments()
	{
		return this.comments;
	}
	
	public void setComments(ArrayList<Comment> comments)
	{
		this.comments = comments;
		
	}
	
	public void setImageFile(String imageFile)
	{
		this.imageFile = imageFile;
	}
	
	public String getImageFile()
	{
		return this.imageFile; 
	}
	
	public void setImageId(String imageId)
	{
		this.imageId = imageId;
	}
	
	public String getImageId()
	{
		return this.imageId; 
	}
	
	
	

}

