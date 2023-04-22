package com.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.ParentAdapter;

public class FragmentHealth extends Fragment implements ExpandableListView.OnGroupExpandListener, ParentAdapter.OnChildTreeViewClickListener, View.OnClickListener {

    private ExpandableListView eList;
    private ArrayList<ParentEntity> parents;
    private ParentAdapter adapter;
    private Button bt_search;
    private EditText et_search;

    private Intent intent;
   final List<String> list1= Arrays.asList("运动系统","消化系统","呼吸系统","泌尿系统","生殖系统","循环系统","内分泌系统","神经系统");
    final  List<String> list2= Arrays.asList("落枕、颈椎病、肩周炎、坐骨神经痛","胃食管反流、胃炎、消化性溃疡、胰腺疾病、肠道疾病","呼吸道感染、肺结核、慢性支气管炎、哮喘"
            ,"尿路感染、泌尿系统梗阻、泌尿系统肿瘤","生殖系统感染、性功能障碍相关疾病、生殖系统畸形和生殖系统肿瘤"
            ,"冠心病、心肌病、风湿性心脏病、动脉疾病、静脉疾病、微血管、疾病","糖尿病、甲状腺疾病、痛风"
            ,"脑梗死、脑出血、帕金森病、阿尔茨海默病、癫痫");
    final List<String> list3= Arrays.asList("预防","治疗","饮食");

    private void loadData() {

        parents = new ArrayList<ParentEntity>();
        for (int i = 0; i < list1.size(); i++) {
            ParentEntity parent = new ParentEntity(); //父类对象
            parent.setGroupName(list1.get(i));//假数据 ，设置一级列表数据
            parent.setGroupColor(Color.parseColor("#FF0000"));//红色 ，一级列表颜色

            ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();
            String[] split=list2.get(i).split("、");
            for (int j = 0; j < split.length; j++) {

                ChildEntity child = new ChildEntity(); //子类对象
                child.setGroupName(split[j]);//假数据 ，二级列表数据
                child.setGroupColor(Color.parseColor("#676565"));//紫色   二级列表颜色
//
                ArrayList<String> childNames = new ArrayList<String>();
                ArrayList<Integer> childColors = new ArrayList<Integer>();

                for (int k = 0; k < 3; k++) {
                    childNames.add(list3.get(k));//假数据 三级列表数据
                    childColors.add(Color.parseColor("#686262"));//紫色
                }
                child.setChildNames(childNames);
                childs.add(child);
            }
            parent.setChilds(childs);
            parents.add(parent);
        }
    }


    private void initEList() {

        eList.setOnGroupExpandListener(this);
        adapter = new ParentAdapter(getContext(), parents);//父类分组适配器
        eList.setAdapter(adapter);
        adapter.setOnChildTreeViewClickListener(this);//设置条目点击事件

    }


    @Override
    public void onClickPosition(int parentPosition, int groupPosition, int childPosition) {

        String childName1 = parents.get(parentPosition).getChilds()
                .get(groupPosition).getGroupName()
                .toString();
        String childName2 = parents.get(parentPosition).getChilds()
                .get(groupPosition).getChildNames().get(childPosition)
                .toString();
        intent.putExtra("disease", childName1+childName2+"方法");
        startActivity(intent);
    }


    @Override
    public void onGroupExpand(int groupPosition) {
        for (int i = 0; i < parents.size(); i++) {
            if (i != groupPosition) {
                eList.collapseGroup(i);//关闭状态
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        intent =new Intent(getContext(), BrowserActivity.class);
        eList = view.findViewById(R.id.eList);
        et_search = view.findViewById(R.id.et_search);
        et_search.setText("");
        bt_search = view.findViewById(R.id.bt_search);
        bt_search.setOnClickListener(this);
        bt_search = view.findViewById(R.id.bt_search);
        loadData();//获取数据，假数据
        initEList();//初始化
        return view;
    }

    @Override
    public void onClick(View v) {
        intent.putExtra("disease",et_search.getText().toString());
        startActivity(intent);
    }
}