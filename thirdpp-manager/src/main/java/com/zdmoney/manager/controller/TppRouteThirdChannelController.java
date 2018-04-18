package com.zdmoney.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.models.TppRouteThirdChannel;
import com.zdmoney.manager.service.TppRouteThirdChannelService;
import com.zdmoney.manager.utils.JackJsonUtil;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.RouteTransformUtil;
import com.zdmoney.manager.utils.StringUtil;

@Controller
@RequestMapping(value = "/bim/routeInfo")
public class TppRouteThirdChannelController extends TppBaseController {
    
    private final Logger log = Logger.getLogger(TppRouteThirdChannelController.class);
    
    @Autowired
    private TppRouteThirdChannelService trcService;
    
    @RequestMapping(value = "/channelList")
    public String view(HttpServletRequest request,ModelMap modelMap) {
      return "/bim/routeInfo/channelList";
    }
    
    @RequestMapping(value = "/businessPriorityListData")
    @ResponseBody
    public String priorityList( HttpServletRequest request,ModelMap modelMap) {
        return this.channelList(request, modelMap, "2");
    }
    
    
    //
    @RequestMapping(value = "/businessPriorityInfoEditUI")
    public String infoAppsUIChannel(HttpServletRequest request, ModelMap modelMap) {
        try {
            return "bim/routeInfo/businessPriorityListEdit";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }
    }
    
    
    @RequestMapping(value = "/channelListData")
    @ResponseBody
    public String channelListData(HttpServletRequest request,ModelMap modelMap) {
        return this.channelList(request, modelMap, "1");
    }
    
    @SuppressWarnings("unused")
    @RequestMapping(value = "/channelListEditUI/{id}")
    public String infoAppsUIChannel(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap) {

        try {
            TppRouteThirdChannel info = null;
            //权限验证
            Map<String, Object> pam = new HashMap<String, Object>();
            if (!StringUtil.isEmpty(id)) {

                info = trcService.selectChannelInfoByID(StringUtil.parseLong(id));
            }
          
            modelMap.put("pChannelInfo", info);

            return "bim/routeInfo/channelListEdit";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }
    }
    
    //保存次数
    @RequestMapping(value = "/channelListUpdate",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppUpate(@ModelAttribute("channelListSave") TppRouteThirdChannel info,HttpServletRequest request, ModelMap modelMap) {
        
        return this.CURD(info, request, modelMap,"0002");
       
    }
    
    @RequestMapping(value = "/channelListSave",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave(@ModelAttribute("channelListSave") TppRouteThirdChannel info,HttpServletRequest request, ModelMap modelMap) {
       
        return this.CURD(info, request, modelMap,"0001");
       
    }
    
    /**
     * 改变系统状态
     */
    @RequestMapping(value = "/updateChannelStatus/{id}/{isAvailable}")
    @ResponseBody
    public String updateIsActive(@PathVariable("id") String id,@PathVariable("isAvailable") String isAvailable,HttpServletRequest request, ModelMap modelMap) {
        TppRouteThirdChannel info = new TppRouteThirdChannel();
        try{
            info.setId(StringUtil.parseInteger(id));
            info.setIsAvailable(isAvailable);
            if(!StringUtil.isEmpty(isAvailable)){
               if(isAvailable.equals("2")){//禁用是通道优先级设置为0
                   info.setPriority(0);
               }
            }
            return this.CURD(info, request, modelMap,"0004");
        }catch(Exception e){
            log.error(e.getMessage(), e);
            modelMap.put("errorMsg", e.getMessage());
             
        }
        return null;
         
    }
    
    @RequestMapping(value = "/businessPriorityInfoSave",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave( String channelInfos,HttpServletRequest request, ModelMap modelMap) {
        System.out.println(channelInfos);
        String repStr = "";
        try {
            List<TppRouteThirdChannel> thirdChannels = JackJsonUtil.strToList(channelInfos, TppRouteThirdChannel.class);
            if(thirdChannels != null && thirdChannels.size()>0){
                for (TppRouteThirdChannel tppRouteThirdChannel : thirdChannels) {
                    repStr = this.CURD(tppRouteThirdChannel, request, modelMap,"0004");
                }
            }
            System.out.println(thirdChannels.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } 
        return repStr;
       
    }
    
    /**
     * 
     * 
     * @param info
     * @param request
     * @param modelMap
     * @param type 0001-insert 0002-update 0003-delete other-0004
     * @return
     */
    @SuppressWarnings({ "unused", "unchecked" })
    private String CURD( TppRouteThirdChannel info,HttpServletRequest request, ModelMap modelMap, String type){
        String response =  "";
        boolean status = true;
        JSONObject jb=new JSONObject();
        
        try {
                if("0001".equals(type)){ //save
                    response = Validate.getInstance().validate(info,com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
                    if(StringUtil.isEmpty(response)){//校验通过
                        if(!StringUtil.isEmpty(info.getThirdTypeNo())){
                            if(!trcService.existChannel(info)){
                                int cout = trcService.insert(info);
                            }else{
                                response = "该通道已存在";
                                status = false;
                            }
                            
                        }
                    }else{
                        status = false;
                    }
                }else if("0002".equals(type)){//update
                    response = Validate.getInstance().validate(info,com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
                    if(StringUtil.isEmpty(response)){//校验通过
                        TppRouteThirdChannel tempDto = trcService.getChannelByThirdTypeNo(info.getThirdTypeNo());
                        
                        int uId = info.getId();
                        if(trcService.existChannel(info) && tempDto != null && tempDto.getId() != uId){
                            response = "该通道已存在";
                            status = false;
                        }else{
                            if(!StringUtil.isEmpty(info.getId())){
                                trcService.update(info);
                            }
                        }
                    }else{
                        status = false;
                    }
                }else if("0004".equals(type)){
                    trcService.update(info);
                }
        } catch(Exception e){
            response="操作失败";
            status = false;
            log.error(e.getMessage(), e);
            //modelMap.put("errorMsg", e.getMessage());
            //return "/errorpage/error";
        }finally{
            jb.put("valmsg",response);
            jb.put("status",status);
        }
        return jb.toString();
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String channelList(HttpServletRequest request,ModelMap modelMap,String type) {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            if("1".equals(type)){
                super.datagridParam(request, params);
            }else if("2".equals(type)){//优先级
                params.put("isAvailable", "1");
            }
            //分页         
            //查询条件
            //封装查询条件
            // 2.遍历增加及转换自定义内容
            List<Map> rows = trcService.getRouteChannelInfoList(params);
            for (Map d : rows) {
                if(!StringUtil.isEmpty(d.get("IS_AVAILABLE"))){
                    if(d.get("IS_AVAILABLE").toString().equals("1")){//是
                        d.put("AC","<a href='javascript:void(0);' style='text-decoration:none' onClick=\"updateFlag('"+ d.get("ID")+ "','"+ "2"+ "');\">禁用 </a>");
                    }
                    else if(d.get("IS_AVAILABLE").toString().equals("2")){//否
                        d.put("AC","<a href='javascript:void(0);' style='text-decoration:none'  onClick=\"updateFlag('"+ d.get("ID")+ "','"+ "1"+ "');\">启用 </a>");
                    }
             
                };
            }
            RouteTransformUtil.convertIsAvailable(rows);

            // 3.组合输出列表查询所需数据
            // JsonConfig 用于解析转换的设置
            JsonConfig config = new JsonConfig();
            JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
            JSONArray json_rows = JSONArray.fromObject(rows, config);
            String json = "{\"total\":\"" + rows.size() + "\",\"rows\":" + json_rows.toString()
                          + "}";
            return json;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.out.println(e);
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }
    }

}
