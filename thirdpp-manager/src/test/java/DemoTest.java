

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zdmoney.manager.models.TppRouteChannelCost;
import com.zdmoney.manager.models.TppRouteConf;
import com.zdmoney.manager.models.TppRouteThirdChannel;
import com.zdmoney.manager.service.TppRouteConfService;
import com.zdmoney.manager.service.TppRouteThirdChannelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml",
"classpath*:spring-mybatis.xml","classpath*:mybatis-config.xml"})
public class DemoTest {
    
    @Autowired
    private TppRouteConfService tppRouteConfService;
    @Autowired
    private TppRouteThirdChannelService tService;
    
   
    @Test
    public void batchInsert(){
        
        Random random = new Random();
       
        
        
        for(int i=0 ;i<100;i++){
            TppRouteConf demo3 = new TppRouteConf();
            demo3.setIsAvailable("2");
            demo3.setRouteName("calvin"+random.nextInt(10));
            demo3.setRouteClass("calvin"+random.nextInt(10));
            tppRouteConfService.insert(demo3);
        }
        
    }
    
    @Test
    public void batchInsert1(){
        
        Random random = new Random();
       
        
        
        for(int i=0 ;i<100;i++){
            TppRouteThirdChannel demo3 = new TppRouteThirdChannel();
            demo3.setIsAvailable("2");
            demo3.setThirdTypeNo(i+"");
            tService.insert(demo3);
        }
        
    }
    
   
}
