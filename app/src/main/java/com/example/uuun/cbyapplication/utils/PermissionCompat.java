package com.example.uuun.cbyapplication.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Geh on 2017/2/25.
 */

public class PermissionCompat {

    public interface PermissionListener {
        void onGranted();

        void onDenied();
    }

    private static final int code = 100;
    private Activity activity;
    private Fragment fragment;
    private final String[] permissions;
    private ArrayList<String> denied;
    private String[] array;
    private PermissionListener listener;
    private static String description;
    private boolean isActivity = false;

    public PermissionCompat(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
        isActivity = true;
    }

    public PermissionCompat(Fragment fragment, String[] permissions) {
        this.fragment = fragment;
        this.permissions = permissions;
        isActivity = false;
    }

    public void setPermissionListener(PermissionListener listener) {
        this.listener = listener;
    }

    public void request() {
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//sdk版本小于23
            if (listener != null) {
                listener.onGranted();
            }
        }
        for (String permission : permissions) {
            if (isActivity) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    if (denied == null) {
                        denied = new ArrayList<>();
                    }
                    denied.add(permission);
                }
            } else {
                if (ContextCompat.checkSelfPermission(fragment.getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                    if (denied == null) {
                        denied = new ArrayList<>();
                    }
                    denied.add(permission);
                }
            }
        }

        if (denied == null) {
            if (isActivity) {
//                Toast.makeText(activity, R.string.perssion_all_granted, Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(fragment.getActivity(), R.string.perssion_all_granted, Toast.LENGTH_SHORT).show();
            }
            if (listener != null) {
                listener.onGranted();
            }
        } else {
            array = new String[denied.size()];
            if (isActivity) {
                ActivityCompat.requestPermissions(activity, denied.toArray(array), code);
            } else {
                fragment.requestPermissions(denied.toArray(array), code);
            }
//            for (String s : array) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, s)) {
//                    createDialog(s);
//                }
//            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (array == null) {
            if (listener != null) {
                listener.onGranted();
            }
        } else {
            if (requestCode == code) {
                int len = grantResults.length;
                for (int i = 0; i < len; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED && !hasShow) {
                        createDialog(permissions[i]);
                        hasShow = true;
                    } else {
                        if (listener != null) {
                            listener.onGranted();
                        }
                    }
                }
            }
        }
        if (denied != null) {
            denied.clear();
        }
        array = null;
    }

    private boolean hasShow = false;
    private int id;

    private void createDialog(String description) {
//        FragmentManager ft=activity.getSupportFragmentManager();
//        FragmentTransaction tr =ft.beginTransaction();
//        PermissionDialogFragment a= (PermissionDialogFragment) ft.findFragmentByTag(description);
//        tr.remove(a).commit();
//        tr =ft.beginTransaction();
//        a = new PermissionDialogFragment();
////        ft.popBackStack(id,0);
////        FragmentTransaction tr=ft.beginTransaction();
//        a.show(tr,description);

        AlertDialog.Builder builder = null;
        if (isActivity) {
            builder = new AlertDialog.Builder(activity);
        } else {
            builder = new AlertDialog.Builder(fragment.getActivity());
        }
        builder.setMessage("需要权限:" + description);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                if (isActivity) {
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivity(intent);
                } else {
                    intent.setData(Uri.parse("package:" + fragment.getActivity().getPackageName()));
                    fragment.getActivity().startActivity(intent);
                }
                hasShow = false;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDenied();
                hasShow = false;
            }
        }).show();
    }

    public void createDescriptionDialog(String description) {

        AlertDialog.Builder builder = null;
        if (isActivity) {
            builder = new AlertDialog.Builder(activity);
        } else {
            builder = new AlertDialog.Builder(fragment.getActivity());
        }
        builder.setMessage("需要权限.." + description);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    public static class PermissionDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("需要权限.." + description);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }
}