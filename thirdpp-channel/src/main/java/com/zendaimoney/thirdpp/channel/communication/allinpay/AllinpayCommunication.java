package com.zendaimoney.thirdpp.channel.communication.allinpay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.communication.Communication;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.util.LogPrn;

@Component
public class AllinpayCommunication extends Communication {
	
	private static final LogPrn logger = new LogPrn(AllinpayCommunication.class);

	private static final String ENCODE_TYPE = "GBK";
	private static final int CONNECTION_TIME_OUT = 1 * 30 * 1000;
	private static final int SO_TIMEOUT = 1 * 60 * 1000;
	private static final int MAX_TOTAL_CONNECTIONS = 200;
	private static final int DEFAULT_ROUTE_MAX_CONNECTIONS = 100;
	private static final int HTTP_PORT = 80;
	private static final int HTTPS_PORT = 443;

	private static PoolingClientConnectionManager pccm = null;
	
	public Message sendMessageByHttp(Message message) throws Exception{
		
		HttpClient httpclient = null;
		HttpEntity httpEntity = null;
		String responseString = "";
		String httpStatusCode="200";

		try {
			if (pccm == null) {
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					}

					public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					}
				};
				ctx.init(null, new TrustManager[] { tm }, null);

				SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", HTTP_PORT, PlainSocketFactory.getSocketFactory()));
				registry.register(new Scheme("https", HTTPS_PORT, socketFactory));
				pccm = new PoolingClientConnectionManager(registry);
				pccm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
				pccm.setDefaultMaxPerRoute(DEFAULT_ROUTE_MAX_CONNECTIONS);
			}

			httpclient = new DefaultHttpClient(pccm);
			String url = message.getUrl();
			if(null == url || "".equals(url)){
				throw new RuntimeException("url is empty,you should check your request params...");
			}

			HttpPost httpPost = new HttpPost(message.getUrl());
			String loggerinfo = "communication allinpay request info ["+message.getRequestInfo()+"]";
			logger.debug(loggerinfo);
			StringEntity entity = new StringEntity(message.getRequestInfo(), ENCODE_TYPE);

			httpPost.setEntity(entity);
			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIME_OUT);
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			httpPost.setParams(params);
			HttpResponse response = httpclient.execute(httpPost);
			httpStatusCode = String.valueOf(response.getStatusLine().getStatusCode());
			httpEntity = response.getEntity();
			responseString = receiveInputStream(httpEntity.getContent());
			loggerinfo = "communication allinpay response info ["+responseString+"]";
			logger.debug(loggerinfo);
			message.setHttpStatusCode(httpStatusCode);
			message.setResponseInfo(responseString);
			return message;
		} catch (Exception e) {
			message.setHttpStatusCode(httpStatusCode);
			String msg = "HttpClientManager sendMessage error..."+e.getMessage();
			logger.error(msg);
			throw e;
		} finally {
			try {
				EntityUtils.consume(httpEntity);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
