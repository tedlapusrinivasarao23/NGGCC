package com.contact.mgmt.dummy;

import java.util.Comparator;

import com.contact.mgmt.bos.GroupRef;

public class CountGroupListComparator implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			GroupRef s1=(GroupRef)o1;  
			GroupRef s2=(GroupRef)o2;  
			if(s1.getCount()==s2.getCount())  
				
				return 0;  
				else if(s1.getCount()>s2.getCount())  
				return 1;  
				else  
				return -1;  
				} 
	
}
