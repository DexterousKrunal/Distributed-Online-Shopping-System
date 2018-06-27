import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.List;
import java.io.*;

public class PuranikPatelSender {

	public static void main(String args[])
	{
	     Scanner input = new Scanner(System.in);
	     Socket s = null;
	     Socket s1 = null;

	     List list = new List();
	     List listB = new List();
	     File fileName= new File("../AOSassignment2/src/data.txt");
	    

		try
		{
			int serverPort = 0;
			String ipAddress = " ";
			System.out.println("Enter server port");
			serverPort = Integer.parseInt(input.nextLine());
			System.out.println("Enter Ip address of server");
		    ipAddress = input.nextLine();
			s = new Socket("192.168.43.108",serverPort);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
            ObjectOutputStream objectOutput = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream objectInput;
        	String address =  InetAddress.getLocalHost().getHostName();
			out.writeUTF(address);
			System.out.println(address);
			String port= "1599";
			out.writeUTF(port);
			System.out.println("Enter user name");
			String username = input.nextLine();
			list.add(username);
			System.out.println("Enter password");
			String password = input.nextLine();
			list.add(password);
			list.add(username);
			objectOutput.writeObject(listB);
			objectOutput.writeObject(list);
			
			String points = in.readUTF();
			System.out.println("Your points are:"+points);
			String group = in.readUTF();
			System.out.println(group);
			List l1 = new List();
			if(group!=null){
				System.out.println("inside group clause");
				DatagramSocket clientSocket = new DatagramSocket(1599);
				 //System.out.println(InetAddress.getLocalHost());
				BufferedReader inFromUser =
				         new BufferedReader(new InputStreamReader(System.in));
				     // DatagramSocket clientSocket = new DatagramSocket();
				      InetAddress IPAddress = InetAddress.getByName("DESKTOP-NKECT7M");
				      System.out.println(IPAddress);
				      byte[] sendData = new byte[1024];
				      byte[] receiveData = new byte[1024];
				    /*  String sentence = "I am your client";

				      sendData = sentence.getBytes();
				      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
				      clientSocket.send(sendPacket);
				      System.out.println(sendPacket.getAddress());
*/
				      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				      clientSocket.receive(receivePacket);
				      String modifiedSentence = new String(receivePacket.getData());
				      System.out.println("FROM SERVER:" + modifiedSentence);
				      
				      DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
				      clientSocket.receive(receivePacket1);
				      String modifiedSentence1 = new String(receivePacket1.getData(),0,receivePacket1.getLength());
				      System.out.println(modifiedSentence1);
				      int len = Integer.parseInt(modifiedSentence1);
				      List az = new List();
				      for(int i= 0;i<len;i++)
		 				{
				      
				       receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
				      clientSocket.receive(receivePacket1);
				      
				       modifiedSentence1 = new String(receivePacket1.getData(),0,receivePacket1.getLength());
				      System.out.println(modifiedSentence1);
				      az.add(modifiedSentence1);
				      
		 				}
				      
				      String productName = inFromUser.readLine();
				      
				      for(int i= 0;i<az.getRows()-1;i++)
			          {
			        	  String choice = az.getItem(i);
			        	  if (choice.contains(productName))
			        	  {
			        		  System.out.println("product u have selected is:"+choice);
			        		  sendData = choice.getBytes();
			        		  DatagramPacket sendPacket  = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
						      clientSocket.send(sendPacket);			        	  }
			          }
				      
				      
				      receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
				      clientSocket.receive(receivePacket1);
				      
				       modifiedSentence1 = new String(receivePacket1.getData(),0,receivePacket1.getLength());
				      System.out.println("Your new points after shopping are:" + modifiedSentence1);
				      System.out.println("Thank you for shopping");
				      clientSocket.close();
					}
		}

		catch(UnknownHostException e)
		{
			System.out.println("Sock1:"+e.getMessage());
		}
		catch(EOFException e)
		{
			System.out.println("EOF:"+e.getMessage());
		}
		catch(IOException e)
		{
			System.out.println("IO:"+e.getMessage());
		}
		finally
		{
			if(s!=null)
				try
			{
					s.close();
			}
			catch(IOException e)
			{
				System.out.println("Close:"+e.getMessage());
			}
		}
	}
}


