package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
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

import demo.com.wdmoviedemo.bean.CommentData;

/**
 * date: 2019/1/29.
 * Created 王思敏
 * function:影院评论
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    public static final String FORMAT_DATE_TIME_PATTERN = "MM-dd HH:mm:ss";
    private Context context;
    private List<CommentData> list;

    public CommentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.comment_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.sdvImage.setImageURI(Uri.parse(list.get(i).getCommentHeadPic()));
        viewHolder.txtName.setText(list.get(i).getCommentUserName());
        viewHolder.txtContent.setText(list.get(i).getCommentContent());
        //转换成日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_PATTERN,Locale.getDefault());
        viewHolder.txtDate.setText(dateFormat.format(list.get(i).getCommentTime()));
        viewHolder.txtPraiseNum.setText(""+list.get(i).getGreatNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<CommentData> result) {
        if (result !=null){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView sdvImage;
        private final TextView txtName;
        private final TextView txtContent;
        private final TextView txtDate;
        private final ImageView imagePraise;
        private final TextView txtPraiseNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.comment_sdv_image);
            txtName = itemView.findViewById(R.id.comment_txt_name);
            txtContent = itemView.findViewById(R.id.comment_txt_content);
            txtDate = itemView.findViewById(R.id.comment_txt_date);
            imagePraise = itemView.findViewById(R.id.comment_image_praise);
            txtPraiseNum = itemView.findViewById(R.id.comment_txt_praisenum);
        }
    }
}
