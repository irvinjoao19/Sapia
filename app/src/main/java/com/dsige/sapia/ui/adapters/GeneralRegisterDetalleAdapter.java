package com.dsige.sapia.ui.adapters;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dsige.sapia.R;
import com.dsige.sapia.helper.Util;
import com.dsige.sapia.model.SapiaRegistroDetalle;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralRegisterDetalleAdapter extends RecyclerView.Adapter<GeneralRegisterDetalleAdapter.ViewHolder> {

    private List<SapiaRegistroDetalle> detalles = new ArrayList<>();
    private int layout;
    private OnItemClickListener listener;

    public GeneralRegisterDetalleAdapter(OnItemClickListener listener) {

        this.listener = listener;
    }

    public void addItems(List<SapiaRegistroDetalle> detalles) {
        this.detalles.addAll(detalles);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_photo_general, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(detalles.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return detalles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.textViewDescripcion)
        TextView textViewDescripcion;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(SapiaRegistroDetalle d, OnItemClickListener listener) {
            File f = new File(Environment.getExternalStorageDirectory(), Util.FolderImg + "/" + d.getUrlPhoto());
            Picasso.get()
                    .load(f)
                    .into(imageViewPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            progressBar.setVisibility(View.GONE);
                            imageViewPhoto.setImageResource(R.drawable.photo_error);
                        }
                    });
            itemView.setOnClickListener(view -> listener.onItemClick(d, getAdapterPosition()));
            textViewDescripcion.setText(d.getDescripcion());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SapiaRegistroDetalle d, int position);
    }
}