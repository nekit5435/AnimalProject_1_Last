package animalproject.animalproject_1.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.animalproject.animalproject_1.MainActivity;
import com.google.android.gms.maps.model.LatLng;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;


public class Ads implements Runnable {
    public static ArrayList<Ad> adsList;
    private String str;
    private Socket soc;
    public void getAdsList() {
        adsList = new ArrayList<Ad>();
        MyAsyncTask task = new MyAsyncTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        task.cancel(true);
    }
    public Ads()
    {
        getAdsList();
    }

    @Override
    public void run() {

    }

    // public Ad getAdByID(int id)

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                soc = new Socket(MainActivity.serverIp, MainActivity.serverPort);
                Log.d(" Connectedwow "," Connectedwow ");
                PrintWriter out = null;
                BufferedReader in = null;
                out = new PrintWriter(soc.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(
                        soc.getInputStream()));

                str = in.readLine();
                Log.d(" Wowmessage ", str);
                str = in.readLine();
                Log.d(" Wowmessage ", str);
                out.println("getA");

                while ((str = in.readLine()) != null) {
                    Log.d(" Finalout: ", str);
                    adsList.add(new Ad(new LatLng(Float.parseFloat(str.split(";")[0]), Float.parseFloat(str.split(";")[1])),str.split(";")[2],str.split(";")[3],str.split(";")[4],str.split(";")[5],str.split(";")[6],str.split(";")[7],str.split(";")[8],str.split(";")[9],Integer.parseInt(str.split(";")[10]),null));
                    Log.d(" Addded ", " Addded ");
                }

                out.close();
                in.close();
                soc.close();
            }
            catch (Exception e) {
                Log.getStackTraceString(e);
                Log.d(" Connectedwowex "," Connectedwowex ");
            }
            return null;
        }
    }



}
class Ad implements Parcelable{
    public LatLng cords;
    public String type;
    public String breed;
    public String gender;
    public String other;
    public String name;
    public String phone;
    public String age;
    public String reward;
    public int id;
    public Bitmap image;

    private Socket soc;

    private class MyAsyncTaskForSending extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                soc = new Socket(MainActivity.serverIp, MainActivity.serverPort);
                Log.d(" Connectedwow1 "," Connectedwow1 ");
                PrintWriter out = null;
                out = new PrintWriter(soc.getOutputStream(), true);
                Log.d(" StrSend ",MainActivity.stringToSend);
                out.println(MainActivity.stringToSend);
                Log.d(" ssse "," ssse ");
                out.close();
                soc.close();
            }
            catch (Exception e) {
                Log.getStackTraceString(e);
                Log.d(" Connectedwowex1 "," Connectedwowex1 ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public void sendToServer()
    {
        new MyAsyncTaskForSending().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public Ad(LatLng cords, String type, String breed, String gender, String name, String phone, String other, String age, String reward, int id, Bitmap image) {
        this.cords = cords;
        this.type = type;
        this.age = age;
        this.breed = breed;
        this.other = other;
        this.reward = reward;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.image = image;
        this.phone = phone;
    }

    protected Ad(Parcel in) {
        cords = in.readParcelable(LatLng.class.getClassLoader());
        type = in.readString();
        breed = in.readString();
        gender = in.readString();
        other = in.readString();
        name = in.readString();
        phone = in.readString();
        age = in.readString();
        reward = in.readString();
        id = in.readInt();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Ad> CREATOR = new Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cords, i);
        parcel.writeString(type);
        parcel.writeString(breed);
        parcel.writeString(gender);
        parcel.writeString(other);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(age);
        parcel.writeString(reward);
        parcel.writeInt(id);
        parcel.writeParcelable(image, i);
    }
}
