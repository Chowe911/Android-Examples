package one.chowe.ww;

import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private TextView v;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v=new TextView(this);
        v.setGravity(Gravity.CENTER);
        v.setTextSize(18);
        new Thread(){

            @Override
            public void run() {
                super.run();
                Message msg=new Message();
                String tag="";
                String host = android.net.Proxy.getDefaultHost();
                int port = android.net.Proxy.getDefaultPort();
                if (host != null && port != -1) {
                    System.getProperties().setProperty("proxySet", "true");
                    System.setProperty("http.proxyHost", host);
                    System.setProperty("http.proxyPort", Integer.toString(port));
                }
                try {
                    Document doc = Jsoup.connect("http://chowe.one/ww.html").timeout(5000).get();

                    Elements elements=doc.body().getElementsByTag("p");
                    for(Element e:elements)
                    {
                        tag+=e.text();
                        tag+="\n";
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }

                msg.obj=(Object)tag;
                handler.sendMessage(msg);
            }
        }.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                v.setText((String)msg.obj);

            }
        };
        setContentView(R.layout.activity_main);
        setContentView(v);
    }


}
