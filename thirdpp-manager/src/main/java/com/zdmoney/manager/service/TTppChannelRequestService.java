package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

public interface TTppChannelRequestService {
	
	List select_tppChannelTRequestList(Map<String, Object> params);
    
    Integer select_tppChannelTRequestList_count(Map<String, Object> params);
    
    List select_tppChannelTRequestByTransferFlow(Map<String, Object> params);
}
