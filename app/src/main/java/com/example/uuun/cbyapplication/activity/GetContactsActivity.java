package com.example.uuun.cbyapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.uuun.cbyapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 获取手机联系人页面
 */
public class GetContactsActivity extends BaseActivity {
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contacts);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lv = (ListView) findViewById(R.id.getContacts_lv);

        showListView();

    }
    private void showListView(){


        final ArrayList<HashMap<String, String>> list = getPeopleInPhone2();
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                list,
                android.R.layout.simple_list_item_2,
                new String[] {"peopleName", "phoneNum"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent();
                    intent.putExtra("returnPhone",list.get(i).get("phoneNum"));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
    }

    private ArrayList<HashMap<String, String>> getPeopleInPhone2(){
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);     // 获取手机联系人
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();

            int indexPeopleName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);    // people name
            int indexPhoneNum = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);            // phone number

            String strPeopleName = cursor.getString(indexPeopleName);
            String strPhoneNum = cursor.getString(indexPhoneNum);

            map.put("peopleName", strPeopleName);
            map.put("phoneNum", strPhoneNum);
            list.add(map);
        }
        if(!cursor.isClosed()){
            cursor.close();
            cursor = null;
        }

        return list;
    }
}
