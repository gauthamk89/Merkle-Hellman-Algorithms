import java.math.BigInteger;

/** List Class holds the data to be stored inside a doubly linked list
 *  with links that point to prev and next nodes.
 */
public class List 
{
	BigInteger x;
	List prev;
	List next;
	
	/** Constructors*/
	List(BigInteger x)
	{
		this.x = x;
		prev = null;
		next = null;
	}
	
	public List() {
		prev=null;
		next = null;
	}
}
