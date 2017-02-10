package com.fyj.utils;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	
	protected final static Logger logger = LoggerFactory.getLogger("APP");

	
    /** 
    * @Title: main 
    * @Description: test log
    * @param args
    * @return void    返回类型 
    */
    public static void main( String[] args )
    {
    	
    	System.out.println(TimeUnit.HOURS.toMillis(1));
    	
    	logger.info("=====test={},","asdf");
        System.out.println( "Hello World!" );
    }
}
