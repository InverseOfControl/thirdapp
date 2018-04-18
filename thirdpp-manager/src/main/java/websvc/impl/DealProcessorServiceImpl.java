package websvc.impl;

import java.lang.reflect.Method;

import websvc.DealProcessorService;

public class DealProcessorServiceImpl implements DealProcessorService {
	private static final String PKG_NAME = "websvc.servant";
	private static final String METHODNAME = "execute";

	/**
	 * 统一接口
	 * 
	 * @version 1.00
	 * @param dispatchCommandRequest
	 * @return DispatchCommandResponse
	 * @see
	 */
	public String dispatchCommand(String in0,String in1) {
		try {
			String wbSvrCode = in0;

			String out = null;
			//调用服务分支
			Class<?> svcClass = Class.forName(PKG_NAME +".THUMB_" +wbSvrCode);
			Object[] args = new Object[]{in0,in1};
			Class[] argsClass = new Class[2];
			for (int i = 0, j = 2; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
			Method method = svcClass.getMethod(METHODNAME, argsClass);

			out = (String)method.invoke(null, args);
			
			

			return out;

		} catch (Exception e) {
			return "";
		}
	}

}
