package com.c3app.seiue;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存
        webSettings.setDomStorageEnabled(true);//启用 DOM 存储
        webSettings.setDatabaseEnabled(true);//启用数据库存储
        webSettings.setJavaScriptEnabled(true);//启用JavaScript
        webView.setWebViewClient(new CustomWebViewClient());
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        //设置用户代理，伪装移动端浏览器
        webView.getSettings().setUserAgentString(
                "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Mobile Safari/537.36");
        //判断并启用加载https和http混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //加载希悦客户端url
        webView.loadUrl("https://chalk-c3.seiue.top/home");
    }


    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 检查URL是否是http或https
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return false; // 继续加载 URL
            }
            // 处理自定义 URL scheme
            else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));// 使用外部应用打开
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true; // 拦截 URL，不在 WebView 中加载
            }
        }
    }


    //适配系统自带的返回方式
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
