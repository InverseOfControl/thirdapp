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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.models.TppRouteConf;
import com.zdmoney.manager.service.TppRouteConfService;
import com.zdmoney.manager.utils.JackJsonUtil;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.RouteTransformUtil;
import com.zdmoney.manager.utils.StringUtil;

@Controller
@RequestMapping(value = "/bim/routeInfo")
public class TppRouteConfController extends TppBaseController {

    private final Logger        log = Logger.getLogger(TppRouteConfController.class);

    @Autowired
    private TppRouteConfService tppRCService;

    @RequestMapping(value = "/routeConfList")
    public String view(HttpServletRequest request, ModelMap modelMap) {
        return "/bim/routeInfo/routeConfList";
    }

    @RequestMapping(value = "/routeConfPriorityList")
    public String viewPriority(HttpServletRequest request, ModelMap modelMap) {
        return "/bim/routeInfo/routeConfPriorityList";
    }

    @RequestMapping(value = "/routeConfListEdit/{id}")
    public String viewEdit1(@PathVariable("id") String id, HttpServletRequest request,
                            ModelMap modelMap) {
        TppRouteConf info = null;
        if (!StringUtil.isEmpty(id))
            info = tppRCService.getRouteConfByID(StringUtil.parseLong(id));
        modelMap.put("routeConf", info);
        return "/bim/routeInfo/routeConfListEdit";
    }

    @RequestMapping(value = "/routeConfListData")
    @ResponseBody
    public String routeConfList(HttpServletRequest request, ModelMap modelMap) {
        return this.routeInfoListData(request, modelMap, "1");
    }
    
    
    @RequestMapping(value = "/routePriorityListData")
    @ResponseBody
    public String priorityInfoList(HttpServletRequest request, ModelMap modelMap) {
        return this.routeInfoListData(request, modelMap, "2");
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String routeInfoListData(HttpServletRequest request, ModelMap modelMap,String pageType){
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            if("1".equals(pageType)){
                super.datagridParam(request, params);
            }else if("2".equals(pageType)){//优先级
                params.put("isAvailable", "1");
            }
            //分页         
            //查询条件
            //封装查询条件
            // 2.遍历增加及转换自定义内容
            List<Map> rows = tppRCService.getRouteConfInfoList(params);
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

    @RequestMapping(value = "/priorityInfoUpdate", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave(String routeConfInfos, HttpServletRequest request, ModelMap modelMap) {
        System.out.println(routeConfInfos);
        String repStr = "";
        try {
            List<TppRouteConf> routes = JackJsonUtil.strToList(routeConfInfos, TppRouteConf.class);
            if (routes != null && routes.size() > 0) {
                for (TppRouteConf r : routes) {
                    repStr = this.CURD(r, request, modelMap, "0004");
                }
            }
            System.out.println(routes.size());
        } catch (Exception e) {
        }
        return repStr;

    }

    @RequestMapping(value = "/routeConfInfoUpdate", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppUpdate(TppRouteConf info, HttpServletRequest request, ModelMap modelMap) {
        String repStr = "";
        try {
            repStr = this.CURD(info, request, modelMap, "0002");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return repStr;

    }

    @RequestMapping(value = "/routeConfInfoSave", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave(TppRouteConf info, HttpServletRequest request, ModelMap modelMap) {
        String repStr = "";
        try {
            repStr = this.CURD(info, request, modelMap, "0001");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return repStr;

    }
    
    /**
     * 改变系统状态
     */
    @RequestMapping(value = "/updateRouteStatus/{id}/{isAvailable}")
    @ResponseBody
    public String updateIsActive(@PathVariable("id") String id,@PathVariable("isAvailable") String isAvailable,HttpServletRequest request, ModelMap modelMap) {
        TppRouteConf info = new TppRouteConf();
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

    /**
     * 
     * 
     * @param info
     * @param request
     * @param modelMap
     * @param type 0002-update  0001-insert 0003-delete 0004-other
     * @return
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private String CURD(TppRouteConf info, HttpServletRequest request, ModelMap modelMap,String type) {
        String response = "";
        boolean status = true;
        JSONObject jb = new JSONObject();
        try {
            if ("0002".equals(type)) {//save
                response = Validate.getInstance().validate(info,com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
                if (StringUtil.isEmpty(response)) {//校验通过
                    if (!StringUtil.isEmpty(info.getId())) {
                        int count = tppRCService.update(info);
                        response = "操作成功";
                    }
                } else {
                    status = false;
                }
            } else if ("0001".equals(type)) { //insert
                response = Validate.getInstance().validate(info,com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
                if (StringUtil.isEmpty(response)) {//校验通过
                    int id = tppRCService.insert(info);
                } else {
                    status = false;
                }
            } else if ("0004".equals(type)) {//调整优先级
                int count = tppRCService.update(info);
                response = "";
            }

        } catch (Exception e) {
            response = "操作失败";
            status = false;
            log.error(e.getMessage(), e);
        } finally {
            jb.put("valmsg", response);
            jb.put("status", status);
        }
        return jb.toString();
    }

}
