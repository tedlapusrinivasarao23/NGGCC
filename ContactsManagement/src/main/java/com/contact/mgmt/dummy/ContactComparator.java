package  com.contact.mgmt.dummy;

import java.util.Comparator;

import com.contact.mgmt.bos.AddContact;

public class ContactComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		AddContact s1=(AddContact)o1;  
		AddContact s2=(AddContact)o2;  
		String sf=s1.getFirstName();
		String sf1=s2.getFirstName();
		int res = 0;
		try
		{
		 res=sf.compareTo(sf1);
		}
		catch(Exception e)
		{
			
		}
		if(res!=0)
		{
			System.out.print(res);
			return res;
		}
		else
		{
			return -1;
		}
		
		
		
			} 
}
	





