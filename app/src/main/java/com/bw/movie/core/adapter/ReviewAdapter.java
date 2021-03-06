package com.bw.movie.core.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bw.movie.bean.FilmDetailsData;
import com.bw.movie.bean.ReviewData;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:影评
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public static final String FORMAT_DATE_TIME_PATTERN = "MM-dd HH:mm:ss";
    private Context context;
    private List<ReviewData> list;

    public ReviewAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    public ReviewData getItem(int position) {
        return list.get(position);
    }

    public interface ImagePraiseOnclickListener {
        void ImagePraiseOnclick(int i, int Id, ReviewData reviewData);
        void ImageComment(String i);
    }

    public ImagePraiseOnclickListener mImagePraiseOnclickListener;

    public void setImagePraiseOnclickListener(ImagePraiseOnclickListener imagePraiseOnclickListener) {
        mImagePraiseOnclickListener = imagePraiseOnclickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.review_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.sdvImage.setImageURI(Uri.parse(list.get(i).getCommentHeadPic()));
        viewHolder.txtName.setText(list.get(i).getCommentUserName());
        viewHolder.txtContent.setText(list.get(i).getCommentContent());
        //转换成日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_PATTERN, Locale.getDefault());
        viewHolder.txtDate.setText(dateFormat.format(list.get(i).getCommentTime()));
        viewHolder.txtPraiseNum.setText("" + list.get(i).getGreatNum());
        viewHolder.txtComment.setText("" + list.get(i).getReplyNum());
        if (list.get(i).getIsGreat() == 1) {
            viewHolder.imagePraise.setImageResource(R.drawable.com_icon_praise_selected);
        } else {
            viewHolder.imagePraise.setImageResource(R.drawable.com_icon_praise_default);
        }
        viewHolder.imagePraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImagePraiseOnclickListener != null) {
                    ReviewData reviewData = list.get(i);
                    mImagePraiseOnclickListener.ImagePraiseOnclick(i, reviewData.getCommentId(), reviewData);
                }
            }
        });
        viewHolder.imageComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePraiseOnclickListener.ImageComment("@ "+list.get(i).getCommentUserName()+" 回复：");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ReviewData> result) {
        if (result != null) {
            list.addAll(result);
        }
    }
    public void de( ) {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sdvImage;
        private final TextView txtName;
        private final TextView txtContent;
        private final TextView txtDate;
        private final ImageView imagePraise;
        private final TextView txtPraiseNum;
        private final ImageView imageComment;
        private final TextView txtComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.review_sdv_image);
            txtName = itemView.findViewById(R.id.review_txt_name);
            txtContent = itemView.findViewById(R.id.review_txt_content);
            txtDate = itemView.findViewById(R.id.review_txt_date);
            imagePraise = itemView.findViewById(R.id.review_image_praise);
            txtPraiseNum = itemView.findViewById(R.id.review_txt_praisenum);
            imageComment = itemView.findViewById(R.id.review_image_comment);
            txtComment = itemView.findViewById(R.id.review_txt_comment);
        }
    }
}
