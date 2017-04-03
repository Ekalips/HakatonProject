package com.ekalips.hakatonproject;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wldev on 4/3/17.
 */

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        Realm.init(this);
//        Realm.setDefaultConfiguration( new RealmConfiguration.Builder());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);


//        String token = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "onCreate: " + token);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        EventBus.builder().sendNoSubscriberEvent(true).installDefaultEventBus();
    }


    public static Application get() {
        return myApplication;
    }
}
