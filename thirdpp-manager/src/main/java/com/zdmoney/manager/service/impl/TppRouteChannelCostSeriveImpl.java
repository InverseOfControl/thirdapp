package com.zdmoney.manager.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.mapper.TppRouteChannelCostMapper;
import com.zdmoney.manager.models.TppRouteChannelCost;
import com.zdmoney.manager.service.TppRouteChannelCostService;
import com.zdmoney.manager.utils.StringUtil;

@Service
public class TppRouteChannelCostSeriveImpl implements TppRouteChannelCostService {

    @Autowired
    private TppRouteChannelCostMapper ttpCostMapper;
    
    @Override
    public List<Map> getCostInfoList(Map<String, Object> params) {
        return ttpCostMapper.getCostInfoList(params);
    }

    @Override
    public TppRouteChannelCost getCostInfoByID(Long parseLong) {
        return ttpCostMapper.getCostInfoByID(parseLong);
    }

    @Override
    public int insert(TppRouteChannelCost info) {
        return ttpCostMapper.insert(info);
    }

    @Override
    public int update(TppRouteChannelCost info) throws Exception {
        if(info.getId() == null)
            throw new Exception("update时主键不能为空");
        return ttpCostMapper.update(info);
    }

    @Override
    public int updateAll(TppRouteChannelCost info) throws Exception {
        if(info.getId() == null)
            throw new Exception("update时主键不能为空");
        return ttpCostMapper.updateAll(info);
    }

    @Override
    public void delete(String[] ids) throws Exception{
        if(ids == null){
            throw new Exception("delete时主键不能为空");
        }
        ttpCostMapper.delete(ids);
    }

    @Override
    public String checkSection(TppRouteChannelCost info) throws Exception{
        String str = null;
        //类型为固定,金额范围有值需校验
        if(("1").equals(info.getCostType()) && StringUtil.isEmpty(info.getMinAmount())){
        	return str;
        }
        if(info.getMinAmount() != null && info.getMinAmount().compareTo(new BigDecimal(0)) > 0){
            if(info.getMaxAmount() != null){
                if(info.getMinAmount().compareTo(info.getMaxAmount()) >= 0){//
                    str = "输入的区间非法";
                    return str;
                }
            }
        }else{ 
            str = "请输入最低金额(金额需大于0)";
            return str; 
        }
        
        List<TppRouteChannelCost> tppRouteChannelCosts = ttpCostMapper.getSection(info);

        if(tppRouteChannelCosts != null && tppRouteChannelCosts.size() >1 ){
            str = "输入的区间非法";
            return str;
        }else if(tppRouteChannelCosts != null && tppRouteChannelCosts.size()==1 ){
            if(info.getId() == null){//新增
                str = "输入的区间非法";
                return str;
            }
        }
        return str;
    }
    
    public String checkMaxAmount(TppRouteChannelCost info) throws Exception{
        if(info.getHasLimitAmount().equals("1")){//封顶
            if(info.getLimitAmount() != null &&(info.getLimitAmount().compareTo(new BigDecimal(0)) > 0)){
                return null;
            }else{
               return "请输入封顶金额"; 
            }
        }
        
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public String checkData(TppRouteChannelCost info) throws Exception {
        String str = Validate.getInstance().validate(info,com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
        if(!StringUtil.isEmpty(str))
            return str;
        str = checkAmount(info);
        if(!StringUtil.isEmpty(str))
            return str;
        str = checkSection(info);
        if(!StringUtil.isEmpty(str))
            return str;
        return str;
        
    }

    @Override
    public String checkAmount(TppRouteChannelCost info) throws Exception {
        if("1".equals(info.getCostType())){ //固定
            if(info.getFixedAmount() == null){
                return "请输入固定金额";
            }
        }else if("2".equals(info.getCostType())) { //百分比
            if(info.getPercent() == null){
                return "请输入百分比率";
            }
            String str = checkMaxAmount(info);
            if(!StringUtil.isEmpty(str))
                return str;
        }
        return null;
    }

}
