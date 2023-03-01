package com.project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.ParentEntity;
import com.project.R;

import java.util.ArrayList;

public class ParentAdapter extends BaseExpandableListAdapter {

    private Context mContext;// 上下文
    private ArrayList<ParentEntity> mParents;// 数据源
    private OnChildTreeViewClickListener mTreeViewClickListener;// 点击子ExpandableListView子项的监听

    public ParentAdapter(Context context, ArrayList<ParentEntity> parents) {
        this.mContext = context;
        this.mParents = parents;
    }

    //  获得某个父项的某个子项
    @Override
    public ChildEntity getChild(int groupPosition, int childPosition) {
        return mParents.get(groupPosition).getChilds().get(childPosition);
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return mParents.get(groupPosition).getChilds() != null ? mParents.get(groupPosition).getChilds().size() : 0;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final ExpandableListView eListView = getExpandableListView();
        ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();
        final ChildEntity child = getChild(groupPosition, childPosition);

        childs.add(child);
        final ChildAdapter childAdapter = new ChildAdapter(this.mContext, childs);//三级列表adapter
        eListView.setAdapter(childAdapter);

        /**
         *点击子ExpandableListView子项时，调用回调接口
         */
        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int groupIndex, int childIndex, long arg4) {

                if (mTreeViewClickListener != null) {
                    mTreeViewClickListener.onClickPosition(groupPosition, childPosition, childIndex);
                }
                return false;
            }
        });


        /**
         *         子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         *         （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         */
        eListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, (child.getChildNames().size() + 1) * (int) mContext.getResources().getDimension(R.dimen.parent_expandable_list_height));
                eListView.setLayoutParams(lp);
            }
        });

        /**
         *         子ExpandableListView关闭时，此时只剩下group这一项，
         *         所以子ExpandableListView的总高度即为一项的高度
         */
        eListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,  (int) mContext.getResources().getDimension(R.dimen.parent_expandable_list_height));
                eListView.setLayoutParams(lp);
            }
        });
        return eListView;

    }

    /**
     * 动态创建子ExpandableListView
     */
    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(mContext);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.parent_expandable_list_height));
        mExpandableListView.setLayoutParams(lp);
        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return mParents != null ? mParents.size() : 0;
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.parent_group_item, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.update(mParents.get(groupPosition));
        return convertView;
    }

    /**
     * Holder优化
     */
    class GroupHolder {

        private TextView parentGroupTV;
        public GroupHolder(View v) {
            parentGroupTV = (TextView) v.findViewById(R.id.parentGroupTV);
        }

        public void update(ParentEntity model) {
            parentGroupTV.setText(model.getGroupName());
            parentGroupTV.setTextColor(model.getGroupColor());
        }
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*@Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }*/

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 设置点击子ExpandableListView子项的监听
     */
    public void setOnChildTreeViewClickListener(OnChildTreeViewClickListener treeViewClickListener) {
        this.mTreeViewClickListener = treeViewClickListener;
    }

    /**
     * 点击子ExpandableListView子项的回调接口
     */
    public interface OnChildTreeViewClickListener {

        void onClickPosition(int parentPosition, int groupPosition, int childPosition);
    }

}