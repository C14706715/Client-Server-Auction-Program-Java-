public class Server {
	public static void main(String args[])
	{
		//Starts up the server through the use of Threads 
		System.out.println("\nAuction Server\n\n");
		SellerActivationThread mythread1 = new SellerActivationThread();
		mythread1.start();
		BuyerActivationThread mythread2 = new BuyerActivationThread();
		mythread2.start();
	}

}
