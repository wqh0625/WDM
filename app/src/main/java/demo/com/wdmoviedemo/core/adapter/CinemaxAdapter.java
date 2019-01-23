package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import bawei.com.wdmoviedemo.R;
import demo.com.wdmoviedemo.bean.CarouselData;

/**
 * date: 2019/1/23.
 * Created by Administrator
 * function:
 */
public class CinemaxAdapter extends RecyclerView.Adapter<CinemaxAdapter.ViewHolder> {
    private Context context;
    private List<CarouselData> list;

    public CinemaxAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recy_carousel, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    viewHolder.sdvImage.setImageURI(Uri.parse(list.get(i).getImageUrl()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<CarouselData> result) {
        if (result !=null){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sdvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.sdv_image);
        }
    }
}
