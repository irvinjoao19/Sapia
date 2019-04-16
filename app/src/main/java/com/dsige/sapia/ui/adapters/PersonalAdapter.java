package com.dsige.sapia.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsige.sapia.R;
import com.dsige.sapia.data.local.model.Personal;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.ViewHolder> {

    private List<Personal> personals = Collections.emptyList();
    private OnItemClickListener listener;

    public PersonalAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addItems(List<Personal> personals1) {
        personals = personals1;
//        this.personals.addAll(personals);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_personal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(personals.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return personals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewAddress)
        TextView textViewAddress;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Personal p, OnItemClickListener listener) {
            textViewName.setText(p.getNombrePersonal().toUpperCase());
            textViewAddress.setText(p.getNombreCargo());
            itemView.setOnClickListener(view -> listener.onItemClick(p, view, getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                listener.onLongClick(p, v, getAdapterPosition());
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Personal p, View v, int position);

        void onLongClick(Personal p, View v, int position);
    }
}