package com.example.webservice;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private TextView textView;
    private EditText editText;
    private TextView textView2;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("IP",editText.getText().toString());
        editor.apply();
        super.onDestroy();
    }

    private void bindViews(){
        button = (Button)findViewById(R.id.button1);
        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        textView2 = (TextView)findViewById(R.id.textView2);
        editText2 = (EditText) findViewById(R.id.editText2);
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        String IP = preferences.getString("IP","");
        editText.setText(IP);
        textView2.setText(getLocalIP().toString());
        button.setOnClickListener(this);
    }

    public static HashMap<String,String> getLocalIP() {
        HashMap<String,String> IPmap = new HashMap();
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();

                // 遍历所有ip
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = (InetAddress) ips.nextElement();
                    if (null == ip || "".equals(ip)) {
                        continue;
                    }
                    String sIP = ip.getHostAddress();
                    if(sIP == null || sIP.indexOf(":") > -1) {
                        continue;
                    }
                    IPmap.put(ni.getDisplayName(), sIP);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IPmap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                new MyTask(textView,editText.getText().toString()).execute(editText2.getText().toString());
                textView.setText("执行中。。。");
            break;

        }
    }
}
