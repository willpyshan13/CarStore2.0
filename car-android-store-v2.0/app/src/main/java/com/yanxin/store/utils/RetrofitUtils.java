package com.yanxin.store.utils;

import android.annotation.SuppressLint;
import android.os.Build;

import com.blankj.utilcode.util.LogUtils;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static Retrofit mRetrofit;
    private static Map<String, Retrofit> utilsMap = new HashMap<>();

    private static Retrofit getInstance(String httpUrl) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        if (utilsMap.get(httpUrl) == null) {
            synchronized (Retrofit.class) {
                mRetrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(httpUrl)
                        .client(getOkHttpUtils()).build();
            }
            utilsMap.put(httpUrl, mRetrofit);
        }
        return utilsMap.get(httpUrl);
    }

    private static OkHttpClient getOkHttpUtils() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(message -> LogUtils.e("RetrofitUtils", message))
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(),new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectTimeout(20, TimeUnit.SECONDS);
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
//                    TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init((KeyStore) null);
//            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
//                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
//            }
//            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, new TrustManager[]{trustManager}, null);
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            okBuilder.sslSocketFactory(sslSocketFactory, trustManager);
//        }
        return okBuilder.build();
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    //信任所有的服务器,返回true
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static <T> T client(String httpUrl, Class<T> clazz) {
        try {
            return getInstance(httpUrl).create(clazz);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
