package com.example.webservice;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
/**
 * Created by Administrator on 2018/3/1.
 */

public class MyTask extends AsyncTask <String,String,Void>{

    private final static String NAME_SPACE = "http://webserviceTest/";
    private final static String METHOD = "add";
    private String WSDL_URL = null;

    private final static String TAG = "MyTask";

    private TextView mtextview;

    MyTask(TextView textview,String IP){
        this.mtextview = textview;
        WSDL_URL = "http://"+IP+"/Service/Function?wsdl";

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... stings) {
        SoapObject request = new SoapObject(NAME_SPACE,METHOD);
        request.addProperty("mm",stings[0]);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.bodyOut = request;
        HttpTransportSE ht = new HttpTransportSE(WSDL_URL);
        ht.debug = false;
        try {
            System.out.println(new Date(System.currentTimeMillis()));
            ht.call(NAME_SPACE+METHOD,envelope);
            System.out.println(new Date(System.currentTimeMillis()));
        }catch (Exception e){
            e.printStackTrace();
        }
        SoapObject object = (SoapObject)envelope.bodyIn;
        System.out.println("获得服务数据");
        if (object == null)
        {
            publishProgress("服务未开启");
            return null;
        }
        String result = object.getProperty(0).toString();
        Log.d(TAG, result);
        publishProgress(result);
        return null;
    }

    @Override
    protected void onProgressUpdate(String ... values) {
        mtextview.setText(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
