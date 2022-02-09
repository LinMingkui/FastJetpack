package com.aisier.architecture.net.base;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.aisier.architecture.BuildConfig;
import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Invocation;

public class MyInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request.Builder builder = chain.request()
                .newBuilder()
                .addHeader("Connection", "close");
        Request request = builder.build();
        printRequest(request);
        okhttp3.Response response = chain.proceed(request);
        printResponse(t1, response);
        return response;
    }

    private void printResponse(long t1, okhttp3.Response response) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        try {
            ResponseBody body = response.peekBody(1024 * 1024);
            long t2 = System.nanoTime();
            LogUtils.v("请求耗时：%.1fms\n请求地址：%s\n请求结果：%s",
                    (t2 - t1) / 1e6d, response.request().url(), body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printRequest(Request request) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        try {
            String params = "null";
            RequestBody body = request.body();
            if (body != null) {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                Charset charset = UTF_8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF_8);
                }
                if (charset != null) {
                    params = buffer.readString(charset);
                }
            }
            LogUtils.v("请求地址：%s\n请求参数：%s\n请求方式：%s\nAPI：%s",
                    request.url(), params, request.method(), request.tag(Invocation.class));
        } catch (IOException e) {
            LogUtils.v("请求地址：%s\n请求参数：%s\n请求方式：%s\nAPI：%s",
                    request.url(), e.toString(), request.method(), request.tag(Invocation.class));
            e.printStackTrace();
        }
    }
}