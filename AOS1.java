import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AOS1 {

	public static void main(String[] args)  
		{
	     String testfromServer =null;
	     ServerSocket welcomeServerSocket = null;
	     int portNbr = Integer.parseInt(args[0]);
	     try
	     {
			  welcomeServerSocket = new ServerSocket(portNbr);
		  	  System.out.println("Reciever Server is waiting to Serve client");
		  	  while(true)
		  	  {
		  		Socket clientSocket = welcomeServerSocket.accept();
		  		SProxyServerConnection spsc = new SProxyServerConnection(clientSocket);
		  	  }
	     }
	     catch(IOException e)
	     {
	    	 if (welcomeServerSocket != null && !welcomeServerSocket.isClosed()) 
	    	 {
	    	        try 
	    	        {
	    	        	welcomeServerSocket.close();
	    	        } 
	    	        catch (IOException e1)
	    	        {
	    	            e1.printStackTrace(System.err);
	    	        }
	    	    }
	     }  
	  
	  }
	}
	class SProxyServerConnection extends Thread
	{
		static DataOutputStream outToProxyServer ;
	    static BufferedReader inFromProxyServer;
	    static Socket ServerProxyServerSocket;
	    static String FirstMessage = null;
	    String[] SwitchWordsAtServer = null;
	    String[] SwitchWordsAftertrim = null;
	    String MsgFromProxy = null;
	    String line = null;
		String[] temp = null;
		public SProxyServerConnection(Socket ServerProxyServerSocket)
	    {
	    	try{
	    		
	    		outToProxyServer = new DataOutputStream(ServerProxyServerSocket.getOutputStream());
	    		inFromProxyServer = new BufferedReader(new InputStreamReader(ServerProxyServerSocket.getInputStream()));
	            this.start();
	    	}catch(IOException e)
	    	{
	    		System.out.println("Connection failure with Server");
	    		try 
	    		{
					outToProxyServer.close();
					inFromProxyServer.close();
				} catch (IOException e1) 
	    		{
					System.out.println("Unable to close the Buffers which are used for communicating");
				}
	    	}
	    	
	    }
	    public void run()
	    {
	    	try {
	    		
	    		MsgFromProxy = inFromProxyServer.readLine();
	    		System.out.println(MsgFromProxy);
	    		
	    		do
	    		{	
				
	    		   switch (MsgFromProxy.charAt(0))
	    			
	    			{
	    			   case 'A':  System.out.println("Connection Established from reciever");
	    			   				System.out.println();
									outToProxyServer.writeBytes("A.Success"+'\n');
									break;
	    			   case 'B' 
			    				    SwitchWordsAtServer = MsgFromProxy.split(" ");
			    				   
			    		    	  	System.out.println(MsgFromProxy);
			    		
			    		    	  	String toFindSubstring =  MsgFromProxy.substring(5);
			    		    	  	String[] SwitchWordsAftertrim = toFindSubstring.split(" ");
			    		    	  	Collection<String> str = new ArrayList() ;
			    		    		
			    		    		for(String string:SwitchWordsAftertrim)
			    		    		{
			    		    		   str.add(string);	
			    		    		}
			    		    		System.out.println(str);
			    		    	  	String output=this.compute(str);
			    		    	  	System.out.println(output);
			    		    	  	if(output.isEmpty())
			    		    	  	{
			    		    	  		outToProxyServer.writeBytes("O.Output from Server: No Substring"+'\n');
			    		    	  		
			    		    	  	}
	    			   				else
	    			   				{
	    			   					outToProxyServer.writeBytes("O.Output from Server: "+output+"Length is "+output.length()+'\n');
	    			   				}
			    		    	  	
	    			   				break;
	    			   case 'C' :  outToProxyServer.writeBytes("X.Clossing the connection from receiver "+'\n');
	    				    	   
					    		  try 
					   	    		{
					   					outToProxyServer.close();
					   					inFromProxyServer.close();
					   					ServerProxyServerSocket.close();
					   					System.out.println("Connection Disconnected with Server");
					   				} catch (IOException e) 
					   	    		{
					   					System.out.println("Unable to close the Buffers which are used for communicating");
					   				}
	    			   				 break; 
	    			   case 'Q' : outToProxyServer.writeBytes("X.Clossing the connection from receiver "+'\n');	
				    			   try 
				    	    		{
				    					outToProxyServer.close();
				    					inFromProxyServer.close();
				    					ServerProxyServerSocket.close();
				    				} catch (IOException e) 
				    	    		{
				    					System.out.println("Unable to close the Buffers which are used for communicating");
				    				}
	    			              break;
	    			   default:System.out.println("Unable to Intrepet Server Message");
	    			   				break;
	     			
		    			}
	    		    
	    			
	    		}while((MsgFromProxy = inFromProxyServer.readLine())!= null);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    public  String compute(Collection<String> strings) {
		    if(strings.isEmpty()) return "";
		    Set<Character> v = new HashSet<Character>();
		    int i = 0;
		    try {
		        while(true) {
		            for(String s : strings) v.add(s.charAt(i));
		            if(v.size() > 1) break;
		            v.clear();
		            i++;
		        }
		    } catch(StringIndexOutOfBoundsException ex) {}
		    return strings.iterator().next().substring(0, i);
		}
	}


