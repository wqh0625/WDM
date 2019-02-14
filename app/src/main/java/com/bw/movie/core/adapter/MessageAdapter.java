package com.bw.movie.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bw.movie.bean.MessageData;

/**
 * date: 2019/1/29.
 * Created 王思敏
 * function:系统消息
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final String FORMAT_DATE_TIME_PATTERN = "HH:mm:ss";
    private Context context;
    private List<MessageData> list;

    public MessageAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }
    public OnHideText onHideText;

    public void setOnHideText(OnHideText onHideText) {
        this.onHideText = onHideText;
    }

    public interface OnHideText{
         void onHideText(int id ,int position,MessageData messageData);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_infor_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.Title.setText(list.get(i).getTitle());
        viewHolder.Content.setText(list.get(i).getContent());
        //转换日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_PATTERN,Locale.getDefault());
        viewHolder.Time.setText(dateFormat.format(list.get(i).getPushTime()));
        if (list.get(i).getStatus() == 0) {
            viewHolder.sd.setVisibility(View.VISIBLE);
        }else{
            viewHolder.sd.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onHideText.onHideText(list.get(i).getId(),i,list.get(i));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<MessageData> result) {
        if (result !=null){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView Title;
        private final TextView Content;
        private final TextView Time;
        private final TextView sd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.activity_infor_item_title);
            Content = itemView.findViewById(R.id.activity_infor_item_neirong);
            Time = itemView.findViewById(R.id.activity_infor_item_time);
            sd = itemView.findViewById(R.id.sd);
        }
    }
}
