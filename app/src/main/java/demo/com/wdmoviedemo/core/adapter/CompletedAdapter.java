package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import demo.com.wdmoviedemo.bean.ObligationData;

/**
 * date: 2019/1/29.
 * Created 王思敏
 * function:已完成
 */
public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ViewHolder> {
    public static final String FORMAT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private Context context;
    private List<ObligationData> list;

    public CompletedAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.completed_recy, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //转换成日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_PATTERN,Locale.getDefault());
        viewHolder.txtMovieName.setText(list.get(i).getMovieName());
        viewHolder.txtOrderId.setText("订单号："+list.get(i).getOrderId());
        viewHolder.txtCinemaName.setText("影院："+list.get(i).getCinemaName());
        viewHolder.txtScreeningHall.setText("影厅："+list.get(i).getScreeningHall());
        viewHolder.txtCreateTime.setText(dateFormat.format(list.get(i).getCreateTime()));
        viewHolder.txtAmount.setText("数量："+list.get(i).getAmount());
        viewHolder.txtPrice.setText("金额："+list.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ObligationData> result) {
        if (result !=null){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtMovieName;
        private final TextView txtOrderId;
        private final TextView txtCinemaName;
        private final TextView txtScreeningHall;
        private final TextView txtCreateTime;
        private final TextView txtAmount;
        private final TextView txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txt_movieName);
            txtOrderId = itemView.findViewById(R.id.txt_orderId);
            txtCinemaName = itemView.findViewById(R.id.txt_cinemaName);
            txtScreeningHall = itemView.findViewById(R.id.txt_screeningHall);
            txtCreateTime = itemView.findViewById(R.id.txt_createTime);
            txtAmount = itemView.findViewById(R.id.txt_amount);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}
