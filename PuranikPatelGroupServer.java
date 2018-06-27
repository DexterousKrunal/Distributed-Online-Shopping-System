package groupServer;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.List;
import java.io.*;

public class PuranikPatelGroupServer {
	
	
	 public static void main(String[] args)  {
	    
		 try
	      {
			System.out.println(InetAddress.getLocalHost());			   System.out.println("Server running");
	    	  int serverPort=9999;
	    	  ServerSocket listenGroupSocket = new ServerSocket(serverPort);
	    	  while(true)
	    	  {
	    		  Socket clientGroupSocket = listenGroupSocket.accept();
	    		  Connection c = new Connection(clientGroupSocket);
	    		 
	    	  }
	    	}catch(IOException e)
	      {
	    	  System.out.println("Listen:"+e.getMessage());
	      }
	     }
	
}

class Connection extends Thread
{
	DataInputStream in;
	DataOutputStream out;
	Socket clientGroupSocket;
	ObjectInputStream objectInput;
	ObjectOutputStream objectOutput;
	
	DataInputStream in1;
	DataOutputStream out1;
	Socket clientGroupSocket1;
	ObjectInputStream objectInput1;
	
	 File filePlatinum= new File("..//groupServer/src/groupServer/platinum1.txt");
	 File fileSilver= new File("../groupServer/src/groupServer/silver.txt");
	 File fileGold= new File("../groupServer/src/groupServer/gold.txt");
	 List list = new List();
	public Connection(Socket aClientGroupSocket)
	{ 
		try
		{
			clientGroupSocket = aClientGroupSocket;
			in = new DataInputStream(clientGroupSocket.getInputStream());
			out = new DataOutputStream(clientGroupSocket.getOutputStream());
            objectInput = new ObjectInputStream(clientGroupSocket.getInputStream()); 
            objectOutput = new ObjectOutputStream(clientGroupSocket.getOutputStream());
           
//            String address =  InetAddress.getLocalHost().getHostName();
//			out.writeUTF(address);
//			System.out.println(address);
//			System.out.println("reached this");
			FileReader fileReader = new FileReader(fileSilver);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			String uname = in.readUTF();
			System.out.println("user name is:"+uname);
			String pointz = in.readUTF();
			System.out.println("Points are:"+pointz);
			String clientAddress = in.readUTF();
			System.out.println(clientAddress);
			String port1 = in.readUTF();
			System.out.println("Client port is:"+port1);
			
			InetAddress.getByName(clientAddress);
			 int point = Integer.parseInt(pointz);
			if(point>0&&point<=500)
			{
				 fileReader = new FileReader(fileSilver);
				 bufferedReader = new BufferedReader(fileReader);	
			}
			else 
				if(point>500&&point<=1000)
				{
					 fileReader = new FileReader(fileGold);
					 bufferedReader = new BufferedReader(fileReader);	
				}
				else if(point>1000)
				{
					 fileReader = new FileReader(filePlatinum);
					 bufferedReader = new BufferedReader(fileReader);	
				}

			
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				list.add(line);
					
			}
			 System.out.println("list length:"+list.getRows());
			
		//	DatagramSocket serverSocket = new DatagramSocket(9876);
			
	         byte[] receiveData = new byte[102478];
	          byte[] sendData = new byte[1024];
			
			 while(true)
              {
				
				   InetAddress IPAddress = InetAddress.getByName(clientAddress);

                 int port =Integer.parseInt(port1);
                 DatagramSocket serverSocket = new DatagramSocket();
                 String capitalizedSentence = "Enter product of your choice";
                 sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket =
                 new DatagramPacket(sendData, sendData.length, IPAddress,port);
                 serverSocket.send(sendPacket);
                 
                 String numOfProd = String.valueOf(list.getRows()-1);
                 System.out.println(numOfProd);
                 sendData = numOfProd.getBytes();
                 sendPacket =
    	                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
    	                 serverSocket.send(sendPacket);
                 
                 for(int i= 0;i<list.getRows()-1;i++)
 				{
 					
 					String  fileData = list.getItem(i);
 					sendData = fileData.getBytes();
 				 sendPacket =
	    	                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
	    	                 serverSocket.send(sendPacket);
 					
 				}
                 
                 DatagramPacket  receivePacket = new DatagramPacket(receiveData, receiveData.length);
                 
                 
                 serverSocket.receive(receivePacket);
                 System.out.println("reached here");
                 String d = new String( receivePacket.getData(),0,receivePacket.getLength());
                 System.out.println("Product selected by user: " + d);
                 
                 File file1= new File("C:/Users/jaimin/workspace/AOSassignment2/src/data.txt");
 				Path file2 = Paths.get("C:/Users/jaimin/workspace/AOSassignment2/src/data.txt");
 			     List list4 = new List();
 			     String fileData = " ";
 			     String points = " " ;
 				try
 				{
 					
 					FileWriter fileWriter = new FileWriter(file1, true);

 				    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
 					FileReader fileReader1 = new FileReader(file1);
 					BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
 					StringBuffer stringBuffer1 = new StringBuffer();
 					String line1;
 					Integer temp1=0;
 					while ((line1 = bufferedReader1.readLine()) != null) {
 						stringBuffer.append(line1);
 						stringBuffer.append("\n");
 						list4.add(line1);
 						
 					}
 					
 					
 					System.out.println(list4.getRows());
// 					
 					String[] a1 = d.split("\\s");
 					for(int i= 0;i<list4.getRows()-1;i++)
 					{
 						//System.out.println(list1.getItem(i));
 						 fileData = list4.getItem(i);
 						 String[] a = fileData.split("\\s");
 						 String oldPoints =a[2];
 						

 						
 						if(fileData.contains(uname))
 						{
 							System.out.println("Current points are:"+a[2]);
 							Integer z= Integer.parseInt(a[2]);
 							int newP = z+10;
 							String old1 = a[0]+" "+a[1]+" "+a[2];
 							String new1 = a[0]+" "+a[1]+" "+newP;

 							System.out.println("new data:"+new1);
 							
 							
 							ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(file2, StandardCharsets.UTF_8));

 							for (int j = 0; j < fileContent.size(); j++) {
 							    if (fileContent.get(j).equals(old1)) {
 							    	System.out.println("found");
 							        fileContent.set(i, new1);
 							        break;
 							    }
 							}

 							Files.write(file2, fileContent, StandardCharsets.UTF_8);
 							
 							 String newPS = Integer.toString(newP);
 			                 sendData = newPS.getBytes();
 			                 sendPacket =
 			                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
 			                 serverSocket.send(sendPacket);
 						}
 						
 					}

 				
 			
 				}

 				catch(EOFException e){
 					System.out.println("EOF:"+e.getMessage());
 				}
 				catch(IOException e)
 				{
 					System.out.println("IO:"+e.getMessage());
 				}

              }


			
		}
		catch(IOException e)
		{
			System.out.println("Connection:"+e.getMessage());
		}
		}
/*
		public void run()
		{
			  
			try
			{
								
				}

			
			catch(EOFException e){
				System.out.println("EOF:"+e.getMessage());
			}
			catch(IOException e)
			{
				System.out.println("IO:"+e.getMessage());
			}
			finally 
			{
				try
				{
					clientGroupSocket.close();
				}
				catch(IOException e)
				{
					System.out.println("Close failed");
				}
			}
		}*/
		
	
}

