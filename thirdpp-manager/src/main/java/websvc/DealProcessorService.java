package websvc;

import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * 统一接口
 */
@WebService(targetNamespace = "http://websvc/", name = "DealProcessorService")
public interface DealProcessorService {
	/**
	 * 统一接口
	 * @version 1.00
	 * @param in0
	 * @param in1
	 * @return String
	 * @see
	 */
	public String dispatchCommand(
			@WebParam(name = "arg0", targetNamespace = "http://websvc/")
			String in0,
			@WebParam(name = "arg1", targetNamespace = "http://websvc/")
			String in1);

}
