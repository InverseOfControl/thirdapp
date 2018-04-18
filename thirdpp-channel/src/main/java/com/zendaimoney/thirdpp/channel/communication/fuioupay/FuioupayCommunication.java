package com.zendaimoney.thirdpp.channel.communication.fuioupay;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.communication.GenericCommunication;
import com.zendaimoney.thirdpp.channel.communication.Message;
@Component
public class FuioupayCommunication extends GenericCommunication {
	private static final String ENCODE_TYPE = "UTF-8";
	private static final int TIME_OUT = 1 * 30 * 1000;
	private static final int SO_TIMEOUT = 1 * 60 * 1000;
	private static final int MAX_TOTAL_CONNECTIONS = 200;
	private static final int DEFAULT_ROUTE_MAX_CONNECTIONS = 100;
	private static final int HTTP_PORT = 80;
	private static final int HTTPS_PORT = 443;

	private static PoolingClientConnectionManager pccm = null;
	
	public Message sendMessageByHttp(Message message) throws Exception{
		Map<String,String> parametersMap=message.getMessageMap();
		String url=message.getUrl();
		
		HttpEntity httpEntity = null;
		String httpStatusCode="200";
		
		try {
			if(pccm==null){
				SSLContext ctx = SSLContext.getInstance("TLS"); 
				X509TrustManager tm = new X509TrustManager() {
		            public X509Certificate[] getAcceptedIssuers() {return null;}
		            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
		            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
		        };
		        ctx.init(null, new TrustManager[] { tm }, null);
		        
				SSLSocketFactory socketFactory = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
				SchemeRegistry registry=new SchemeRegistry();
				registry.register(new Scheme("http", HTTP_PORT, PlainSocketFactory.getSocketFactory()));
				registry.register(new Scheme("https", HTTPS_PORT, socketFactory));
				pccm=new PoolingClientConnectionManager(registry);
				pccm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
				pccm.setDefaultMaxPerRoute(DEFAULT_ROUTE_MAX_CONNECTIONS);
			}
			
			HttpClient httpclient = new DefaultHttpClient(pccm);
			
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			for (String key : parametersMap.keySet()) {
				paramsList.add(new BasicNameValuePair(key, parametersMap.get(key)));
			}
		//	String loggerinfo = Message.uuid.get()+"|communication fuiou request info["+parametersMap+"]";
		//	logger.info(loggerinfo);
		//	Message.sendlog.set(loggerinfo);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList,ENCODE_TYPE);
			httpPost.setEntity(entity);
			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			
			httpPost.setParams(params);
			
			HttpResponse response = httpclient.execute(httpPost);
			httpStatusCode = String.valueOf(response.getStatusLine().getStatusCode());
			httpEntity=response.getEntity();
			String responseString =receiveInputStream(httpEntity.getContent());
			message.setResponseInfo(responseString);
			//Message.revlog.set(responseString);
			message.setHttpStatusCode(httpStatusCode);
			return message;
		}catch (Exception e) {
			message.setHttpStatusCode(httpStatusCode);
		//	String msg = Message.uuid.get()+"|HttpClientManager sendMessage exception..."+e.getMessage();
		//	logger.error(msg);
		//	Message.revlog.set(msg);
			throw e;
		}finally{
    		EntityUtils.consume(httpEntity);
		}
		
	}

	/**
	 * 获取响应参数信息
	 * **/
	private static String receiveInputStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		if (inputStream != null) {
			int length = 0;
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			outputStream.close();
			inputStream.close();
		}
		byte[] responseBytes = outputStream.toByteArray();
		String responseString = new String(responseBytes, ENCODE_TYPE);
		return responseString;
	}


	

}
