package com.zendaimoney.thirdpp.channel.communication.kjtpay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
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
import com.zendaimoney.thirdpp.channel.util.LogPrn;

@Component
public class KjtpayCommunication extends GenericCommunication {

	private static final LogPrn logger = new LogPrn(KjtpayCommunication.class);

	private static final String ENCODE_TYPE = "UTF-8";
	private static final int TIME_OUT = 1 * 30 * 1000;
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
			String loggerinfo = "communication kjtpay request info ["+message.getRequestInfo()+"]";
			logger.debug(loggerinfo);
			
			Map<String, String> paramsMap = message.getMessageMap();
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (paramsMap != null && !paramsMap.isEmpty()) {
				for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
					list.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
			}
			
			// 实现将请求的参数封装到表单中，即请求体中
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, ENCODE_TYPE);
			
			httpPost.setEntity(entity);
			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			httpPost.setParams(params);
			HttpResponse response = httpclient.execute(httpPost);
			httpStatusCode = String.valueOf(response.getStatusLine().getStatusCode());
			httpEntity = response.getEntity();
			responseString = receiveInputStream(httpEntity.getContent());
			loggerinfo = "communication kjtpay response info ["+responseString+"]";
			logger.info(loggerinfo);
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
	 * 实名认证
	 * @param message
	 * @param encodeType
	 * @return
	 * @throws Exception
	 */
	public Message sendMessageByHttp(Message message, String encodeType) throws Exception{
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
			String loggerinfo = "communication shunionAuth request info ["+message.getRequestInfo()+"]";
			logger.info(loggerinfo);
			
			Map<String, String> paramsMap = message.getMessageMap();
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (paramsMap != null && !paramsMap.isEmpty()) {
				for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
					
					// 去除值域为空的请求参数
					if (!StringUtils.isBlank(entry.getValue())) {
						
						list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
					}
				}
			}
			
			// 实现将请求的参数封装到表单中，即请求体中
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encodeType);
			
			httpPost.setEntity(entity);
			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			httpPost.setParams(params);
			HttpResponse response = httpclient.execute(httpPost);
			httpStatusCode = String.valueOf(response.getStatusLine().getStatusCode());
			httpEntity = response.getEntity();
			responseString = receiveInputStream(httpEntity.getContent(), encodeType);
			loggerinfo = "communication shunionAuth response info ["+responseString+"]";
			logger.info(loggerinfo);
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
	
	/**
	 * 获取响应参数信息
	 * **/
	private static String receiveInputStream(InputStream inputStream, String encodeType) throws Exception {
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
		String responseString = new String(responseBytes, encodeType);
		return responseString;
	}
}
