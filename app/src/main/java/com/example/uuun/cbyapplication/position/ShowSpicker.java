package com.example.uuun.cbyapplication.position;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShowSpicker extends AppCompatActivity {
    //TextView time,position;
    public static TimePickerView pvTime;

    public static OptionsPickerView pvOptions;//地址选择器
    public static ArrayList<ProvinceBean> options1Items = new ArrayList<>();//省
    public static ArrayList<ArrayList<CityBean>> options2Items = new ArrayList<>();//市
    public static ArrayList<ArrayList<ArrayList<AreaBean>>> options3Items = new ArrayList<>();//区
    public static ArrayList<String> Provincestr = new ArrayList<>();//省
    public static ArrayList<ArrayList<String>> Citystr = new ArrayList<>();//市
    public static ArrayList<ArrayList<ArrayList<String>>> Areastr = new ArrayList<>();//区

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    public static OptionsPickerView initPositionData(Context context) {
        //选项选择器
        pvOptions = new OptionsPickerView(context);
        // 获取数据库
        SQLiteDatabase db = DBManager.getdb((Application) context.getApplicationContext());
        //省
        Cursor cursor = db.query("province", null, null, null, null, null,
                null);
        while (cursor.moveToNext()) {
            int pro_id = cursor.getInt(0);
            String pro_code = cursor.getString(1);
            String pro_name = cursor.getString(2);
            String pro_name2 = cursor.getString(3);
            ProvinceBean provinceBean = new ProvinceBean(pro_id, pro_code, pro_name, pro_name2);
            options1Items.add(provinceBean);//添加一级目录
            Provincestr.add(cursor.getString(2));
            //查询二级目录，市区
            Cursor cursor1 = db.query("city", null, "province_id=?", new String[]{pro_id + ""}, null, null,
                    null);
            ArrayList<CityBean> cityBeanList = new ArrayList<>();
            ArrayList<String> cityStr = new ArrayList<>();
            //地区集合的集合（注意这里要的是当前省份下面，当前所有城市的地区集合我去）
            ArrayList<ArrayList<AreaBean>> options3Items_03 = new ArrayList<>();
            ArrayList<ArrayList<String>> options3Items_str = new ArrayList<>();
            while (cursor1.moveToNext()) {
                int cityid = cursor1.getInt(0);
                int province_id = cursor1.getInt(1);
                String code = cursor1.getString(2);
                String name = cursor1.getString(3);
                String provincecode = cursor1.getString(4);
                CityBean cityBean = new CityBean(cityid, province_id, code, name, provincecode);
                //添加二级目录
                cityBeanList.add(cityBean);
                cityStr.add(cursor1.getString(3));
                //查询三级目录
                Cursor cursor2 = db.query("area", null, "city_id=?", new String[]{cityid + ""}, null, null,
                        null);
                ArrayList<AreaBean> areaBeanList = new ArrayList<>();
                ArrayList<String> areaBeanstr = new ArrayList<>();
                while (cursor2.moveToNext()) {
                    int areaid = cursor2.getInt(0);
                    int city_id = cursor2.getInt(1);
//                    String code0=cursor2.getString(2);
                    String areaname = cursor2.getString(3);
                    String citycode = cursor2.getString(4);
                    AreaBean areaBean = new AreaBean(areaid, city_id, areaname, citycode);
                    areaBeanList.add(areaBean);
                    areaBeanstr.add(cursor2.getString(3));
                }
                options3Items_str.add(areaBeanstr);//本次查询的存储内容
                options3Items_03.add(areaBeanList);
            }
            options2Items.add(cityBeanList);//增加二级目录数据
            Citystr.add(cityStr);
            options3Items.add(options3Items_03);//添加三级目录
            Areastr.add(options3Items_str);
        }
        //设置三级联动效果
        pvOptions.setPicker(Provincestr, Citystr, Areastr, true);
        //设置选择的三级单位
//        pvOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        //设置是否循环滚动
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.show();
        return pvOptions;
    }

    public static TimePickerView initTimeView(Context context) {
        //时间选择器
        pvTime = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);
        //设置标题
        pvTime.setTitle("选择日期");
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 50, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        //设置是否循环
        pvTime.setCyclic(false);
        //设置是否可以关闭
        pvTime.setCancelable(true);
        //显示
        pvTime.show();


        return pvTime;
    }
}
