package com.example.xmlconfig;

import com.example.study.service.IEmpService;
import com.example.study.service.pojo.Emp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 21:15 2020/8/8
 * @Modified By:
 */
public class Consumer {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
		context.start();
		IEmpService service = (IEmpService) context.getBean("empService");
		Emp emp = service.findById(1);
		System.out.println(emp);

	}
}
