import java.math.BigInteger;


public class DoublyLinkedList {
	
	List head;
	List tail;
	
	/** Constructors*/
	DoublyLinkedList()
	{
		head = null;
		tail = null;
	}
	
	/** Check if the function is Empty
	 * if the head or tail is null return true else false
	 * Running time for all the cases: Big Theta(1)*/
	public boolean isEmpty()
	{
		if(head == null || tail==null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
		
	/** Function will add the character to the list from the rear end
	 *  Running time for all the cases Big Theta(1)*/
	public void addCharAtEnd(BigInteger input)
	{
		//BigInteger value = BigInteger.valueOf(input);
		List node = new List(input);
		
		if(isEmpty())
		{
			head = node;
		}
		else
		{
			tail.next = node;
			node.prev = tail;
		}
		
		tail = node;
	}

		
	/** Method which displays the contents of the list
	 *  Running case in BigTheta(n), n indicates the number of elements in the list*/
	public String toString()
	{
		StringBuilder s1 = new StringBuilder();
		List temp = head;
		
		if(head==null){
			return null;
		}
		
		while(temp!=null)
		{
			s1.append(temp.x);
			temp = temp.next;
		}
		return s1.toString();
	}
	
	/** Gets the data from the doubly linked at a specified index
	 *  Running case in BigTheta(n), n indicates the number of elements in the list*/
	public BigInteger getdata(int index)
	{
		List temp = head;
		int count =0;
		
		if(head==null)
		{
			return BigInteger.ZERO;
		}
		
		while(count!=index)
		{
			if(temp.next!=null)
			{
				temp = temp.next;
			}
			else
			{
				break;
			}
			count = count +1;
		}
		return temp.x;
	}
	 
}
