package com.dsige.sapia.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsige.sapia.R;
import com.dsige.sapia.model.MenuPrincipal;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuPrincipal> menus;
    private OnItemClickListener listener;

    public MenuAdapter(List<MenuPrincipal> menus, OnItemClickListener listener) {
        this.menus = menus;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(menus.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewTitulo)
        TextView textViewTitulo;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(MenuPrincipal m, OnItemClickListener listener) {
            imageViewPhoto.setImageResource(m.getImage());
            textViewTitulo.setText(m.getTitle());
            itemView.setOnClickListener(view -> listener.onItemClick(m, view, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MenuPrincipal m, View v, int position);
    }
}