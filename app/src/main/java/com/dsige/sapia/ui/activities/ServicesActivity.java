package com.dsige.sapia.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;

import com.dsige.sapia.R;
import com.dsige.sapia.model.MenuPrincipal;
import com.dsige.sapia.ui.adapters.MenuAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServicesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        ButterKnife.bind(this);
        bindToolbar();
        bindUI();
    }

    private void bindToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_services);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void bindUI() {
        List<MenuPrincipal> menus = new ArrayList<>();
        menus.add(new MenuPrincipal(1, getResources().getString(R.string.tecnico), R.mipmap.ic_launcher));
        menus.add(new MenuPrincipal(2, getResources().getString(R.string.reporte_atencion), R.mipmap.ic_launcher));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        MenuAdapter menuAdapter = new MenuAdapter(menus, (m, view, position) -> {
            switch (m.getId()) {
                case 1:

                    break;
                case 2:
                    startActivity(new Intent(ServicesActivity.this, ReporteAtentionActivity.class));
                    break;
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuAdapter);
    }
}