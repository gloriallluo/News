package com.java.jingjia.util.graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.style.FontStyle;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DrawUtils;
import com.java.jingjia.Entity;
import com.java.jingjia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    public List<Entity> gData;
//    public List<List<Entity>> iData;

    public EntityExpandableListAdapter(List<Entity> gData,List<List<Entity>> iData, Context mContext) {
        this.gData = gData;
//        this.iData = iData;
        this.mContext = mContext;
    }



    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Entity getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Entity getChild(int groupPosition, int childPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @SuppressLint("DefaultLocale")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        EntityViewHolder groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_entity, parent, false);
        }else{
            groupHolder = (EntityViewHolder) convertView.getTag();
        }
        groupHolder = new EntityViewHolder(convertView,mContext);
        groupHolder.mLabel = convertView.findViewById(R.id.entity_item_label);
        convertView.setTag(groupHolder);
        groupHolder.mLabel.setText(gData.get(groupPosition).getLabel());
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("DefaultLocale")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        EntityDetailViewHolder itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_entity_detail, parent, false);
            itemHolder = new EntityDetailViewHolder(convertView, mContext);
            itemHolder.mAbstractInfo = convertView.findViewById(R.id.entity_item_info );
            itemHolder.mImg = convertView.findViewById(R.id.entity_item_img);
            itemHolder.mRelations = convertView.findViewById(R.id.entity_item_relations);
            itemHolder.mProperties = convertView.findViewById(R.id.entity_item_properties);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (EntityDetailViewHolder) convertView.getTag();
        }

        itemHolder.mAbstractInfo.setText(gData.get(groupPosition).getAbstractInfo());
        if(!gData.get(groupPosition).getImageUrl().equals(""))
            Picasso.get().load(gData.get(groupPosition).getImageUrl())
                    .resize(300,300).centerCrop()
                    .placeholder(R.color.white)
                    .into(itemHolder.mImg);

        if(!gData.get(groupPosition).getRelations().isEmpty()){
            //关系列
            Column<String> relation = new Column<>("关系", "relation");
            //方向类
            Column<Boolean> forward = new Column<>("方向", "forward",
                    new ImageResDrawFormat<Boolean>(60,60) {
                @Override
                protected Context getContext() {
                    return mContext;
                }
                @Override
                protected int getResourceID(Boolean isCheck, String value, int position) {
                    if(isCheck){
                        return R.mipmap.forward_true;
                    }else{
                        return R.mipmap.forward_false;
                    }
                }
            });

            Column<String> label = new Column<>("实体", "label");
            //表格数据 datas是需要填充的数据
            final TableData<Entity.Relation> tableData = new TableData
                    ("关系", gData.get(groupPosition).getRelations(),relation,forward,label);
            //设置数据
            itemHolder.mRelations.setZoom(true, 3, 1);//是否缩放
            itemHolder.mRelations.setTableData(tableData);
            FontStyle fontStyle = new com.bin.david.form.data.style.FontStyle(mContext,14, ContextCompat.getColor(mContext,R.color.arc_text));
            LineStyle lineStyle = new LineStyle();
            lineStyle.setColor(ContextCompat.getColor(mContext,R.color.yd_blue));
            lineStyle.setEffect(new DashPathEffect(new float[] {5, 5}, 0));
            itemHolder.mRelations.getConfig().setColumnTitleStyle(fontStyle);
            itemHolder.mRelations.getConfig().setHorizontalPadding(10)
                    .setVerticalPadding(10)
                    .setSequenceHorizontalPadding(0)
                    .setSequenceVerticalPadding(0)
                    .setContentGridStyle(lineStyle)
                    .setShowXSequence(false)
                    .setShowYSequence(false)
                    .setFixedYSequence(true);
//        itemHolder.mRelations.setZoom(true,2,0.5f);
            itemHolder.mRelations.getConfig().setTableGridFormat(new BaseGridFormat(){
                @Override
                protected boolean isShowYSequenceHorizontalLine(int row) {
                    return false;
                }
                @Override
                protected boolean isShowYSequenceVerticalLine(int row) {
                    return false;
                }
                @Override
                protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
                    return false;
                }
                @Override
                protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
                    return col == 3;
                }
                @Override
                public void drawTableBorderGrid(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {

                }
            });
            itemHolder.mRelations.getConfig().setYSequenceBackground(new IBackgroundFormat() {
                @Override
                public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                    DrawUtils.drawPatch(canvas,mContext,R.mipmap.set_bg,rect);
                }
            });
            itemHolder.mRelations.getConfig().setYSequenceCellBgFormat(new ICellBackgroundFormat<Integer>() {
                @Override
                public void drawBackground(Canvas canvas, Rect rect, Integer position, Paint paint) {

                }

                @Override
                public int getTextColor(Integer integer) {
                    return ContextCompat.getColor(mContext,R.color.white);
                }
            });
            itemHolder.mRelations.getConfig().setColumnCellBackgroundFormat(new ICellBackgroundFormat() {
                @Override
                public void drawBackground(Canvas canvas, Rect rect, Object o, Paint paint) {
                    DrawUtils.drawPatch(canvas,mContext,R.mipmap.set_bg,rect);
                }

                @Override
                public int getTextColor(Object o) {
                    return 0;
                }

            });
            itemHolder.mRelations.getTableData().getTableInfo().setTableRect(new Rect(0, 0, 30, 30));
        }

        if(!gData.get(groupPosition).getProperties().isEmpty()){
            //
            //普通列
            Column<String> pro = new Column<>("属性", "key");
            Column<Boolean> con = new Column<>("内容", "value");
            //表格数据 datas是需要填充的数据
            final TableData<Entity.Relation> pTableData = new TableData
                    ("属性", new ArrayList<Map.Entry>(gData.get(groupPosition).getProperties().entrySet()),pro,con);
            //设置数据
            itemHolder.mProperties.setZoom(true, 3, 1);//是否缩放
            itemHolder.mProperties.setTableData(pTableData);
            FontStyle fontStyle2 = new com.bin.david.form.data.style.FontStyle(mContext,14, ContextCompat.getColor(mContext,R.color.arc_text));
            LineStyle lineStyle2 = new LineStyle();
            lineStyle2.setColor(ContextCompat.getColor(mContext,R.color.yd_blue));
            lineStyle2.setEffect(new DashPathEffect(new float[] {5, 5}, 0));
            itemHolder.mProperties.getConfig().setColumnTitleStyle(fontStyle2);
            itemHolder.mProperties.getConfig().setHorizontalPadding(10)
                    .setVerticalPadding(10)
                    .setSequenceHorizontalPadding(0)
                    .setSequenceVerticalPadding(0)
                    .setContentGridStyle(lineStyle2)
                    .setShowXSequence(false)
                    .setShowYSequence(false)
                    .setFixedYSequence(true);
//        itemHolder.mRelations.setZoom(true,2,0.5f);
            itemHolder.mProperties.getConfig().setTableGridFormat(new BaseGridFormat(){
                @Override
                protected boolean isShowYSequenceHorizontalLine(int row) {
                    return false;
                }
                @Override
                protected boolean isShowYSequenceVerticalLine(int row) {
                    return false;
                }
                @Override
                protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
                    return false;
                }
                @Override
                protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
                    return col == 3;
                }
                @Override
                public void drawTableBorderGrid(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {

                }
            });
            itemHolder.mProperties.getConfig().setYSequenceBackground(new IBackgroundFormat() {
                @Override
                public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                    DrawUtils.drawPatch(canvas,mContext,R.mipmap.set_bg,rect);
                }
            });
            itemHolder.mProperties.getConfig().setYSequenceCellBgFormat(new ICellBackgroundFormat<Integer>() {
                @Override
                public void drawBackground(Canvas canvas, Rect rect, Integer position, Paint paint) {

                }

                @Override
                public int getTextColor(Integer integer) {
                    return ContextCompat.getColor(mContext,R.color.white);
                }
            });
            itemHolder.mProperties.getConfig().setYSequenceStyle(new FontStyle( ) {
            });
            itemHolder.mProperties.getConfig().setColumnCellBackgroundFormat(new ICellBackgroundFormat() {
                @Override
                public void drawBackground(Canvas canvas, Rect rect, Object o, Paint paint) {
                    DrawUtils.drawPatch(canvas,mContext,R.mipmap.set_bg,rect);
                }

                @Override
                public int getTextColor(Object o) {
                    return 0;
                }

            });
        }

        return convertView;
    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}