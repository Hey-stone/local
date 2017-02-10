package com.fyj.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @Title: CloneUtils.java 
 * @Package com.fyj.utils  
 * @author 冯亚军
 * @date 2017年2月8日下午12:11:26
 * @Description: 深拷贝
 * @version V1.0   
 */
public class CloneUtils {

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T obj){
		
		T clonedObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			clonedObj = (T) ois.readObject();
			ois.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return clonedObj;
	}
}
