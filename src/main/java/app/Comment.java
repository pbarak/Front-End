package app;


public class Comment {

	private String text = new String();
	private String commenter = new String();
	private String timestamp = new String();
	
	
	public Comment(String text, String commenter, String timestamp)
	{
		this.text = text;
		this.commenter = commenter;
		this.timestamp = timestamp;
	}
	
	public void setText(String text)
	{
		if(text.length() <= 1024)
		{
			this.text = text;
		}
		
		
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public void setCommenter(String commenter)
	{
		this.commenter = commenter;
	}
	
	public String getCommenter()
	{
		return this.commenter;
	}
	
	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
	
	public String getTimestamp()
	{
		return this.timestamp;
	}
	
}

