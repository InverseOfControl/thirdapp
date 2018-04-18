package com.zdmoney.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppRouteThirdChannelMapper;
import com.zdmoney.manager.models.TppRouteThirdChannel;
import com.zdmoney.manager.service.TppRouteThirdChannelService;

@Service
public class TppRouteThirdChannelSerivceImpl implements TppRouteThirdChannelService {
    
    @Autowired
    TppRouteThirdChannelMapper channelMapper;

    @Override
    public TppRouteThirdChannel selectChannelInfoByThirdTypeNo(String ThirdTypeNo) throws Exception {
        List<TppRouteThirdChannel> thirdChannels = channelMapper.selectChannelInfoByThirdTypeNo(ThirdTypeNo);
        if(thirdChannels != null && !thirdChannels.isEmpty() ){
            if(thirdChannels.size()==1){
                return thirdChannels.get(0);
            }else{
                throw new Exception("第三方支付通道信息有误"); 
            }
        }
        return null;
    }

    @Override
    public boolean hasChannel(String thirdTypeNo) throws Exception {
        List<TppRouteThirdChannel> thirdChannels = channelMapper.selectChannelInfoByThirdTypeNo(thirdTypeNo);
        if(thirdChannels ==null || thirdChannels.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public List<Map> getRouteChannelInfoList(Map<String, Object> params) {
        return channelMapper.getRouteChannelInfoList(params);
    }

    @Override
    public TppRouteThirdChannel selectChannelInfoByID(Long parseLong) {
        return channelMapper.selectChannelInfoByID(parseLong);
    }
    
    @Override
    public TppRouteThirdChannel getChannelByThirdTypeNo(String thirdTypeNo) {
        return channelMapper.getChannelByThirdTypeNo(thirdTypeNo);
    }

    @Override
    public int update(TppRouteThirdChannel info) throws Exception {
        if(info.getId() == null)
            throw new Exception("update时主键不能为空");
        return channelMapper.update(info);
    }

    @Override
    public int insert(TppRouteThirdChannel info) {
        return channelMapper.insert(info);
    }

    @Override
    public boolean existChannel(TppRouteThirdChannel info) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("thirdTypeNo", info.getThirdTypeNo());
        List<Map> maps = channelMapper.getRouteChannelInfoList(params);
        if(maps == null || maps.isEmpty()){
            return false;
        }
        return true;
    }
    
    

    

}
