package com.bw.movie.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import com.bw.movie.bean.CinemaDetailListData;

/**
 * date: 2019/1/24.
 * Created 王思敏
 * function:正在热映
 */
public class CinemaDetailAdapter extends RecyclerView.Adapter<CinemaDetailAdapter.ViewHolder> {
    private Context context;
    private List<CinemaDetailListData> list;

    public CinemaDetailAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.carousel_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CinemaDetailListData cinemaDetailListData = list.get(i);

        viewHolder.sdvImage.setImageURI(cinemaDetailListData.getImageurl());
        viewHolder.txtName.setText(cinemaDetailListData.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CinemaDetailListData> result) {
        if (result != null) {
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sdvImage;
        private final TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.home_sdv_image);
            txtName = itemView.findViewById(R.id.home_txt_name);
        }
    }
}
