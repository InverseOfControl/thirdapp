package com.zendaimoney.thirdpp.route.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zendaimoney.thirdpp.route.Filter;
import com.zendaimoney.thirdpp.route.RouteCacheContainer;
import com.zendaimoney.thirdpp.route.entity.*;
import com.zendaimoney.thirdpp.route.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.route.exception.PlatformException;
import com.zendaimoney.thirdpp.route.filter.AvailableFilter;
import com.zendaimoney.thirdpp.route.filter.CustomFilter;
import com.zendaimoney.thirdpp.route.mapper.*;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YM20051 on 2017/6/7.
 */
@Service
public class DataTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RouteThirdChannelMapper routeThirdChannelMapper;
    @Autowired
    private SysInfoCategoryMapper sysInfoCategoryMapper;
    @Autowired
    private SysThirdChannelInfoMapper sysThirdChannelInfoMapper;
    @Autowired
    private ThirdFieldMapper thirdFieldMapper;
    @Autowired
    private RoutePrecipitationMapper routePrecipitationMapper;
    @Autowired
    private RouteChannelCostMapper routeChannelCostMapper;
    @Autowired
    private RouteConfMapper routeConfMapper;

    public void update(){
        this.initFilter();
        this.initData();
    }


    /**
     * 初始化缓存数据
     */
    private void initData(){

        try {
            // 信息类别
            this.initInfoCategory();
            // 第三方通道配置(大通道)
            this.initThirdChannels();

            // 第三方通道 对应 产品通道(业务通道)
            this.initThirdChannelInfo();

            // 第三方通道 对应 银行配置(包括单笔限额)
            this.initThirdFieldMapper();

            // 沉淀量
            this.initPrecipitation();

            // 通道费率
            this.initChannelCost();
        } catch (Exception e) {
            throw new PlatformException(PlatformErrorCode.CACHE_LOAD_ERROR, e.getCause());
        }

    }

    /**
     * 初始化路由链
     */
    private void initFilter(){
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new AvailableFilter());
        EntityWrapper<RouteConfEntity> ew = new EntityWrapper<>();
        ew.and("is_available={0}", "1");   // 可用
        ew.orderBy("priority", false);     // 倒序
        List<RouteConfEntity> routeConfEntities = routeConfMapper.selectList(ew);

        try {
            for (RouteConfEntity routeConfEntity : routeConfEntities) {
                String routeClass = routeConfEntity.getRouteClass();
                if(StringUtils.isEmpty(routeClass)){
                    throw new PlatformException(PlatformErrorCode.ROUTE_PARSE_ERROR, "路由配置解析错误!");
                }
                Class<?> clazz = Class.forName(routeClass.trim());
                Object obj = clazz.newInstance();
                if (obj instanceof Filter) {
                    filters.add((Filter) obj);
                }
            }
        } catch (Exception e) {
            throw new PlatformException(PlatformErrorCode.ROUTE_PARSE_ERROR, e.getCause());
        }
        filters.add(new CustomFilter());

        RouteCacheContainer.filters = filters;
    }

    // 初始化 第三方通道( 包含失败次数, 总沉淀量)
    private void initThirdChannels(){

        EntityWrapper<RouteThirdChannelEntity> ew = new EntityWrapper<>();
        ew.and("is_available={0}", "1");   // 可用
        ew.orderBy("priority", false);     // 倒序
        // 删除多余的
        List<RouteThirdChannelEntity> thirdChannelEntities = routeThirdChannelMapper.selectList(ew);
        RouteCacheContainer.routeThirdChannels.clear();
        
//        for (RouteThirdChannelEntity thirdChannelEntity : thirdChannelEntities) {
//            String thirdTypeNo = thirdChannelEntity.getThirdTypeNo();
//            RouteThirdChannelReqVO thirdChannelReqVO = RouteCacheContainer.routeThirdChannels.get(thirdTypeNo);
//            if(thirdChannelReqVO == null){
//                RouteCacheContainer.routeThirdChannels.remove(thirdTypeNo);
//            }
//        }
        
        // 更新最新的
		for (RouteThirdChannelEntity thirdChannelEntity : thirdChannelEntities) {
			RouteThirdChannelReqVO thirdChannelReqVO = new RouteThirdChannelReqVO();
			BeanUtils.copyProperties(thirdChannelEntity, thirdChannelReqVO);
			String thirdTypeNo = thirdChannelReqVO.getThirdTypeNo();
			String channelName = RouteCacheContainer.thirdChannels.get(thirdTypeNo);
			thirdChannelReqVO.setThirdChannelName(channelName == null ? thirdTypeNo: channelName);

			RouteCacheContainer.routeThirdChannels.put(thirdTypeNo,thirdChannelReqVO);
		}

    }

    // 初始化 信息类别
    private void initInfoCategory(){
        List<SysInfoCategoryEntity> sysInfoCategoryEntities = sysInfoCategoryMapper.selectList(null);
        RouteCacheContainer.infoCategories.clear();
        for (SysInfoCategoryEntity sysInfoCategoryEntity : sysInfoCategoryEntities) {
            String infoCategoryCode = sysInfoCategoryEntity.getInfoCategoryCode();
            RouteCacheContainer.infoCategories.put(infoCategoryCode, sysInfoCategoryEntity.getMerchantType());
        }

    }

    // 初始化 第三方通道对应的产品通道(业务通道)
    private void initThirdChannelInfo(){
        List<SysThirdChannelInfoEntity> thirdChannelInfoEntities = sysThirdChannelInfoMapper.findByAll(1);
        RouteCacheContainer.thirdChannelInfos.clear();
        for (SysThirdChannelInfoEntity thirdChannelInfoEntity : thirdChannelInfoEntities) {
            // 第三方通道码 + "_" + 商户类型
            String key = thirdChannelInfoEntity.getThirdTypeNo() + "_" + thirdChannelInfoEntity.getMerchantType();
            RouteCacheContainer.thirdChannelInfos.put(key, thirdChannelInfoEntity);
        }
    }

    // 初始化 第三方通道对应的银行信息(包括单笔限额)
    private void initThirdFieldMapper(){
        List<ThirdFieldMapperEntity> thirdFieldMapperEntities = thirdFieldMapper.findByAll(1);
        RouteCacheContainer.thirdFieldMappers.clear();
        for (ThirdFieldMapperEntity thirdFieldMapperEntity : thirdFieldMapperEntities) {
            // 第三方通道码 + "_" + 银行编码
            String key = thirdFieldMapperEntity.getThirdPartyType() + "_"+ thirdFieldMapperEntity.getTppFieldCode();
            RouteCacheContainer.thirdFieldMappers.put(key, thirdFieldMapperEntity);

        }
    }

    // 初始化 沉淀量
    private void initPrecipitation(){

        List<RoutePrecipitationEntity> routePrecipitationEntities = routePrecipitationMapper.selectList(null);
        RouteCacheContainer.precipitations.clear();
        for (RoutePrecipitationEntity routePrecipitationEntity : routePrecipitationEntities) {
            // 第三方通道码 + "_" + 商户号
            String key = routePrecipitationEntity.getThirdTypeNo() + "_" + routePrecipitationEntity.getCertificateNo();
            RouteCacheContainer.precipitations.put(key, routePrecipitationEntity.getPrecipitation());
        }
    }

    // 初始化通道费率
    private void initChannelCost(){

        List<RouteChannelCostEntity> routeChannelCostEntities = routeChannelCostMapper.findByAll();

        Map<String, List<RouteChannelCostEntity>> channelCosts = new HashMap<>();
        RouteCacheContainer.channelCosts.clear();
        for (RouteChannelCostEntity routeChannelCostEntity : routeChannelCostEntities) {
            String key = routeChannelCostEntity.getThirdTypeNo();
//            List<RouteChannelCostEntity> channelCostEntities = RouteCacheContainer.channelCosts.get(key);
            List<RouteChannelCostEntity> channelCostEntities = channelCosts.get(key);
            if(CollectionUtils.isEmpty(channelCostEntities)){
                channelCostEntities = new ArrayList<>();
                channelCosts.put(key, channelCostEntities);
                //RouteCacheContainer.channelCosts.put(key, channelCostEntities);
            }
            channelCostEntities.add(routeChannelCostEntity);
        }
        // 覆盖原来的 通道费率
        RouteCacheContainer.channelCosts.putAll(channelCosts);

    }
}
