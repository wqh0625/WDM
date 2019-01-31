package com.bw.movie.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import com.bw.movie.bean.FilmDetailsData;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:演员名字
 */
public class ActornameAdapter extends RecyclerView.Adapter<ActornameAdapter.ViewHolder> {
    private Context context;
    private List<FilmDetailsData> list;

    public ActornameAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.actorname_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtActorName.setText(list.get(i).getStarring());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<FilmDetailsData> result) {
        if (result !=null){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtActorName;
        private final TextView txtRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtActorName = itemView.findViewById(R.id.txt_actorname);
            txtRole = itemView.findViewById(R.id.txt_role);
        }
    }
}
