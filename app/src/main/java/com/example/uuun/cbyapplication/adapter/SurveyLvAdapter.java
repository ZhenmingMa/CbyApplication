package com.example.uuun.cbyapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uuun.cbyapplication.R;
import com.example.uuun.cbyapplication.bean.QuestionOptions;
import com.example.uuun.cbyapplication.bean.ResultQuestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/13.
 */

public class SurveyLvAdapter extends BaseAdapter {

    public interface GetAnswer {
        public void getAnswer(QuestionOptions questionOptions);
    }

    ;
    private GetAnswer getAnswer;
    private Context context;
    private List<ResultQuestions> list = new ArrayList<>();
    private CheckBox checkBox;
    private boolean checks[];

    public SurveyLvAdapter(Context context, GetAnswer getAnswer) {

        this.context = context;
        this.getAnswer = getAnswer;
    }

    public List<ResultQuestions> getList() {
        return list;
    }

    public void setList(List<ResultQuestions> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
//        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.survey_lv_item, viewGroup, false);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.survey_lv_title);
            holder.ll = (LinearLayout) view.findViewById(R.id.survey_lv_rg);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
        holder.title.setText(i + 1 + "." + list.get(i).getQuestion().getContent());
        final ViewHolder finalHolder1 = holder;
        if (list.get(i).getQuestion().getType() == 1) {//-问题类型：1单选 2多选 3 填空

            RadioGroup rg = new RadioGroup(context);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(16, 0, 20, 0);//设置边距
            RadioButton rb = null;
            int j;
            for (j = 0; j < list.get(i).getQuestionOptions().size(); j++) {
                rb = new RadioButton(context);
                rb.setPadding(15, 0, 0, 0);
                rb.setLayoutParams(lp);
                rb.setText(list.get(i).getQuestionOptions().get(j).getContent());
                rb.setTextSize(15);
                rb.setTag(j);
                final int finalJ = j;
                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAnswer.getAnswer(list.get(i).getQuestionOptions().get(finalJ));
                    }
                });
                rg.addView(rb);

            }
            holder.ll.addView(rg);
        } else if (list.get(i).getQuestion().getType() == 2) {
            int j;
            checks = new boolean[list.get(i).getQuestionOptions().size()];
            for (j = 0; j < list.get(i).getQuestionOptions().size(); j++) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 0, 20, 0);
                checkBox = new CheckBox(context);
                checkBox.setText(list.get(i).getQuestionOptions().get(j).getContent());
                checkBox.setTag(i);
                checkBox.setPadding(15, 0, 0, 0);
                checkBox.setTextColor(Color.BLACK);
                checkBox.setLayoutParams(lp);
                final int finalJ = j;
                //  final int pos = i;
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        //  checks[pos] = b;
                        //         MyLog.info("!!!!!!!!!!!!!!"+i);
                        getAnswer.getAnswer(list.get(i).getQuestionOptions().get(finalJ));
                        if (b) {
                            int tag = (int) checkBox.getTag();
                            Toast.makeText(context, tag + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //checkBox = (CheckBox) holder.ll.getChildAt(list.get(i).getQuestion().getId());
                //  checkBox.setChecked(checks[pos]);
                holder.ll.addView(checkBox);
            }
        } else {
            EditText et = new EditText(context);
            et.setBackgroundResource(R.drawable.reward_edittext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(40, 20, 40, 20);
            holder.ll.addView(et, lp);
        }

        return view;
    }

    class ViewHolder {
        TextView title;
        LinearLayout ll;
    }
}
