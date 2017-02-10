package com.fyj.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Title: FileUtil.java 
 * @Package com.fyj.utils  
 * @author 冯亚军
 * @date 2017年2月10日上午9:46:39
 * @Description: 文件写入读取
 * @version V1.0   
 */
public class FileUtil {

	// 从给定位置读取Json文件
	public static String readJson(String path) {
		// 从给定位置获取文件
		File file = new File(path);
		BufferedReader reader = null;
		// 返回值,使用StringBuffer
		StringBuffer data = new StringBuffer();
		//
		try {
			reader = new BufferedReader(new FileReader(file));
			// 每次读取文件的缓存
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			// 关闭文件流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
			}
		}
		return data.toString();
	}

	// 给定路径与Json文件，存储到硬盘
	public static void writeJson(String fullPath, Object json) {
		BufferedWriter writer = null;
		//File file = new File(path + fileName + ".json");
		File file = new File(fullPath);
		// 如果文件不存在，则新建一个
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 写入
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("文件写入成功！");
	}
	
	public static void main(String[] args){
		Map<Integer, int[]> maps = new HashMap<>();
		for(int i = 0; i < 10;i++){
			int[] apps= new int[10];
			for(int j = 0 ; j < 10 ; j++){
				apps[j] = j * 10;
			}
			maps.put(i, apps);
		}
		writeJson("D:\\test.json", JSON.toJSON(maps));
		String json = readJson("D:\\test.json");
		
		JSONObject object = JSONObject.parseObject(json);
		
		System.out.println(JSONViewTool.stringToJSON(json));
		
		for (String key : object.keySet() ) {
			System.out.println("key " + key);
			System.out.println("value " + object.get(key));
		}
	}

}
