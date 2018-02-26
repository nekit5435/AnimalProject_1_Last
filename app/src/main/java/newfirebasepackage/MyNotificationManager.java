package newfirebasepackage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.animalproject.animalproject_1.MainActivity;
import com.example.animalproject.animalproject_1.R;


public class MyNotificationManager {
    private Context myContext;
    private static MyNotificationManager mInstance;
    private MyNotificationManager(Context context)
    {
        myContext = context;
    }
    public static synchronized MyNotificationManager getmInstance(Context context)
    {
        if (mInstance==null)
            mInstance = new MyNotificationManager(context);
        return mInstance;
    }
    public void displayNotification(String title, String body)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(myContext,FirebaseConstants.CHANNEL_ID)
                .setSmallIcon(R.drawable.templogo_icon)
                .setContentTitle(title)
                .setContentText(body);
        Intent intent = new Intent(myContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(myContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if(mNotificationManager != null)
            mNotificationManager.notify(1,mBuilder.build());
    }
}
