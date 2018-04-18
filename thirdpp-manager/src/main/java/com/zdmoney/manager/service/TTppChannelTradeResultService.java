package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

public interface TTppChannelTradeResultService {
	List select_tppChannelTradeResultList(Map<String, Object> params);
    
    Integer select_tppChannelTradeResultList_count(Map<String, Object> params);
    
    List select_tppChannelTradeResultByTransferFolw(Map<String, Object> params);
    
    List select_tppChannelTradeResultByReqId(Map<String, Object> params);
}
