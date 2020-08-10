package com.example.apiconfig;

import com.example.study.service.IEmpService;
import com.example.study.service.impl.EmpServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.rpc.model.ApplicationModel;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 11:02 2020/8/8
 * @Modified By:
 */
public class Prodiver {
	public static void main(String[] args) {

		//服务实现
		IEmpService empService = new EmpServiceImpl();

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
		ServiceConfig<IEmpService> service = new ServiceConfig<>();
		ApplicationModel.getConfigManager().setApplication(application);

		service.setRegistry(registry);
		service.setProtocol(protocol);
		service.setInterface(IEmpService.class);
		service.setRef(empService);
		service.setVersion("1.0.0");

		//暴露及注册服务
		service.export();

		try {
			Thread.sleep(120000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
