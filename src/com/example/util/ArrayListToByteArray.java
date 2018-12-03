package com.example.util;

import java.util.List;

public class ArrayListToByteArray {
   public static byte[]listTobyteArray(List list){
	   byte []buffer=new byte[list.size()];
	    for(int i=0;i<buffer.length;i++){
	    	buffer[i]=(byte) list.get(i);
	    }
	    return buffer;
   }
}
