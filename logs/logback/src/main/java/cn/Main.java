package cn;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 18:02 2020/7/13
 * @Modified By:
 */
public class Main {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Main.class);
		logger.error("error");
		logger.warn("warn");
		logger.info("info");
		logger.debug("debug");

		Server server = new Server();
		server.showLog();

	}
}
