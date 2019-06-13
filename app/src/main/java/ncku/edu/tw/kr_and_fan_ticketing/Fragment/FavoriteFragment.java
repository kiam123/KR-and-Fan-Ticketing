package ncku.edu.tw.kr_and_fan_ticketing.Fragment;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import ncku.edu.tw.kr_and_fan_ticketing.Activity.MainActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Activity.SearchActivity;
import ncku.edu.tw.kr_and_fan_ticketing.Adapter.FavoriteAdapter;
import ncku.edu.tw.kr_and_fan_ticketing.Data.FavoriteItem;
import ncku.edu.tw.kr_and_fan_ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    RecyclerView mFavoriteRecyclerView;
    static FavoriteAdapter mFavoriteAdapter;
    static ArrayList<FavoriteItem> mFavoriteItems;
    static FirebaseFirestore db;
    static FragmentActivity  favActivity ;
    static ListenerRegistration registration;
    static ArrayList<String> favoriteList = new ArrayList<>();
    SubscriptionBroadcast subscriptionBroadcast;
    private static final int NOTIFICATION_ID = 7000;
    private static NotificationManager mNotifyManager;
    public static final String Subscription_ACTION = "Subscription_ACTION";
    private static final String PRIMARY_CHANNEL_ID ="primary_notification_channel";

    public FavoriteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(viewGroup);

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup) {
        mFavoriteRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerview);
        mFavoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFavoriteItems = new ArrayList<>();

        mFavoriteAdapter = new FavoriteAdapter(getActivity(), mFavoriteItems);
        mFavoriteRecyclerView.setAdapter(mFavoriteAdapter);
        favActivity = getActivity();

        if (registration != null) {
            Log.d("registration", "need remove");
            removeSnapListener();
        }
        mFavoriteItems.clear();
        initBroadcastReceiver();
        dbListener();
    }


    public static void dbListener() {
        db = FirebaseFirestore.getInstance();
        final String path = "user/" + MainActivity.userName + "/subscribe";
        registration = db.collection(path)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("dbListener", "Listen failed.");
                            return;
                        }
                        Log.d("dbListener", "change~" + MainActivity.userName);

                        mFavoriteItems.clear();
                        favoriteList.clear();
//                    for (QueryDocumentSnapshot doc : value) {
//                    }

                        db.collection(path)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                                Log.d("dbListener", doc.getId());
                                                setItems(doc);
                                            }
                                        }
                                    }
                                });
                    }

                    public void setItems(QueryDocumentSnapshot doc) {
                        String plane = doc.get("plane").toString();
                        String date = doc.get("date").toString();
                        String price = doc.get("price").toString();
                        String flyTime = doc.get("flyTime").toString();
                        String landTime = doc.get("landTime").toString();
                        String ori = doc.get("ori").toString();
                        String dst = doc.get("dst").toString();
                        String image = doc.get("img").toString();
                        String fromPrice = doc.get("fromPrice").toString();
                        String toPrice = doc.get("toPrice").toString();
                        String target = doc.get("target").toString();
                        favoriteList.add(target + "," + plane + "," + flyTime);
                        mFavoriteItems.add(new FavoriteItem(plane, date, price, ori, dst, flyTime, landTime, fromPrice, toPrice, image));
                        mFavoriteAdapter.notifyDataSetChanged();
                        if(SearchActivity.searchAdapter != null) {
                            SearchActivity.searchAdapter.notifyDataSetChanged();
                        }
                        checkPrice(price,toPrice);
                    }

                    public void checkPrice(String price,String toPrice) {
                        int intPrice = Integer.parseInt(price.split("\\$")[1].replace(",",""));
                        int intToPrice = Integer.parseInt(toPrice.split("\\$")[1].replace(",",""));
                        if (intPrice < intToPrice) {
                            sendNotification();
                        }
                    }
                });

    }

    public static ArrayList<String> getFavoriteList() {
        return  favoriteList;
    }

    public static void removeSnapListener() {
        mFavoriteItems.clear();
        mFavoriteAdapter.notifyDataSetChanged();
        registration.remove();
    }

    public void initBroadcastReceiver() {
        createNotificationChannel();
        subscriptionBroadcast = new SubscriptionBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Subscription_ACTION);
        getActivity().registerReceiver(subscriptionBroadcast, intentFilter);
    }

    public class SubscriptionBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    public void createNotificationChannel() {
        mNotifyManager =
                (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "notification_channel_name",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("notification_channel_description");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public static void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    private static NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(favActivity, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (favActivity, NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(favActivity, PRIMARY_CHANNEL_ID)
                .setContentTitle("Ticketing")
                .setContentText("There are some tickets meet your requirements")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(subscriptionBroadcast);
    }
}
