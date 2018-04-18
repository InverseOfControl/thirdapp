package com.zendaimoney.thirdpp.channel.sei;

import com.allinpay.XmlTools;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 通联通快捷支付接口测试
 * @author wulj
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class AllinPayNewTest {

    private static final String ENCODE_TYPE = "GBK";
    private static final int CONNECTION_TIME_OUT = 1 * 30 * 1000;
    private static final int SO_TIMEOUT = 1 * 60 * 1000;
    private static final int MAX_TOTAL_CONNECTIONS = 200;
    private static final int DEFAULT_ROUTE_MAX_CONNECTIONS = 100;
    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;

    private static final String KEYSTOREPATH = "conf/allinpayconfig/20060400000044502.p12";
    private static final String KEYSTOREPASS = "111111";
    private static final String KEYPASS = "111111";

    private static final String URL = "https://113.108.182.3/aipg/quickpay";
    //private static final String URL = "https://www.test.allinpaygd.com/aipg/quickpay";

    private static PoolingClientConnectionManager pccm = null;

    private static final String xml1 = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
            "<AIPG>\n" +
            "\t<INFO>\n" +
            "\t\t<TRX_CODE>310001</TRX_CODE>\n" +
            "\t\t<VERSION>04</VERSION>\n" +
            "\t\t<DATA_TYPE>2</DATA_TYPE>\n" +
            "\t\t<LEVEL>1</LEVEL>\n" +
            "\t\t<MERCHANT_ID>200604000000445</MERCHANT_ID>\n" +
            "\t\t<USER_NAME>20060400000044502</USER_NAME>\n" +
            "\t\t<USER_PASS>`12qwe</USER_PASS>\n" +
            "\t\t<REQ_SN>201804090935001</REQ_SN>\n" +
            "\t\t<SIGNED_MSG></SIGNED_MSG>\n" +
            "\t</INFO>\n" +
            "\t<FAGRA>\n" +
            "\t\t<MERCHANT_ID>200604000000445</MERCHANT_ID>\n" +
            "\t\t<BANK_CODE>0105</BANK_CODE>\n" +
            "\t\t<ACCOUNT_TYPE>00</ACCOUNT_TYPE>\n" +
            "\t\t<ACCOUNT_NO>9558801001114990713</ACCOUNT_NO>\n" +
            "\t\t<ACCOUNT_NAME>张三</ACCOUNT_NAME>\n" +
            "\t\t<ACCOUNT_PROP>0</ACCOUNT_PROP>\n" +
            "\t\t<ID_TYPE>0</ID_TYPE>\n" +
            "\t\t<ID>320800198504287434</ID>\n" +
            "\t\t<TEL>13916158084</TEL>\n" +
            "\t\t<CVV2></CVV2>\n" +
            "\t\t<VAILDDATE></VAILDDATE>\n" +
            "\t\t<MERREM>这是一个保留信息</MERREM>\n" +
            "\t\t<REMARK>备注</REMARK>\n" +
            "\t</FAGRA>\n" +
            "</AIPG>";

    private static final String xml2 = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
            "<AIPG>\n" +
            "\t<INFO>\n" +
            "\t\t<TRX_CODE>310002</TRX_CODE>\n" +
            "\t\t<VERSION>04</VERSION>\n" +
            "\t\t<DATA_TYPE>2</DATA_TYPE>\n" +
            "\t\t<LEVEL>1</LEVEL>\n" +
            "\t\t<MERCHANT_ID>200604000000445</MERCHANT_ID>\n" +
            "\t\t<USER_NAME>20060400000044502</USER_NAME>\n" +
            "\t\t<USER_PASS>`12qwe</USER_PASS>\n" +
            "\t\t<REQ_SN>201804080935002</REQ_SN>\n" +
            "\t\t<SIGNED_MSG></SIGNED_MSG>\n" +
            "\t</INFO>\n" +
            "\t<FAGRC>\n" +
            "\t\t<MERCHANT_ID>200604000000445</MERCHANT_ID>\n" +
            "\t\t<SRCREQSN>201804090935001</SRCREQSN>\n" +
            "\t\t<VERCODE>111111</VERCODE>\n" +
            "\t</FAGRC>\n" +
            "</AIPG>";

    /**
     * 2.1.1	协议支付签约短信触发
     */
    @Test
    public void quickPaySignWithMessage(){
        SSLContext sslContext = null;

        try{
            if (pccm == null) {
                // 创建x.509证书
                X509TrustManager x509 = new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }
                };

                // 创建ssl上下文
                // SSLContext ctx = SSLContext.getInstance("TLS");
                sslContext = SSLContexts.custom().loadKeyMaterial(readStore(), KEYPASS.toCharArray()).build();
                // sslContext.init(null, new TrustManager[] { x509 }, null);

                //SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                //SchemeRegistry registry = new SchemeRegistry();
                //registry.register(new Scheme("http", HTTP_PORT, PlainSocketFactory.getSocketFactory()));
                //registry.register(new Scheme("https", HTTPS_PORT, socketFactory));
                //pccm = new PoolingClientConnectionManager(registry);
                //pccm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
                //pccm.setDefaultMaxPerRoute(DEFAULT_ROUTE_MAX_CONNECTIONS);
            }

            StringEntity stringEntity = new StringEntity(xml1, ENCODE_TYPE);
            HttpPost httpPost = new HttpPost(URL);
            httpPost.setEntity(stringEntity);

            HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = receiveInputStream(response.getEntity().getContent());
            System.out.println("response_string:" + responseString);
            System.out.println("status_line:" + response.getStatusLine());
            EntityUtils.consume(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    @Test
    public void quickPaySignWithMessage1(){
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
            HttpPost httpPost = new HttpPost(URL);

            String xmlStr = XmlTools.signMsg(xml1, "E:\\workspace\\thirdpp\\thirdpp-channel\\src\\main\\resources\\conf\\allinpayconfig\\20060400000044502.p12","111111",false);

            StringEntity entity = new StringEntity(xmlStr, ENCODE_TYPE);

            httpPost.setEntity(entity);
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIME_OUT);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

            httpPost.setParams(params);
            HttpResponse response = httpclient.execute(httpPost);
            httpStatusCode = String.valueOf(response.getStatusLine().getStatusCode());
            httpEntity = response.getEntity();
            responseString = receiveInputStream(httpEntity.getContent());

            System.out.println(responseString);
            System.out.println(httpStatusCode);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Test
    public void quickPaySign(){
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
            HttpPost httpPost = new HttpPost(URL);

            String xmlStr = XmlTools.signMsg(xml2, "E:\\workspace\\thirdpp\\thirdpp-channel\\src\\main\\resources\\conf\\allinpayconfig\\20060400000044502.p12","111111",false);

            StringEntity entity = new StringEntity(xmlStr, ENCODE_TYPE);

            httpPost.setEntity(entity);
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIME_OUT);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

            httpPost.setParams(params);
            HttpResponse response = httpclient.execute(httpPost);
            httpStatusCode = String.valueOf(response.getStatusLine().getStatusCode());
            httpEntity = response.getEntity();
            responseString = receiveInputStream(httpEntity.getContent());

            System.out.println(responseString);
            System.out.println(httpStatusCode);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 读取ssl证书
     * @return
     * @throws Exception
     */
    private KeyStore readStore() throws Exception {
        Resource resource = new ClassPathResource(KEYSTOREPATH);
        InputStream keyStoreStream = resource.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("JKS"); // 证书签名算法
        keyStore.load(keyStoreStream, KEYSTOREPASS.toCharArray());

        return keyStore;
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
