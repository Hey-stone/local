package com.fyj.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @Title: CSVUtils.java 
 * @Package com.coolchuan.undertow.timestore  
 * @author 冯亚军
 * @date 2017年1月11日上午10:28:39
 * @Description: cvs操作
 * @version V1.0   
 */
public class CSVUtils {
	
	/** 
	* @Title: creatCVSFile 
	* @Description: 写
	* @param exportData  要写入的数据list
	* @param filePath  路径
	* @param fileName  文件名
	* @param writeTile  title
	* @throws IOException
	* @return void    返回类型 
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void creatCSVFile(List exportData , String filePath,String fileName,String [] writeTile) throws IOException{
		File file  = new File(filePath + File.separator + fileName);
		Writer writer = new FileWriter(file); 
		
		CSVWriter csvWriter = new CSVWriter(writer, ',');
		csvWriter.writeNext(writeTile);
		csvWriter.writeAll(exportData);
		csvWriter.close();
	}
	
	/** 
	* @Title: readCVSFile 
	* @Description: 读cvs文件
	* @param filePath
	* @param fileName
	* @param separator
	* @throws IOException
	* @return void    返回类型 
	*/
	public static void readCSVFile(String filePath,String fileName,char separator) throws IOException{
		File file = new File(filePath + File.separator + fileName);
		FileReader fileReader = new FileReader(file);
		CSVReader csvReader = new CSVReader(fileReader,separator);
		String[] titles = csvReader.readNext();
		if(titles != null && titles.length > 0){  
            for(String str : titles)  
                if(null != str && !str.equals(""))  
                    System.out.print(str + " , ");  
            System.out.println("\n---------------");  
        }  
		List<String[]> list = csvReader.readAll();  
        for(String[] ss : list){  
            for(String s : ss)  
                if(null != s && !s.equals(""))  
                    System.out.print(s + " , ");  
            System.out.println();  
        }  
        csvReader.close(); 
	}
	
	/** 
	* @Title: createWebBook 
	* @Description: Excel 创建HSSFWorkbook
	* @return
	* @return HSSFWorkbook    返回类型 
	*/
	public static HSSFWorkbook createWebBook(){
		HSSFWorkbook wb = new HSSFWorkbook();
		return wb;
	}
	
	/** 
	* @Title: createHSSFSheet 
	* @Description: 创建sheet
	* @param wb
	* @param sheetName  工作薄的名称
	* @return
	* @return HSSFSheet    返回类型 
	*/
	public static HSSFSheet createHSSFSheet(HSSFWorkbook wb, String sheetName){
		HSSFSheet sheet = wb.createSheet(sheetName);  
		return sheet;
	}
	
	
	
	/** 
	* @Title: writeToExcel 
	* @Description: 只有一个工作簿
	* @param exportData
	* @param sheetName
	* @return void    返回类型 
	*/
	public static void writeToExcelOnlyOneSheet(List exportData, String sheetName,String[] title,String fullPath){
        // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = createWebBook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
       writeToExcelSheet(wb,sheetName,title,exportData);
        
		writeExcelToFile(wb, fullPath);
	}
	
	/** 
	* @param exportData 
	 * @param title 
	 * @param sheetName 
	 * @param wb 
	 * @Title: writeToExcelSheet 
	* @Description: 写数据到每个sheet
	* <Li>  如果要写多个sheet的话要在循环外面创建HSSFWorkbook
	* @return void    返回类型 
	*/
	public static void writeToExcelSheet(HSSFWorkbook wb, String sheetName, String[] title, List exportData) {

		HSSFSheet sheet =  createHSSFSheet(wb, sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		HSSFRow row = sheet.createRow((int) 0);  
		// 第四步，创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle();  
		// 创建一个居中格式  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = null;
		for(int i = 0 ; i < title.length ; i++){
			cell = row.createCell((short) i); 
			cell.setCellValue(title[i]);  
			cell.setCellStyle(style);  
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		int num = 1;
		int size = exportData.size();
		for(int i = 0;i < size; i++){
			String[] values = (String[]) exportData.get(i);
			row = sheet.createRow(num);  
			for(int p = 0 ; p < values.length ; p++ ){
				row.createCell((short) p).setCellValue(values[p]);  
			}
			num ++;
		}

	}

	/** 
	* @Title: writeExcelToFile 
	* @Description: 写成文件
	* @param wb
	* @param fullPath
	* @return void    返回类型 
	*/
	public static void writeExcelToFile(HSSFWorkbook wb,String fullPath){
		try  
        {  
            FileOutputStream fout = new FileOutputStream(fullPath);  
            wb.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
	}
	
/*	public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
        String fileName = "Operator.doc".toString(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
	

}
