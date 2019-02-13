package com.bw.movie.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import com.bw.movie.bean.TicketDetailsData;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:购票详情页
 */
public class TicketDetailsAdapter extends RecyclerView.Adapter<TicketDetailsAdapter.ViewHolder> {
    private Context context;
    private List<TicketDetailsData> list;
    private SpannableString spannableString;

    public TicketDetailsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public interface OnImageClickLisener {
        void onImageClick(String ScreeningHall, String BeginTime, String EndTime, double Price,int id);
    }

    public OnImageClickLisener mOnImageClickLisener;

    public void setOnImageClickLisener(OnImageClickLisener onImageClickLisener) {
        mOnImageClickLisener = onImageClickLisener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.ticket_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.txtScreeningHall.setText(list.get(i).getScreeningHall());
        viewHolder.txtBeginTime.setText(list.get(i).getBeginTime());
        viewHolder.txtEndTime.setText(list.get(i).getEndTime());
        SpannableString spannableString = changTVsize("" + list.get(i).getPrice());
        viewHolder.txtPrice.setText(spannableString);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String screeningHall = list.get(i).getScreeningHall();
                String beginTime = list.get(i).getBeginTime();
                String endTime = list.get(i).getEndTime();
                int id = list.get(i).getId();
                double price = list.get(i).getPrice();
                if (mOnImageClickLisener != null) {
                    mOnImageClickLisener.onImageClick(screeningHall, beginTime, endTime, price,id);
                }
            }
        });
    }
    /**
     * 小数点前后大小不一致
     *
     * @param value
     * @return
     */
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.5f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<TicketDetailsData> result) {
        if (result != null) {
            list.addAll(result);
        }
    }
    public void clearList(){
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtScreeningHall;
        private final TextView txtBeginTime;
        private final TextView txtEndTime;
        private final TextView txtPrice;
        private final ImageView imgNext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtScreeningHall = itemView.findViewById(R.id.txt_screeningHall);
            txtBeginTime = itemView.findViewById(R.id.txt_beginTime);
            txtEndTime = itemView.findViewById(R.id.txt_endTime);
            txtPrice = itemView.findViewById(R.id.txt_price);
            imgNext = itemView.findViewById(R.id.img_next);
        }
    }
}
