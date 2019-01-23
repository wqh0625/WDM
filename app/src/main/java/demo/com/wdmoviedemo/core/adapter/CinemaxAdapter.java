package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import bawei.com.wdmoviedemo.R;
import demo.com.wdmoviedemo.bean.CarouselData;

/**
 * date: 2019/1/23.
 * Created 王思敏
 * function:
 */
public class CinemaxAdapter extends RecyclerView.Adapter<CinemaxAdapter.ViewHolder> {
    private Context context;
    private List<CarouselData> list;

    public CinemaxAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    //接口回调
    public interface OnMovieItemClickListener{
        void onMovieClick(int position);
    }
    public OnMovieItemClickListener mOnMovieItemClickListener;
    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener){
        mOnMovieItemClickListener = onMovieItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.recy_carousel, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.sdvImage.setImageURI(Uri.parse(list.get(i).getImageUrl()));
        viewHolder.txtContent.setText(list.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = list.get(i).getId();
                if (mOnMovieItemClickListener !=null){
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
        if (result !=null){
            list.addAll(result);
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
