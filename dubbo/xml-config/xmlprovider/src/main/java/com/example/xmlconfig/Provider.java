package com.example.xmlconfig;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 21:10 2020/8/8
 * @Modified By:
 */
public class Provider {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
		context.start();
		System.in.read();
	}
}
