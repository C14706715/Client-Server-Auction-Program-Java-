import java.io.*;
import java.net.*;

class Buyer
{
	public static void main(String[] args) throws IOException
  {
		System.out.println("New Buyer");
		//TCP Connection to the host 
		InetAddress inetAddress;
		
		//Initialising  Sockets and readers 
		String parameter = "";
		Socket client = null;
		BufferedReader input=null;
		BufferedReader bufferredReader=null;
		PrintWriter printWriter=null;
		
		//Makes the connection the the host which is the localhost 
		inetAddress = InetAddress.getLocalHost();
		client = new Socket(inetAddress, 7000);
		
		//Initialising input and output to and from the server
		input = new BufferedReader(new InputStreamReader(System.in));
		bufferredReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		printWriter = new PrintWriter(client.getOutputStream(), true);
		
		//Printing message to the screen
		System.out.println(bufferredReader.readLine());
		printWriter.println(input.readLine());
		
		//reads in the welcome message to determine if the user has connected correctly or not 
		String loginMessage = bufferredReader.readLine();
		if (loginMessage.contains("Welcome")) 
    {
			System.out.println(loginMessage);
			while (true)
      {
				char[] txt = new char[400];
				parameter = input.readLine();
				printWriter.println(parameter);
				bufferredReader.read(txt);
				System.out.println(txt);
				if (parameter.contains("quit"))
        {
					break;
				}
			}
		} 
		else{
			System.out.println(loginMessage);
		}

		//Closing off readers and connection to the server
		printWriter.close();
		bufferredReader.close();
		input.close();
		client.close();
	}
}
