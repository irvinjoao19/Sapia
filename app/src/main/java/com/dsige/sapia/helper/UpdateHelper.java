package com.dsige.sapia.helper;

import android.content.Context;
import android.text.TextUtils;

public class UpdateHelper {

//    public static String KEY_UPDATE_ENABLE = "isUpdate";
//    public static final String KEY_UPDATE_VERSION = "version";
//    public static final String KEY_UPDATE_URL = "url";
//    public static final String KEY_UPDATE_NAME = "name";
//
//    public interface OnUpdateCheckListener {
//        void onUpdateCheckListener(String url, String name, String title);
//    }
//
//    public static Builder with(Context context) {
//        return new Builder(context);
//    }
//
//    private Context context;
//    private OnUpdateCheckListener onUpdateCheckListener;
//
//    private UpdateHelper(Context context, OnUpdateCheckListener onUpdateCheckListener) {
//        this.context = context;
//        this.onUpdateCheckListener = onUpdateCheckListener;
//    }
//
//    private void check() {
//        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
//        if (remoteConfig.getBoolean(KEY_UPDATE_ENABLE)) {
//            String currentVersion = remoteConfig.getString(KEY_UPDATE_VERSION);
//            String appVersion = Util.getVersion(context);
//            String updateURL = remoteConfig.getString(KEY_UPDATE_URL);
//            String name = remoteConfig.getString(KEY_UPDATE_NAME);
//            if (!TextUtils.equals(currentVersion, appVersion) && onUpdateCheckListener != null) {
//                onUpdateCheckListener.onUpdateCheckListener(updateURL, name, currentVersion);
//            }
//        }
//    }
//
//    public static class Builder {
//        private Context context;
//        private OnUpdateCheckListener onUpdateCheckListener;
//
//        public Builder(Context context) {
//            this.context = context;
//        }
//
//        public Builder onUpdateCheck(OnUpdateCheckListener onUpdateCheckListener) {
//            this.onUpdateCheckListener = onUpdateCheckListener;
//            return this;
//        }
//
//        public UpdateHelper build() {
//            return new UpdateHelper(context, onUpdateCheckListener);
//        }
//
//        public void check() {
//            UpdateHelper updateHelper = build();
//            updateHelper.check();
//        }
//    }
}