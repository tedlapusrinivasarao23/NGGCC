package com.contact.mgmt.dummy;

import java.util.Comparator;

import com.contact.mgmt.bos.AddContact;

public class KmComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		AddContact s1=(AddContact)o1;  
		AddContact s2=(AddContact)o2;  
		if(s1.getId()==s2.getId())  
		{
			
			return 0; 
		}
		if(s1.getKm()>s2.getKm())  
		{
		
			return 1;  
		}
		else
		{
			return -1;
		}
		
			 
			} 
}
	





