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
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView v;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v=new TextView(this);
        v.setGravity(Gravity.CENTER);
        v.setTextSize(18);
        String str="10086";
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message msg=new Message();
                String str="Hello World!";
                Document doc;
                String host = android.net.Proxy.getDefaultHost();
                int port = android.net.Proxy.getDefaultPort();
                if (host != null && port != -1) {
                    System.getProperties().setProperty("proxySet", "true");
                    System.setProperty("http.proxyHost", host);
                    System.setProperty("http.proxyPort", Integer.toString(port));
                }
                try {
                    doc = Jsoup.connect("http://chowe.one/ww.html").timeout(5000).get();
                    //Elements ele=doc.getElementsByTag("p");
                    //str=doc.head().getElementsByTag("title").text();
                    str=doc.body().getElementsByTag("p").text();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                msg.obj=(Object) str;
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


/*


    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


 */