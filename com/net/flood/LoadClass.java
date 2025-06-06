package com.net.flood;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



public class LoadClass {
    static {
        System.loadLibrary("Downloader");
    }
    int Errno;
    static LoadClass l;
    public	static String[]	defaulHeader(){
        String[] out = new String[5];
        out[0] ="Connection: keep-alive";
        out[1] ="Save-Data: no";
        // out[2] ="Upgrade-Insecure-Requests: 1";
        out[2] ="User-Agent: Mozilla/5.0 (Linux; Android 6.0; FS454 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/92.0.4515.159 Mobile Safari/537.36";
        out[3] ="Cache-Control: max-age=0";
        out[4] ="Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
        return out;
    }
    private void callback_head(String data) {

    }
    private void callback(String data) {

    }
    public static LoadClass get() {

        if(l == null) l = new LoadClass();
        return l;
    }
    private native int Postrequest(String[] getHeaders ,String url ,String data, String pach);
    private native static String runComm(String data, String pach);
    private native int get(String url ,String[] getHeaders , String pach);
    public String getRequestFile(String url ,String[] getHeaders ,File file) {
        //File dir = App.getInstance().getCacheDir();
        String dataout=null;
        //dir.mkdirs();
       // Headers = new File(file.getAbsolutePath()+"head");
        Errno=	get(url, getHeaders , file.getAbsolutePath() );
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] d = new byte[fis.available()];
            fis.read(d);
            fis.close();
            dataout = new String(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataout;

    }
  /*  public static String Comm (String dd ){
        String data = null;

        File     file = new File(Application.a.getCacheDir(), "comm.txt");
      //  file.mkdirs();
        data =    runComm( dd,  file.getAbsolutePath());
      try {
            FileInputStream fis = new FileInputStream(file);
            byte[] d = new byte[fis.available()];
            fis.read(d);
            fis.close();
            data = new String(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }//*/
    public String postRequestString(String url , String data , String[] getHeaders , File file) {

        if (getHeaders == null)   getHeaders = defaulHeader();
      //  if (file == null){
         //   file = new File(Application.a.getCacheDir(), "tmp");
      //  }

        Errno=Postrequest(getHeaders ,url ,data , file.getAbsolutePath());
        Log.e("jhvjh" , ""+Errno);
       // if(Errno != 0) return null;
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] d = new byte[fis.available()];
            fis.read(d);
            fis.close();
            data = new String(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }
}
