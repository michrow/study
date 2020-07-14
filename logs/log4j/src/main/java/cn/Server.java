package cn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 23:46 2020/7/13
 * @Modified By:
 */
public class Server {

	private Log logger = LogFactory.getLog(Main.class);

	public void showLog(){
		logger.info("this is a commons log4j start");
		logger.error("error");
		logger.warn("warn");
		logger.info("info");
		logger.debug("debug");
		logger.info("this is a commons log4j end!");
	}
}
