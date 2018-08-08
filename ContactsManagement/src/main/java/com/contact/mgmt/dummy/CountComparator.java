package com.contact.mgmt.dummy;

import java.util.Comparator;

public class CountComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		GroupCount s1=(GroupCount)o1;  
		GroupCount s2=(GroupCount)o2;  
		if(s1.getCount()==s2.getCount())  
			
			return 0;  
			else if(s1.getCount()>s2.getCount())  
			return 1;  
			else  
			return -1;  
			} 
}
	


