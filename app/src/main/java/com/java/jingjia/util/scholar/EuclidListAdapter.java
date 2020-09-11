package com.java.jingjia.util.scholar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.java.jingjia.R;
import com.java.jingjia.ui.ScholarFragment;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import babushkatext.BabushkaText;

/**
 * Created by Oleksii Shliama on 1/27/15.
 */
public class EuclidListAdapter extends ArrayAdapter<Map<String, Object>> {

    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION_SHORT = "description_short";
    public static final String KEY_DESCRIPTION_FULL = "description_full";
    public static final String KEY_H_INDEX = "hIndex";
    public static final String KEY_ACTIVITY = "activity";
    public static final String KEY_SOCIALBILITY = "socialbility";
    public static final String KEY_CITATIONS = "citations";
    public static final String KEY_PUBS = "pubs";
    private static final String TAG = "EuclidListAdapter";

    private final LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public EuclidListAdapter(Context context, int layoutResourceId, List<Map<String, Object>> data) {
        super(context, layoutResourceId, data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView: ");
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mViewOverlay = convertView.findViewById(R.id.view_avatar_overlay);
            viewHolder.mListItemAvatar = (ImageView) convertView.findViewById(R.id.image_view_avatar);
            viewHolder.mListItemName = (TextView) convertView.findViewById(R.id.text_view_name);
            viewHolder.mListItemDescription = (TextView) convertView.findViewById(R.id.text_view_description);
            viewHolder.mBabushkaText_H = (BabushkaText) convertView.findViewById(R.id.babushka_text_H);
            viewHolder.mBabushkaText_A = (BabushkaText) convertView.findViewById(R.id.babushka_text_A);
            viewHolder.mBabushkaText_S = (BabushkaText) convertView.findViewById(R.id.babushka_text_S);
            viewHolder.mBabushkaText_C = (BabushkaText) convertView.findViewById(R.id.babushka_text_C);
            viewHolder.mBabushkaText_P = (BabushkaText) convertView.findViewById(R.id.babushka_text_P);

            convertView.setTag(viewHolder);



        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //List页
        Picasso.get().load((String)mData.get(position).get(KEY_AVATAR))
//                .resize(ScholarFragment.sScreenWidth, ScholarFragment.sProfileImageHeight).centerCrop()
                .placeholder(R.color.blue)
                .into(viewHolder.mListItemAvatar);

        viewHolder.mListItemName.setText(mData.get(position).get(KEY_NAME).toString().toUpperCase());
        viewHolder.mListItemDescription.setText((String) mData.get(position).get(KEY_DESCRIPTION_SHORT));
        viewHolder.mViewOverlay.setBackground(ScholarFragment.sOverlayShape);



        setMBabushka(viewHolder.mBabushkaText_H,
                new BabushkaText.Piece.Builder("  H  ")
                .backgroundColor(Color.parseColor("#073680"))
                .textColor(Color.WHITE)
                .build(),
                new BabushkaText.Piece.Builder("  " + ((Integer) mData.get(position).get(KEY_H_INDEX)).toString() + "  ")
                .backgroundColor(Color.parseColor("#DFF1FE"))
                .textColor(Color.parseColor("#073680"))
                .style(Typeface.BOLD)
                .build());
        setMBabushka(viewHolder.mBabushkaText_A,
                new BabushkaText.Piece.Builder("  A  ")
                        .backgroundColor(Color.parseColor("#800736"))
                        .textColor(Color.WHITE)
                        .build(),
                new BabushkaText.Piece.Builder(" " +(String.format("%.2f", mData.get(position).get(KEY_ACTIVITY)))+" ")
                        .backgroundColor(Color.parseColor("#fedfe2"))
                        .textColor(Color.parseColor("#800736"))
                        .style(Typeface.BOLD)
                        .build());
        setMBabushka(viewHolder.mBabushkaText_S,
                new BabushkaText.Piece.Builder("  S  ")
                        .backgroundColor(R.color.orange)
                        .textColor(Color.WHITE).build(),
                new BabushkaText.Piece.Builder(" " +(String.format("%.2f", mData.get(position).get(KEY_SOCIALBILITY)))+" ")
                        .backgroundColor(R.color.yd_grey)
                        .textColor(R.color.orange)
                        .style(Typeface.BOLD)
                        .build());
        setMBabushka(viewHolder.mBabushkaText_C,
                new BabushkaText.Piece.Builder("  C  ")
                        .backgroundColor(R.color.orange)
                        .textColor(Color.WHITE).build(),
                new BabushkaText.Piece.Builder("  " +((Integer)mData.get(position).get(KEY_CITATIONS)).toString()+"  ")
                        .backgroundColor(R.color.yd_grey)
                        .textColor(R.color.orange)
                        .style(Typeface.BOLD)
                        .build());
        setMBabushka(viewHolder.mBabushkaText_P,
                new BabushkaText.Piece.Builder("  P  ")
                        .backgroundColor(R.color.orange)
                        .textColor(Color.WHITE).build(),
                new BabushkaText.Piece.Builder("  " +((Integer)mData.get(position).get(KEY_PUBS)).toString()+"  ")
                        .backgroundColor(R.color.yd_grey)
                        .textColor(R.color.orange)
                        .style(Typeface.BOLD)
                        .build());
        return convertView;
    }

    private void setMBabushka(BabushkaText b, BabushkaText.Piece p1, BabushkaText.Piece p2) {
        if(b.getPiece(0) == null ) {
            b.addPiece(p1);
            b.addPiece(p2);
            b.display();
        }else{
            b.replacePieceAt(0, p1);
            b.replacePieceAt(1, p2);
        }
    }

    static class ViewHolder {
        View mViewOverlay;
        ImageView mListItemAvatar;
        TextView mListItemName;
        TextView mListItemDescription;
        BabushkaText mBabushkaText_H;
        BabushkaText mBabushkaText_A;
        BabushkaText mBabushkaText_S;
        BabushkaText mBabushkaText_C;
        BabushkaText mBabushkaText_P;
    }
}
