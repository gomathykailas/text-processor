package com.flytxt.parser.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;

public class TpString {
	private static final byte smallA = 'a';
	private static final byte smallZ = 'z';
	private static final byte capsA = 'A';
	private static final byte capsZ = 'Z';
	private static final byte space = ' ';
	private static final byte deltaFromA2a = capsA-smallA;
	
	public int length(byte[] data, Marker m, MarkerFactory mf) {
		return m.length; // will it come index-length
	}

	public boolean isNull(byte[] data, Marker m, MarkerFactory mf) {
		return m == null || m.length == 0;
	}

	public boolean endsWtihIgnore(byte[] data, Marker dataMarker, byte[] prefix, Marker prefixMarker, MarkerFactory mf) {
		
		byte[] d1 = (dataMarker.getData() != null)? dataMarker.getData():data;
		byte[] d2 = (prefixMarker.getData() != null)? prefixMarker.getData():prefix;
		Marker lowerCase = toLowerCase(d1, dataMarker, mf);
		Marker lowerCase2 = toLowerCase(d2, prefixMarker, mf);
		return endsWtih(d1, lowerCase, d2, lowerCase2, mf);
	}
	
	public boolean endsWtih(byte[] data, Marker dataMarker, byte[] prefix, Marker prefixMarker, MarkerFactory mf) {
		
		byte[] data1 = (dataMarker.getData() != null)? dataMarker.getData():data;
		byte[] data2 = (prefixMarker.getData() != null)? prefixMarker.getData():prefix;
		
		int dataIndex = dataMarker.index + dataMarker.length - 1;
		int prefixIndex = prefixMarker.index + prefixMarker.length - 1;
		for (int i = prefixMarker.index + prefixMarker.length; i > prefixMarker.index; i--) {
			if (data1[dataIndex] != data2[prefixIndex])
				return false;
			dataIndex--;
			prefixIndex--;
		}
		return true;
	}

	public boolean startsWtih(byte[] data, Marker dataMarker, byte[] prefix, Marker prefixMarker) {
		
		byte[] data1 = (dataMarker.getData() != null)? dataMarker.getData():data;
		byte[] data2 = (prefixMarker.getData() != null)? prefixMarker.getData():prefix;
		if(dataMarker.length<prefixMarker.length)
			return false;
		int dataIndex = dataMarker.index + prefixMarker.length - 1;
		int prefixIndex = prefixMarker.index + prefixMarker.length - 1;
		for (int i = dataIndex; i >= prefixMarker.index; i--) {
			if (data1[dataIndex] != data2[prefixIndex])
				return false;
			dataIndex--;
			prefixIndex--;
		}
		return true;
	}

	public Marker toUpperCase(byte[] data, Marker m, MarkerFactory mf) {
		byte[] data1 = m.getData()==null?data:m.getData();
		byte[] dest = new byte[m.length];
		for (int i = m.index; i < m.length; i++) {
			if (data1[i] >= smallA && data1[i] <= smallZ) {
				dest[i] = (byte) (data1[i]+deltaFromA2a);
			}
		}
		return mf.createImmutable(dest, 0, m.length);
	}

	public Marker toLowerCase(byte[] data, Marker m, MarkerFactory mf) {
		byte[] data1 = m.getData()==null?data:m.getData();
		byte[] dest = new byte[m.length];
		for (int i = m.index; i < m.length; i++) {
			if (data1[i] >= capsA && data1[i] <= capsZ) {
				dest[i] = (byte) (data1[i]-deltaFromA2a);
			}
		}
		return mf.createImmutable(dest, 0, m.length);
	}

	public Marker toTitleCase(byte[] data, Marker m, MarkerFactory mf) {
		if(m.length == 0)
			return m;
		byte[] data1 = m.getData()==null?data:m.getData();
		byte[] dest = new byte[m.length];
		System.arraycopy(data, m.index, dest, 0, m.length);
		if (data1[m.index] >= 'a' && data1[m.index] <= 'z')
			dest[m.index] += deltaFromA2a;
		return mf.createImmutable(dest, 0, m.length);
	}
	
	public Marker lTrim(byte[] data, Marker m, MarkerFactory mf) {
		byte[] data1 = m.getData()==null?data:m.getData();
		 int start= m.index;
		 int end = start + m.length;
		 while ((start < end) && (data1[start] == space)) {
			 start++; 
		 }
		return mf.create(start, m.length-start);
	}
	
	public Marker rTrim(byte[] data, Marker m, MarkerFactory mf) {
		byte[] data1 = m.getData()==null?data:m.getData();
		 int start= m.index+m.length-1;
		 int dec = 0;
		 int end = m.index;
		 while ((start >= end) && (data1[start] == space)) {
			 start--;
			 dec--;
		 }
		return mf.create(m.index, m.length+dec);
	}

	public Marker trim(byte[] data, Marker m, MarkerFactory mf) {
		byte[] data1 = m.getData()==null?data:m.getData();
		 int start= m.index;
		 int end = start + m.length-1;
		 int newStart = start;
		 int dec = 0;
		 boolean headFound =  false, tailFound = false;
		 while(start <= end){
			 if(!headFound){
				 if(data1[start]== space){
					 start++;
					 dec--;
				 }
			 	else{
			 		newStart = start;
			 		headFound = true;
			 	}
			 }
			 if(!tailFound){
				 if(data1[end]== space){
					 end--;
					 dec--;
				 }
			 	else{
			 		tailFound = true;
			 	}
			 }
			 if(headFound && tailFound){
				 break;
			 }

		 }
		 int len = m.length+dec;
		 if(len<0)
			 len = 0;
		return mf.create(newStart, len);
	}
	
	public boolean contains(byte[]data1, Marker m1, byte[]data2, Marker m2, MarkerFactory mf){
		byte[] d1 = m1.getData()==null?data1:m1.getData();
		byte[] d2 = m2.getData()==null?data2:m2.getData();
		 for(int i = m1.index; i < m1.index+m1.length - m2.length+1; ++i) {
		        boolean found = true;
		        for(int j = m2.index; j < m2.index+m2.length; ++j) {
		           if (d1[i+j] != d2[j]) {
		               found = false;
		               break;
		           }
		        }
		        if (found) return true;//return i;
		     }
		   return false;//-1;  
	}
	
	public boolean containsIgnoreCase(byte[]data1, Marker m1, byte[]data2, Marker m2, MarkerFactory mf){
		byte[] d1 = m1.getData()==null?data1:m1.getData();
		byte[] d2 = m2.getData()==null?data2:m2.getData();
		Marker lowerCase = toLowerCase(d1, m1, mf);
		Marker lowerCase2 = toLowerCase(d2, m2, mf);
		return contains(lowerCase.getData(), lowerCase, lowerCase2.getData(), lowerCase2, mf);
	}
	
	public Marker extractLeading(byte[] data, Marker m1, int extractCnt, MarkerFactory mf){
	    byte[] data1 = (m1.getData()== null)?data: m1.getData();
	    if(m1.getData()== null){
	        return mf.create(m1.index+extractCnt, m1.length - extractCnt);
	    }
	    else{
	        return mf.createImmutable(data1, m1.index+extractCnt, m1.length - extractCnt);
	    }
	}
	public Marker extractTrailing(byte[] data, Marker m1, int extractCnt, MarkerFactory mf){
	        byte[] data1 = (m1.getData()== null)?data: m1.getData();
	        if(m1.getData()== null){
	            return mf.create(m1.index+m1.length-extractCnt, m1.length - extractCnt);
	        }
	        else{
	            return mf.createImmutable(data1, m1.index+m1.length -extractCnt, m1.length - extractCnt);
	        }
	    }
	public Marker merge(byte[]data1, Marker m1, byte[]data2, Marker m2, MarkerFactory mf){
		byte[] d1 = m1.getData()==null?data1:m1.getData();
		byte[] d2 = m2.getData()==null?data2:m2.getData();
		byte[] result = new byte[m1.length+m2.length];
		//TODO 
		System.arraycopy(d1, m1.index, result, 0, m1.length);
		System.arraycopy(d2, m2.index, result, m1.length, m2.length);
		return mf.createImmutable(result, 0, result.length);
	}
}
