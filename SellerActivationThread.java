import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Simple Thread to connect to the server
public class SellerActivationThread extends Thread
{
	public void run()
	{
		Socket socket = null;
			try
			{
				ServerSocket Server = new ServerSocket(6000); // ServerSocket for SellerClients
				while (true) 
				{
					socket = Server.accept();
					new SellerProcessingThread(socket).start();
				}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
}
