package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import demo.com.wdmoviedemo.bean.CarouselData;

/**
 * date: 2019/1/24.
 * Created 王思敏
 * function:
 */
public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {
    public static final String FORMAT_DATE_TIME_PATTERN = "mm";
    private Context context;
    private List<CarouselData> list;

    public CarouselAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
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
    viewHolder.sdvImage.setImageURI(Uri.parse(list.get(i).getImageUrl()));
    viewHolder.txtName.setText(list.get(i).getName());
        //转换成日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_PATTERN,Locale.getDefault());
        viewHolder.txtTime.setText(dateFormat.format(list.get(i).getReleaseTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CarouselData> result) {
        if (result !=null){
            list.clear();
            list.addAll(result);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sdvImage;
        private final TextView txtName;
        private final TextView txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.home_sdv_image);
            txtName = itemView.findViewById(R.id.home_txt_name);
            txtTime = itemView.findViewById(R.id.home_txt_time);
        }
    }
}
