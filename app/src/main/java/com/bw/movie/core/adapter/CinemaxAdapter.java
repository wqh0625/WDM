package com.bw.movie.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CarouselData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * date: 2019/1/23.
 * Created 王思敏
 * function:首页多条目
 */
public class CinemaxAdapter extends RecyclerView.Adapter<CinemaxAdapter.ViewHolder> {
    private Context context;
    private List<CarouselData> list;
    private int type;
    public static final int CAROUSEL_TYPE = 0;
    public static final int ISHIT_TYPE = 1;
    public static final int COMING_TYPE = 2;

    public CinemaxAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        list = new ArrayList<>();
    }

    //接口回调
    public interface OnMovieItemClickListener {
        void onMovieClick(int position);
    }

    public OnMovieItemClickListener mOnMovieItemClickListener;
    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener){
        mOnMovieItemClickListener = onMovieItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (type == CAROUSEL_TYPE) {
            View view = View.inflate(context, R.layout.recy_carousel, null);
            return new ViewHolder(view);
        } else if (type == ISHIT_TYPE) {
            View view = View.inflate(context, R.layout.recy_ishit, null);
            return new ViewHolder(view);
        } else {
            View view = View.inflate(context, R.layout.recy_coming, null);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.sdvImage.setImageURI(Uri.parse(list.get(i).getImageUrl()));
        viewHolder.txtContent.setText(list.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = list.get(i).getId();
                if (mOnMovieItemClickListener != null) {
                    mOnMovieItemClickListener.onMovieClick(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<CarouselData> result) {
        if (result != null) {
            list.addAll(result);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sdvImage;
        private final TextView txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.sdv_image);
            txtContent = itemView.findViewById(R.id.txt_content);
        }
    }
}
