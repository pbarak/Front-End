package app;


public class Token {
	
	private static String token ;
	
	public static void setVariable(String s)
	{
	    token = s;
	}
	
	public static String getVariable()
	{
	    return token;
	}
}
