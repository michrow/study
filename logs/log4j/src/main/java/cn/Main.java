package cn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 15:34 2020/7/13
 * @Modified By:
 */
public class Main {
	public static void main(String[] args) {
		Log logger = LogFactory.getLog(Main.class);
		logger.error("error");
		logger.warn("warn");
		logger.info("info");
		logger.debug("debug");
	}
}
