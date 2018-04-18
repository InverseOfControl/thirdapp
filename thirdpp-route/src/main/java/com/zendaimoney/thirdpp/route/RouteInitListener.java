package com.zendaimoney.thirdpp.route;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zendaimoney.thirdpp.route.entity.*;
import com.zendaimoney.thirdpp.route.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.route.exception.PlatformException;
import com.zendaimoney.thirdpp.route.filter.AvailableFilter;
import com.zendaimoney.thirdpp.route.filter.CustomFilter;
import com.zendaimoney.thirdpp.route.mapper.*;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YM20051 on 2017/6/1.
 */
public class RouteInitListener implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            // 初始化路由链
            this.initFilter();

            // 加载初始化数据
            this.initData();
        }
    }

    /**
     * 初始化路由链
     */
    private void initFilter(){
        RouteCacheContainer.filters.add(new AvailableFilter());

        RouteConfMapper routeConfMapper = applicationContext.getBean(RouteConfMapper.class);
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
                    RouteCacheContainer.filters.add((Filter) obj);
                }
            }
        } catch (Exception e) {
            throw new PlatformException(PlatformErrorCode.ROUTE_PARSE_ERROR, e.getCause());
        }
        RouteCacheContainer.filters.add(new CustomFilter());
    }

    /**
     * 初始化缓存数据
     */
    private void initData(){

        try {
            // 信息类别
            this.initInfoCategory();

            // 通道编码映射关系
            this.initThirdChannelName();

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

    // 初始化 通道编码与名称映射关系
    private void initThirdChannelName(){
        DictionaryMapper dictionaryMapper = applicationContext.getBean(DictionaryMapper.class);
        EntityWrapper<DictionaryEntity> ew = new EntityWrapper<>();
        ew.and("dic_type={0}", "3");   // 支付平台

        List<DictionaryEntity> dictionaryEntities = dictionaryMapper.selectList(ew);
        for (DictionaryEntity dictionaryEntity : dictionaryEntities) {
            RouteCacheContainer.thirdChannels.put(dictionaryEntity.getDicCode(), dictionaryEntity.getDicName());
        }

    }
    // 初始化 第三方通道( 包含失败次数, 总沉淀量)
    private void initThirdChannels(){

        RouteThirdChannelMapper thirdChannelMapper = applicationContext.getBean(RouteThirdChannelMapper.class);
        EntityWrapper<RouteThirdChannelEntity> ew = new EntityWrapper<>();
        ew.and("is_available={0}", "1");   // 可用
        ew.orderBy("priority", false);     // 倒序

        List<RouteThirdChannelEntity> thirdChannelEntities = thirdChannelMapper.selectList(ew);
        for (RouteThirdChannelEntity thirdChannelEntity : thirdChannelEntities) {
            RouteThirdChannelReqVO thirdChannelReqVO = new RouteThirdChannelReqVO();
            BeanUtils.copyProperties(thirdChannelEntity, thirdChannelReqVO);
            String thirdTypeNo = thirdChannelReqVO.getThirdTypeNo();
            String channelName = RouteCacheContainer.thirdChannels.get(thirdTypeNo);
            thirdChannelReqVO.setThirdChannelName(channelName==null?thirdTypeNo:channelName);

            RouteCacheContainer.routeThirdChannels.put(thirdTypeNo, thirdChannelReqVO);
        }

    }

    // 初始化 信息类别
    private void initInfoCategory(){
        SysInfoCategoryMapper infoCategoryMapper = applicationContext.getBean(SysInfoCategoryMapper.class);
        List<SysInfoCategoryEntity> sysInfoCategoryEntities = infoCategoryMapper.selectList(null);
        for (SysInfoCategoryEntity sysInfoCategoryEntity : sysInfoCategoryEntities) {
            String infoCategoryCode = sysInfoCategoryEntity.getInfoCategoryCode();
            RouteCacheContainer.infoCategories.put(infoCategoryCode, sysInfoCategoryEntity.getMerchantType());
        }

    }

    // 初始化 第三方通道对应的产品通道(业务通道)
    private void initThirdChannelInfo(){
        SysThirdChannelInfoMapper thirdChannelInfoMapper = applicationContext.getBean(SysThirdChannelInfoMapper.class);
        List<SysThirdChannelInfoEntity> thirdChannelInfoEntities = thirdChannelInfoMapper.findByAll(1);
        for (SysThirdChannelInfoEntity thirdChannelInfoEntity : thirdChannelInfoEntities) {
            // 第三方通道码 + "_" + 商户类型
            String key = thirdChannelInfoEntity.getThirdTypeNo() + "_" + thirdChannelInfoEntity.getMerchantType();
            RouteCacheContainer.thirdChannelInfos.put(key, thirdChannelInfoEntity);
        }
    }

    // 初始化 第三方通道对应的银行信息(包括单笔限额)
    private void initThirdFieldMapper(){
        ThirdFieldMapper thirdFieldMapper = applicationContext.getBean(ThirdFieldMapper.class);

        List<ThirdFieldMapperEntity> thirdFieldMapperEntities = thirdFieldMapper.findByAll(1);
        for (ThirdFieldMapperEntity thirdFieldMapperEntity : thirdFieldMapperEntities) {
            // 第三方通道码 + "_" + 银行编码
            String key = thirdFieldMapperEntity.getThirdPartyType() + "_"+ thirdFieldMapperEntity.getTppFieldCode();
            RouteCacheContainer.thirdFieldMappers.put(key, thirdFieldMapperEntity);

        }
    }

    // 初始化 沉淀量
    private void initPrecipitation(){

        RoutePrecipitationMapper routePrecipitationMapper = applicationContext.getBean(RoutePrecipitationMapper.class);
        List<RoutePrecipitationEntity> routePrecipitationEntities = routePrecipitationMapper.selectList(null);
        for (RoutePrecipitationEntity routePrecipitationEntity : routePrecipitationEntities) {
            // 第三方通道码 + "_" + 商户号
            String key = routePrecipitationEntity.getThirdTypeNo() + "_" + routePrecipitationEntity.getCertificateNo();
            RouteCacheContainer.precipitations.put(key, routePrecipitationEntity.getPrecipitation());
        }
    }

    // 初始化通道费率
    private void initChannelCost(){

        RouteChannelCostMapper routeChannelCostMapper = applicationContext.getBean(RouteChannelCostMapper.class);
        List<RouteChannelCostEntity> routeChannelCostEntities = routeChannelCostMapper.findByAll();

        for (RouteChannelCostEntity routeChannelCostEntity : routeChannelCostEntities) {
            String key = routeChannelCostEntity.getThirdTypeNo();
            List<RouteChannelCostEntity> channelCostEntities = RouteCacheContainer.channelCosts.get(key);
            if(CollectionUtils.isEmpty(channelCostEntities)){
                channelCostEntities = new ArrayList<>();
                RouteCacheContainer.channelCosts.put(key, channelCostEntities);
            }
            channelCostEntities.add(routeChannelCostEntity);
        }

    }



}
