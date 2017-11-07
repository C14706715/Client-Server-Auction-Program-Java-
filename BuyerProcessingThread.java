import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BuyerProcessingThread  extends Thread
{
	Socket clientSocket = null;
	BuyerProcessingThread(Socket socket)
  {
    clientSocket = socket;
	}
	
	public void run() {
		//set the flag to false until a bid is made
		boolean buyerFlag = false;
		
		//Declaring Strings and Arrays 
		String listData = "";
		String[] buyer = {"Jake", "Tanya"};
		BufferedReader bufferedReader=null;
		PrintWriter printWriter=null;
		
		try{
			//Attempt to read input from the server
			bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			
			//Ask for login information
			printWriter.println("Enter your login name:");
			
			//Read user input, then check is input matches 
			String str = bufferedReader.readLine();
			System.out.println(str);
			
			for (int i = 0; i < buyer.length; i++) {
				if ((str.equalsIgnoreCase(buyer[i])) && (buyerFlag == false)) {
					//Concat a message to welcome the new buyer
					String login = "Welcome ";
					String loginname = buyer[i];
					System.out.println(loginname + " " + login);
					printWriter.println(login);
					buyerFlag = true;
						
					while (true){
						String para = bufferedReader.readLine();
						buyerFunctionProcess(para, bufferedReader, printWriter, loginname, listData);
						 if (para.contains("quit ")){
							printWriter.println("Thank you, Goodbye");
							break;
						 }
					}
				}
			} 

			if (buyerFlag == false) {
				String login = "Login Failed";
				System.out.println(login);
				printWriter.println(login);
			}

		} 
		catch (IOException exception) {
			exception.printStackTrace();
		}
		
		finally
		{
			try
			{
				bufferedReader.close();
				printWriter.close();
				clientSocket.close();
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}

	}
	public void bidProcess(Item it,String str,boolean flag,PrintWriter printWriter,String loginname,int value)
	{

		if ((it.itemno==Integer.parseInt(str))&& (flag == false)) {
			flag = true;

			if (it.highestBid == 0) {
				it.buyerPrintWriter = printWriter;
				it.highestBidderName = loginname;
				it.highestBid = value;
				
				//Displays the highest bidder
				printWriter.println("Highest Bidder for " + it.name + " at €" + it.highestBid);
			}

			//If bid is below 0 
			else if (value < it.highestBid) {
				printWriter.println("Sorry, Your bid too low");
			}

			//If someone has bidded more than this bid display this message
			else if (it.highestBid < value) {
				it.buyerPrintWriter.println("You have been over bidden by another bidder for the "+ it.name);
				it.highestBidderName = loginname;
				System.out.println("The value is €" + value);
				it.buyerPrintWriter = printWriter;
				it.highestBid = value;
				printWriter.println("You have the higgest bid for item "+ it.name + " at €" + it.highestBid);
			}
		}
	}
	
	private void buyerFunctionProcess(String para,BufferedReader bufferedReader,PrintWriter printWriter,String loginname,String listData) throws IOException{
		//when the user inputs a bid the bid in split into sections like bid, productID and bid price
		if (para.contains("bid")){
			Item it = new Item();
			boolean flag = false;
			String[] split = para.split(" ");
			int value = Integer.parseInt(split[2]);
			for (int loop = 0; loop < ItemList.list.size(); loop++){
				it = ItemList.list.get(loop);
				bidProcess(it, split[1], flag, printWriter, loginname, value);
			}
		}

		//This will list all the product on offer to bid as they are in the Itemlist
		else if (para.contains("list")) {
			listData = ItemList.printList();
			System.out.println(listData);
			printWriter.println(listData);
		}
	}
}
