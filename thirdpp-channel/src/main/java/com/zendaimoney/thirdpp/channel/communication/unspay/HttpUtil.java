package com.zendaimoney.thirdpp.channel.communication.unspay;

import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * http 工具类
 * @author haowp
 *
 */
public class HttpUtil {
	
    private static final LogPrn logger = new LogPrn(HttpUtil.class);
    private static final int CONNECTION_TIME_OUT = 1 * 30 * 1000;
    private static final int SO_TIMEOUT = 1 * 60 * 1000;
    private HttpUtil() {
 
    }
 
    static HostnameVerifier sslHostnameVerifier;
 
    static synchronized void initSslHostnameVerifier() {
        if (sslHostnameVerifier != null) {
            return;
        }
        sslHostnameVerifier = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return urlHostName != null && urlHostName.equals(session.getPeerHost());
            }
        };
    }
 
    static SSLSocketFactory sslSocketFactory;
 
    /**
     * 忽略SSL证书
     */
    static synchronized void initSslSocketFactory() {
        if (sslSocketFactory != null) {
            return;
        }
        InputStream in = null;
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            final X509TrustManager trustManager = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
 
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
 
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
 
                }
            };
            context.init(null, new TrustManager[] { trustManager }, null);
            sslSocketFactory = context.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    private static HttpURLConnection createConnection(String url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        if ("https".equalsIgnoreCase(url.substring(0, 5))) {
            if (sslSocketFactory == null) {
                initSslSocketFactory();
            }
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            if (sslHostnameVerifier == null) {
                initSslHostnameVerifier();
            }
            ((HttpsURLConnection) conn).setHostnameVerifier(sslHostnameVerifier);
        }
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Connection", "close");
        return conn;
    }
 
    public static HttpResponse execute(HttpRequest request) throws Exception{
        /* 参数检查 */
        if (request == null) {
            throw new IllegalArgumentException("HttpRequest must be not null");
        }
        if (CommonUtil.isEmpty(request.getUrl())) {
            throw new IllegalArgumentException("HttpRequest url must be not null");
        }
        if (request.getTimeout() < 0) {
            throw new IllegalArgumentException(String.format("timeout=[%s],HttpRequest timeout must be Greater than zero", request.getTimeout() + ""));
        }
        if (request.getMethod() == HttpMethod.GET && !CommonUtil.isEmpty(request.getContent())) {
            throw new IllegalArgumentException("When Http Method is GET,the HttpRquest content must be null");
        }
        HttpURLConnection connection = null;
        String url = request.getUrl();
        try {
            // 设置url传递参数
            if (!CommonUtil.isEmpty(request.getParams())) {
                String queryString = CommonUtil.map2UrlParams(request.getParams());
                if (!CommonUtil.isEmpty(queryString)) {
                    url = url + "?" + queryString;
                }
            }
            // 获取连接
            connection = createConnection(url);
            connection.setRequestMethod(request.getMethod().toString());
            connection.setConnectTimeout(CONNECTION_TIME_OUT);
            connection.setReadTimeout(SO_TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置请求头
            if (!CommonUtil.isEmpty(request.getHeaders())) {
                for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.connect();
            // 设置请求正文
            if (!CommonUtil.isEmpty(request.getContent())) {
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(request.getContent().getBytes(request.getCharsetName()));
                out.close();
            }
            int code = connection.getResponseCode();
            String message = connection.getResponseMessage();
            StringBuilder result = new StringBuilder();
            InputStream in = connection.getErrorStream();
            if (in == null) {
                in = connection.getInputStream();
            }
            if (in != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                char[] cbuf = new char[4096];
                while (true) {
                    int len = reader.read(cbuf);
                    if (len < 0) {
                        break;
                    }
                    result.append(cbuf, 0, len);
                }
            }
            return new HttpResponse(code, message, result.toString());
        } catch (Exception e) {
            String msg = "HttpClientManager sendMessage error..."+e.getMessage();
            logger.error(msg);
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        //return new HttpResponse(0, "请求失败", "");
    }
    

    /**
     * http post 请求
     * @param message  请求路径
     * @param json 是否json请求
     * @return
     */
    public static Message post(Message message, boolean json) throws Exception{

        String httpStatusCode="200";
        String params = "";

        try{
            params = message.getMessageMap().get("json");
            String loggerinfo = "communication unspay request info ["+message.getRequestInfo()+"]";
            logger.debug(loggerinfo);
            if(null == message.getUrl() || "".equals(message.getUrl())){
                throw new RuntimeException("url is empty,you should check your request params...");
            }

    	    HttpRequest request = new HttpRequest(message.getUrl(), HttpMethod.POST, params);
            if(json){
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Accept", "application/json");
                request = new HttpRequest(message.getUrl(), HttpMethod.POST, params, headers);
            }
            HttpResponse execute = HttpUtil.execute(request);
            httpStatusCode = String.valueOf(execute.getCode());
            String responseString = execute.getContent();

            message.setHttpStatusCode(httpStatusCode);
            message.setResponseInfo(responseString);

            loggerinfo = "communication unspay response info ["+responseString+"]";
            logger.info(loggerinfo);
            return message;

        } catch (Exception e) {
            message.setHttpStatusCode(httpStatusCode);
            String msg = "unspay sendMessage error..."+e.getMessage();
            logger.error(msg);
            throw e;
        }
    }
}
