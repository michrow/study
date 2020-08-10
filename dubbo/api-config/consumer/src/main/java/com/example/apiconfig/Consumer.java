package com.example.apiconfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.study.service.IEmpService;
import com.example.study.service.impl.EmpServiceImpl;
import com.example.study.service.pojo.Emp;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MethodConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.model.ApplicationModel;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 11:33 2020/8/8
 * @Modified By:
 */
public class Consumer {
	public static void main(String[] args) {

		//当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("apiconfigProvider");

		//连结注册中心
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://localhost:2181");

		//服务提供者协议
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");

		//服务提供者暴露服务配置
		ReferenceConfig<IEmpService> reference = new ReferenceConfig<>();
		ApplicationModel.getConfigManager().setApplication(application);

//		调用方法，设置
//		List<MethodConfig> methods = new ArrayList<>();
//		MethodConfig method = new MethodConfig();
//		method.setName("findById");
//		method.setTimeout(10000);
//		method.setRetries(1);
//		methods.add(method);
//		reference.setMethods(methods);

		reference.setRegistry(registry);
		reference.setInterface(IEmpService.class);
		reference.setInterface(IEmpService.class);
		reference.setVersion("1.0.0");

		// 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
		IEmpService service = reference.get();
		Emp emp = service.findById(1);
		System.out.println(emp);

	}
}
