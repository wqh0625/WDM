package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.bean.TicketDetailsData;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:购票详情页
 */
public class TicketDetailsAdapter extends RecyclerView.Adapter<TicketDetailsAdapter.ViewHolder> {
    private Context context;
    private List<TicketDetailsData> list;

    public TicketDetailsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.ticket_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    viewHolder.txtScreeningHall.setText(list.get(i).getScreeningHall());
    viewHolder.txtBeginTime.setText(list.get(i).getBeginTime());
    viewHolder.txtEndTime.setText(list.get(i).getEndTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtScreeningHall;
        private final TextView txtBeginTime;
        private final TextView txtEndTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtScreeningHall = itemView.findViewById(R.id.txt_screeningHall);
            txtBeginTime = itemView.findViewById(R.id.txt_beginTime);
            txtEndTime = itemView.findViewById(R.id.txt_endTime);
        }
    }
}
