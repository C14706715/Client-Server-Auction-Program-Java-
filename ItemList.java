import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemList {

	static List<Item> list = new ArrayList<Item>();

	 public static void addItem(Item i)
	 {
		list.add(i);
	 }
	 public static int getlength() 
	 {
		return list.size();
	 }
	 public static  void removeItem(Item i)
	 {
		list.remove(i);
	 }
	 public static String printList() 
	 {
		String str = "";
		Item element;
		int size=list.size();
		System.out.println("list size:" + size);
		if(size>0)
		{		
			Iterator<Item> it = list.iterator();
				while (it.hasNext())
				{
          //This is how the item will be displayed when I wrtie list on the cmd
					element = it.next();
					str = str + "Item no:" + element.itemno + 
                      "\nItem name:" + element.name + 
                      "\nHighest Bidder:" + element.highestBidderName + 
                      "\nHighestBid:" + element.highestBid + "\n";
				}
		}
		else
		{
			str=str+"list is empty";
		}

		return str;
	}

}
