import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SellerProcessingThread extends Thread{
	//Create a new list from the class ItemList
	ItemList itemlist=new ItemList();
	Socket s = null;
	
	//Binding in the Class
	SellerProcessingThread(Socket sel) {
		s = sel;
	}
	
	//Initialising the itemNo to 0 so that each product added to the auction list will increment 
	int itemNo=0;
	
	//Function which adds Products to the list
	public void addanitem(String add){
		Item item = new Item();
		itemNo++;
		String[] split = add.split(" ");
		item.itemno = itemNo;
		item.name = split[1];
		item.highestBidderName = null;
		item.highestBid = 0;
		ItemList.addItem(item);	
	}
	
	public void run(){
		//Initialising a list and assigning the seller name
		//I could change this to Tanya either
		String Displist = "";
		String seller = "Jake";

		try {
			//Get input
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			//Print to screen
			PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
			//Get the user logged in
			pw.println("Enter your Login name:");
			String str = bf.readLine();

			if (str.equalsIgnoreCase(seller)) {
				String login = "Login Successful";
				System.out.println(seller + " " + login);
				//send message to the server that you are logged in
				pw.println(login);

				//Whilst connect read from the commmand line
				while (true) {
					String para = bf.readLine();

					//Adds a product to the list if the string contains add
					if (para.contains("add")) {
						addanitem(para);
						//Message to the server 
						pw.println("Item added to the auction");
					}

					//List all the auction items
					else if (para.contains("list")) {
						Displist = ItemList.printList();
						System.out.println(Displist);
						//Message to the server
						pw.println(Displist);
					}

					//When seller wants to sell the product, it will go to the highest bidder and messages will be sent accordingly
					else if (para.contains("sell")) {
						Item it = new Item();
						int index = 0;
						boolean flag = false;
						String[] split = para.split(" ");
						System.out.println("List size is: "+ItemList.getlength());
						for (int loop = 0; loop < ItemList.list.size(); loop++) {
							it = ItemList.list.get(loop);
							if ((it.itemno==(Integer.parseInt(split[1])))&& (flag == false)) {
								flag = true;
								System.out.println(ItemList.printList());
								//The sold product is then removed from the auction list
								pw.println("Item "+ it.name+ " is now removed from the auction");
								it.buyerPrintWriter.println("You won the auction\n You're now an owner of a "+ it.name);
								ItemList.list.remove(index);
							}
						}
					}
					//If the seller wants to quit
					else if (para.contains("quit")) {
						pw.println("Thank you, Goodbye");
						break;
					}
					//If an error occurs
					else {
						System.out.println("Error");
						pw.println("Error");
					}
				}
			}
			else {
				String login = "Login Failed, Please retry";
				System.out.println(login);
				pw.println(login);

			}

			bf.close();
			pw.close();
			s.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
