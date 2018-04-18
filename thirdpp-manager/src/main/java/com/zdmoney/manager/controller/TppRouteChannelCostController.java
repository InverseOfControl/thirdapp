package com.zdmoney.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.zdmoney.manager.models.TppRouteChannelCost;
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.service.TppRouteChannelCostService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.RouteTransformUtil;
import com.zdmoney.manager.utils.StringUtil;

@Controller
@RequestMapping(value = "/bim/channelInfo")
public class TppRouteChannelCostController extends TppBaseController {

    private final Logger               log = Logger.getLogger(TppRouteChannelCostController.class);

    @Autowired
    private TppRouteChannelCostService trccService;
    @Autowired
    private TDictionaryService         dictionaryService;

    @RequestMapping(value = "/costInfoList")
    public String view(HttpServletRequest request, ModelMap modelMap) {
        return "/bim/channelInfo/costInfoList";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/costInfoListData/{dicCode}")
    @ResponseBody
    public String infoCategoryList(@PathVariable("dicCode") String dicCode,
                                   HttpServletRequest request, ModelMap modelMap) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            this.datagridParam(request, params);
            //分页         
            //查询条件
            //封装查询条件
            params.put("thirdTypeNo", dicCode);
            // 2.遍历增加及转换自定义内容
            List<Map> rows = trccService.getCostInfoList(params);
            RouteTransformUtil.convertCostType(rows);
            RouteTransformUtil.convertHasLimitAmount(rows);
            // 3.组合输出列表查询所需数据
            // JsonConfig 用于解析转换的设置
            JsonConfig config = new JsonConfig();
            JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
            JSONArray json_rows = JSONArray.fromObject(rows, config);
            String json = "{\"total\":\"" + rows.size() + "\",\"rows\":" + json_rows.toString()
                          + "}";
            return json;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 信息编辑页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/costInfoEditUI/{id}/{dicCode}/{parentId}")
    public String infoCateGoryAppsUI(@PathVariable("id") String id,
                                     @PathVariable("dicCode") String dicCode,
                                     @PathVariable("parentId") String parentId,
                                     HttpServletRequest request, ModelMap modelMap) {
        TppRouteChannelCost info = null;
        //权限验证
        Map<String, Object> pam = new HashMap<String, Object>();
        pam.put("dicCode", dicCode);
        pam.put("parentId", parentId);
        if (!StringUtil.isEmpty(id)) {
            info = trccService.getCostInfoByID(StringUtil.parseLong(id));
        }

        if (null == info) {

            String dicName = dictionaryService.getDicName(pam);
            info = new TppRouteChannelCost();
            info.setThirdTypeName(dicName);

        }
        info.setThirdTypeNo(dicCode);
        modelMap.put("costInfo", info);

        return "bim/channelInfo/costInfoEdit";
    }

    @RequestMapping(value = "/costInfoSave", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave(@ModelAttribute("costInfoSave") TppRouteChannelCost info,
                              HttpServletRequest request, ModelMap modelMap) {
        this.filterData(info);
        return this.CURD(info, request, modelMap, "0001");

    }

    @RequestMapping(value = "/costInfoUpdate", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppUpdate(@ModelAttribute("costInfoSave") TppRouteChannelCost info,
                                HttpServletRequest request, ModelMap modelMap) {
        this.filterData(info);
        return this.CURD(info, request, modelMap, "0002");

    }

    @RequestMapping(value = "/costInfoDelete", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String infoAppSave(String costInfos, HttpServletRequest request, ModelMap modelMap) {
        System.out.println(costInfos);
        String repStr = "";
        try {
            String[] ids = costInfos.split(",");
            TppRouteChannelCost info = new TppRouteChannelCost();
            info.setIds(ids);
            repStr = this.CURD(info, request, modelMap, "0003");
        } catch (Exception e) {
        }
        return repStr;

    }

    @SuppressWarnings("unused")
    private String CURD(TppRouteChannelCost info, HttpServletRequest request, ModelMap modelMap,
                        String type) {
        String response = "";
        boolean status = true;
        JSONObject jb = new JSONObject();

        try {
            if ("0001".equals(type)) { //save
                response = trccService.checkData(info);
                if (StringUtil.isEmpty(response)) {//校验通过
                    if (StringUtil.isEmpty(response)) {//校验通过
                        int id = trccService.insert(info);
                    }else {
                        status = false;
                    }
                } else {
                    status = false;
                }
            } else if ("0002".equals(type)) {//update
                response = trccService.checkData(info);
                if (StringUtil.isEmpty(response)) {//校验通过
                   int cout = trccService.updateAll(info);
                } else {
                    status = false;
                }
            } else if ("0003".equals(type)) {//delete
                trccService.delete(info.getIds());
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

    private void filterData(TppRouteChannelCost info) {
        if ("1".equals(info.getCostType())) {//固定
            info.setHasLimitAmount(null);
            info.setPercent(null);
            info.setLimitAmount(null);
        } else if ("2".equals(info.getCostType())) {//百分比
            if ("2".equals(info.getHasLimitAmount())) {//不封顶
                info.setLimitAmount(null);
            }
            info.setFixedAmount(null);
        }
    }
    
}
