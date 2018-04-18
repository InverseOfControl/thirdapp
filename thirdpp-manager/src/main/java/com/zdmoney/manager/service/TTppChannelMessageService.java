package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

public interface TTppChannelMessageService {
	List select_tppChannelTMessageList(Map<String, Object> params);
    
    Integer select_tppChannelTMessageList_count(Map<String, Object> params);
    
    List select_tppChannelTMessageByReqId(Map<String, Object> params);
}
