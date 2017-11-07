import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Simple Thread to connect to the server
public class BuyerActivationThread extends Thread
{
	public void run()
	{
		Socket socket = null;
			try
			{
					ServerSocket Server = new ServerSocket(7000); 
					while (true) 
					{
						socket = Server.accept();
						new BuyerProcessingThread(socket).start();
					}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
	 }
}
