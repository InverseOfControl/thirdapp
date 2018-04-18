

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.zendaimoney.thirdpp.notify.consumer.NotifyTasker;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml"})
public class NotifyTaskServiceTest {


	@Test
	public void notifyService() {
		//NotifyServiceImpl notifyService = new NotifyServiceImpl();
		//List<TradeNotify> tradeNotify= new ArrayList<TradeNotify>();
		//TradeNotify notify = new TradeNotify();
		
		
		NotifyTasker task = new NotifyTasker();
		task.startNotifyTread();
		
		
	}
	
	@Test
	public void mergeService() throws DataAccessException, SQLException {
		/*
		MergeServiceImpl mergeService = new MergeServiceImpl();
		MqMessage msg = new MqMessage();
		msg.setBizType("");
		msg.setTradeFlow("");
		//mergeService.merge(msg);
*/		
	}

}
