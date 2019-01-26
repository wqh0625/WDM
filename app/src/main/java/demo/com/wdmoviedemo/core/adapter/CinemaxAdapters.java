package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;

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

    public CinemaxAdapters(Context context,int type) {
        this.context = context;
        this.type = type;
        list = new ArrayList<>();
    }
    //接口回调
    public interface OnCinemaxItemClickListener {
        void onMovieClick(int position);
    }

    public OnCinemaxItemClickListener mOnCinemaxItemClickListener;

    public void setOnMovieItemClickListener(OnCinemaxItemClickListener onCinemaxItemClickListener){
        mOnCinemaxItemClickListener= onCinemaxItemClickListener;
    }
    public interface OnImageClickListener{
        void OnImageClick(int position,int followMovie);
    }
    public OnImageClickListener mOnImageClickListener;
    public void setOnImageClickListener(OnImageClickListener onImageClickListener){
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
        viewHolder.sdvImage.setImageURI(Uri.parse(list.get(i).getImageUrl()));
        viewHolder.txtName.setText(list.get(i).getName());
        viewHolder.txtContent.setText(list.get(i).getSummary());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据id获取
                int id = list.get(i).getId();
                if (mOnCinemaxItemClickListener !=null){
                    mOnCinemaxItemClickListener.onMovieClick(id);
                }
            }
        });
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ids = list.get(i).getId();
                int followMovie = list.get(i).getFollowMovie();
                if (mOnImageClickListener !=null){
                    mOnImageClickListener.OnImageClick(ids,followMovie);
                    if (followMovie ==1){
                        viewHolder.image.setBackgroundResource(R.drawable.icon_collection_selected);
                    }else {
                        viewHolder.image.setBackgroundResource(R.drawable.com_icon_collection_default);
                    }

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<CarouselData> result) {
        if (result!=null){
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
