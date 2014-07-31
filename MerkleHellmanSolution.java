import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;



public class MerkleHellmanSolution {
	
	DoublyLinkedList w ;
	DoublyLinkedList b;
	int inputSize=0;
	BigInteger q= new BigInteger("0");
	BigInteger r = new BigInteger("0");
	
	/** Constructor*/
	MerkleHellmanSolution()
	{
		w =new DoublyLinkedList();
		b =new DoublyLinkedList();
	}
	
	
	/** Private Key to decrpyt the encrypted key, it is super increasing
	 *  Running cases for all input condition is Big Theta (n2)
	 *  and Post condition would be function returns a BigInteger*/
	public void gen_encrpytseq(int length)
	{
		BigInteger sum = new BigInteger("0"); 
        Random random = new Random();
        
        for(int i=0;i<(length*8);i++)
        {
        	List temp = w.head;
        	if(temp == null)
        	{
        		sum = BigInteger.ZERO;
        	}
        	else
        	{
        		while(temp!=null)
        		{
        			sum = sum.add(temp.x);
        			temp = temp.next;
        		}
        	}
        	sum = sum.add(new BigInteger(random.nextInt(5)+1+""));
        	w.addCharAtEnd(sum);
        }	
	}
	
	
	/** Public Key used to encrypt the input string
	 *  Function generates q, r and also the public encryption key
	 *  Pre Condition before generating the public key we need to have q and r
	 *  Running time is BigTheta(n)*/
	public void  gen_decrptseq()
	{
	   q = gen_qvalue();
	   r = gen_rvalue();
		
		List temp = w.head;
		while(temp!=null)
		{
			BigInteger x;
			x = temp.x.multiply(r).mod(q);
			b.addCharAtEnd(x);
			temp = temp.next;
		}
	}
	
	/** Function that generates the Q value which should be greater than the sum of all the 
	 *  public key generated
	 *  Running time is BigTheta(n)
	 */
	public BigInteger gen_qvalue()
	{
		List temp = w.head;
		BigInteger sum = new BigInteger("0");
		Random r = new Random();
		while(temp!=null)
		{
			sum = sum.add(temp.x);
			temp = temp.next;
		}
		
		return sum.add(new BigInteger(""+r.nextInt(5)+1));
	}
	
	/**Function that generates the r value which should be 
	 * coprime of q and r */
	public BigInteger gen_rvalue()
	{
		Random rand = new Random();
		BigInteger r;
		do {
            r = q.subtract(new BigInteger(rand.nextInt(1000) + ""));
        } while ((r.compareTo(new BigInteger("0")) > 0) && (q.gcd(r).intValue() != 1));
		
		return r;
	}
	
	/** Encryption function used to encrypt the input string 
	 *  using the public encryption key. Post Condition is function returns a biginteger
	 *  which includes the encrypted key vaue
	 *  Running time for all the cases: Big Theta(n2)
	 *   */
	public BigInteger encrpytion(String input_str)
	{
		inputSize = input_str.length()*8;
		BigInteger encrpytedmsg = new BigInteger("0");
		int count =7;
		int[] input = new int[8];
		Arrays.fill(input,0);
				
		for(int i=0;i<input_str.length();i++)
		{
			int x = input_str.charAt(i);
			while(x!=0)
			{
				input[count] = x%2;
				x = x/2;
				count = count -1;
			}
			
			
			for(int j=0;j<8;j++)
			{
				BigInteger value = b.getdata(i * 8 + j);
				if(input[j]!=0)
				{
					encrpytedmsg = encrpytedmsg.add(value);
				}
			}
			Arrays.fill(input,0);
			count =7;
		}
		
		return encrpytedmsg;		
	}
		
	/** Function that performs the decryption process using the private key
	 *  The running time of the function is BigTheta(N2) as we have 2 loops one decoding the 
	 *  encrypted key into binary equivalent and other loop converting the binary code to a character. 
	 *  All the character is concatenated to form a single string and is returned to the main fucntion
	 *  Post Condition: String is returned
	 *  Pre Condition: Binary equivalent is converted to string*/
	public String decryption(BigInteger encrypt)
	{
			
		BigInteger b1 = encrypt.mod(q);
        BigInteger b2 = r.modInverse(q);
        BigInteger b3 = b1.multiply(b2);
        BigInteger value = b3.mod(q);

        
        int[] bitmask = new int[inputSize];

        int i = inputSize -1;
        while(value.compareTo(BigInteger.ZERO) != 0)
        {
        	BigInteger encrpt_key = w.getdata(i);
        	if(value.compareTo(encrpt_key) >=0)
        	{
        		value = value.subtract(encrpt_key);
        		bitmask[i] = 1;
        	}
        	i=i-1;
        }
               
        int len = bitmask.length;
        String concat="";
        double x=0;
        while(len > 0)
        {
        	len = len - 1;   
        	if(len%8==0)
        	{
        		concat = concat + (char)x;
        		x=0;
        	}
        	else
        	{
        		if(bitmask[len]==1)
        		{
        			x = x+Math.pow(2,8-(len%8)-1);
        		}
        	}     	
        }
        
        StringBuffer buffer = new StringBuffer(concat);
        StringBuffer x1 = buffer.reverse();  
        return x1.toString();
	}
	
	/** Displays the content of the list
	 * Running time is BigTheta(n)
	 */
	public void toString(List head)
	{
		List temp = head;
		
		if(head==null){
			return;
		}
		
		while(temp!=null)
		{
			System.out.println(temp.x);
			temp = temp.next;
		}
	}
	
	
	public static void main(String[] args)
	{
		Scanner keyword =null;
		keyword = new Scanner(System.in);
		System.out.println("Enter a string and I will encrypt it as single large integer");
		String input_str = keyword.nextLine();
		MerkleHellmanSolution m1 = new MerkleHellmanSolution();
		
		if(input_str.length() < 80)
		{
			m1.gen_encrpytseq(input_str.length());
		}
		else
		{
			System.out.println("entered string is greater than 80 characters");
			System.exit(0);
		}
	
		keyword.close();
		m1.gen_decrptseq();
		BigInteger encrypted = m1.encrpytion(input_str);
		System.out.println(input_str+"  is encrypted as:");
		System.out.println(encrypted);
		System.out.println("Result of decryption: " + m1.decryption(encrypted));
	}
}
