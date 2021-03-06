package com.bw.movie.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CarouselData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * date: 2019/1/24.
 * Created 王思敏
 * function:详情适配器
 */
public class CinemaxAdapters extends RecyclerView.Adapter<CinemaxAdapters.ViewHolder> {
    private Context context;
    private List<CarouselData> list;
    private int type;
    public static final int CAROUSEL_TYPE = 0;
    public static final int ISHIT_TYPE = 1;
    public static final int COMING_TYPE = 2;

    public CinemaxAdapters(Context context, int type) {
        this.context = context;
        this.type = type;
        list = new ArrayList<>();
    }

    public CarouselData getItem(int i) {
        return list.get(i);
    }

    //接口回调
    public interface OnCinemaxItemClickListener {
        void onMovieClick(int position);
    }

    public OnCinemaxItemClickListener mOnCinemaxItemClickListener;

    public void setOnMovieItemClickListener(OnCinemaxItemClickListener onCinemaxItemClickListener) {
        mOnCinemaxItemClickListener = onCinemaxItemClickListener;
    }

    public interface OnImageClickListener {
        void OnImageClick(int i, int position, CarouselData carouselData);

    }

    public OnImageClickListener mOnImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (type == CAROUSEL_TYPE) {
            View view = View.inflate(context, R.layout.cinemax_recy, null);
            return new CinemaxAdapters.ViewHolder(view);
        } else if (type == ISHIT_TYPE) {
            View view = View.inflate(context, R.layout.ishit_recy, null);
            return new CinemaxAdapters.ViewHolder(view);
        } else {
            View view = View.inflate(context, R.layout.coming_recy, null);
            return new CinemaxAdapters.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final CarouselData carouselData = list.get(i);
        viewHolder.sdvImage.setImageURI(Uri.parse(carouselData.getImageUrl()));
        viewHolder.txtName.setText(carouselData.getName());
        viewHolder.txtContent.setText(carouselData.getSummary());
        if (carouselData.getFollowMovie() == 1) {
            viewHolder.image.setImageResource(R.drawable.icon_collection_selected);
        } else {
            viewHolder.image.setImageResource(R.drawable.com_icon_collection_default);
        }
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ids = carouselData.getId();
                if (mOnImageClickListener != null) {
                    mOnImageClickListener.OnImageClick(i, ids, list.get(i));
                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据id获取
                int id = carouselData.getId();
                if (mOnCinemaxItemClickListener != null) {
                    mOnCinemaxItemClickListener.onMovieClick(id);
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
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sdvImage;
        private final TextView txtName;
        private final ImageView image;
        private final TextView txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.cinemax_sdv_image);
            txtName = itemView.findViewById(R.id.cinemax_txt_name);
            image = itemView.findViewById(R.id.cinemax_image);
            txtContent = itemView.findViewById(R.id.cinemax_txt_content);
        }
    }
}
