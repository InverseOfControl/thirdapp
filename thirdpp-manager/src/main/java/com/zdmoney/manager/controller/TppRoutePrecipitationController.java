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
import com.zdmoney.manager.models.TppRoutePrecipitation;
import com.zdmoney.manager.models.TppRouteThirdChannel;
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.service.TppRoutePrecipitationService;
import com.zdmoney.manager.service.TppRouteThirdChannelService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.StringUtil;

//沉淀量配置
@Controller
@RequestMapping(value = "/bim/channelInfo")
public class TppRoutePrecipitationController extends TppBaseController {

    private final Logger                 log = Logger
                                                 .getLogger(TppRoutePrecipitationController.class);
    @Autowired
    private TppRoutePrecipitationService precipitationService;
    @Autowired
    private TDictionaryService           tdSerivce;
    @Autowired
    private TppRouteThirdChannelService  tcService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/precipitationListData/{dicCode}")
    @ResponseBody
    public String infoList(@PathVariable("dicCode") String dicCode, HttpServletRequest request,
                           ModelMap modelMap) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            super.datagridParam(request, params);
            //分页         
            //查询条件
            //封装查询条件
            params.put("certificateNo", request.getParameter("certificateNo"));
            params.put("thirdTypeNo", dicCode);
            // 2.遍历增加及转换自定义内容
            List<Map> rows = precipitationService.getPrecipitationInfoList(params);
            //int count = precipitationService.getPrecipitationInfoListCount(params);

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
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/totalprecipitationListData/{dicCode}")
    @ResponseBody
    public String infoListTotal(@PathVariable("dicCode") String dicCode,
                                HttpServletRequest request, ModelMap modelMap) {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            this.datagridParam(request, params);
            //分页         
            //查询条件
            //封装查询条件
            params.put("thirdTypeNo", dicCode);
            // 2.遍历增加及转换自定义内容
            List<Map> rows = tcService.getRouteChannelInfoList(params);
            //int count = precipitationService.getPrecipitationInfoListCount(params);

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
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }
    }

    //修改总沉淀量
    @RequestMapping(value = "/pChannelInfoEditUI/{id}/{dicCode}/{parentId}")
    public String infoAppsUIChannel(@PathVariable("id") String id,
                                    @PathVariable("dicCode") String dicCode,
                                    @PathVariable("parentId") String parentId,
                                    HttpServletRequest request, ModelMap modelMap) {

        try {
            TppRouteThirdChannel info = null;
            //权限验证
            Map<String, Object> pam = new HashMap<String, Object>();
            pam.put("dicCode", dicCode);
            pam.put("parentId", parentId);
            if (!StringUtil.isEmpty(id)) {

                info = tcService.selectChannelInfoByID(StringUtil.parseLong(id));
            }

            if (null == info) {

                String dicName = tdSerivce.getDicName(pam);
                info = new TppRouteThirdChannel();
                info.setThirdTypeName(dicName);

            }
            info.setThirdTypeNo(dicCode);
            modelMap.put("pChannelInfo", info);

            return "bim/channelInfo/precipitationInfoEdit1";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/totalPrecipitationInfoSave", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave1(@ModelAttribute("totalPrecipitationInfoSave") TppRouteThirdChannel info,
                               HttpServletRequest request, ModelMap modelMap) {

        try {
            JSONObject jb = new JSONObject();
            String response = Validate.getInstance().validate(info,com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
            boolean status = false;

            if (StringUtil.isEmpty(info.getId())) {

            } else {
                tcService.update(info);
                response = "操作成功";
                status = true;
            }
            jb.put("valmsg", response);
            jb.put("status", status);
            return jb.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }
    }

    @RequestMapping(value = "/precipitationInfoEditUI/{id}/{dicCode}/{parentId}")
    public String infoAppsUI(@PathVariable("id") String id,
                             @PathVariable("dicCode") String dicCode,
                             @PathVariable("parentId") String parentId, HttpServletRequest request,
                             ModelMap modelMap) {

        TppRoutePrecipitation info = null;
        //权限验证
        Map<String, Object> pam = new HashMap<String, Object>();
        pam.put("dicCode", dicCode);
        pam.put("parentId", parentId);
        if (!StringUtil.isEmpty(id)) {

            info = precipitationService.selectPrecipitationInfoByID(StringUtil.parseLong(id));
        }

        if (null == info) {

            String dicName = tdSerivce.getDicName(pam);
            info = new TppRoutePrecipitation();
            info.setThirdTypeName(dicName);

        }
        info.setThirdTypeNo(dicCode);
        modelMap.put("precipitationInfo", info);

        return "bim/channelInfo/precipitationInfoEdit";
    }

    @RequestMapping(value = "/precipitationInfoSave", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave(@ModelAttribute("precipitationInfoSave") TppRoutePrecipitation info,
                              HttpServletRequest request, ModelMap modelMap) {

       return this.CURD(info, request, modelMap, "0001");
    }
    
    @RequestMapping(value = "/precipitationInfoUpdate", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppUpate(@ModelAttribute("precipitationInfoSave") TppRoutePrecipitation info,
                              HttpServletRequest request, ModelMap modelMap) {

       return this.CURD(info, request, modelMap, "0002");
    }
    
    @SuppressWarnings({ "unchecked", "unused" })
    private String CURD( TppRoutePrecipitation info,HttpServletRequest request, ModelMap modelMap, String type){
        String response =  Validate.getInstance().validate(info,com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
        boolean status = true;
        JSONObject jb=new JSONObject();
        
        try {
            if(!StringUtil.isEmpty(info.getThirdTypeNo())){
                if(StringUtil.isEmpty(response)){//校验通过
                    if("0001".equals(type)){ //save
                       
                            if(!precipitationService.hasCertificateNo(info.getCertificateNo())){
                                int cout = precipitationService.insert(info);
                            }else{
                                response = "该商户号已存在";
                                status = false;
                            }
                            
                       
                    }else if("0002".equals(type)){//update
                        TppRoutePrecipitation  tempDto =  precipitationService.getDtoByCertificateNo(info.getCertificateNo());
                        int uId = info.getId();
                        if(precipitationService.hasCertificateNo(info.getCertificateNo())&& tempDto !=null && tempDto.getId() != uId){//存在
                           
                                response = "该商户号已存在";
                                status = false;
                        }else{//不存在
                            if(!StringUtil.isEmpty(info.getId())){
                                precipitationService.update(info);
                            }
                        }
                    }
                }else{
                    status = false;
                }
            }else{
                status = false;
                response = "操作失败";
                log.error("传入的第三方通道编码为空-thirdTypeNo");
            }
        } catch(Exception e){
            response="操作失败";
            status = false;
            log.error(e.getMessage(), e);
            modelMap.put("errorMsg", e.getMessage());
            return "/errorpage/error";
        }finally{
            jb.put("valmsg",response);
            jb.put("status",status);
        }
        return jb.toString();
    }

}
