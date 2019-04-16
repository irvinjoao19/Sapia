package com.dsige.sapia.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dsige.sapia.R;
import com.dsige.sapia.helper.Permission;
import com.dsige.sapia.helper.Util;
import com.dsige.sapia.data.local.model.Personal;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Objects;

public class RegisterPersonalActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        String nombre = Objects.requireNonNull(editTextNombre.getText()).toString();
        String cargo = Objects.requireNonNull(editTextCargo.getText()).toString();
        if (!nombre.isEmpty()) {
            if (!cargo.isEmpty()) {
                if (update) {
                    p.setNombrePersonal(nombre);
                    p.setNombreCargo(cargo);
                    String personal = new Gson().toJson(p);
                    Intent i = new Intent().putExtra("personal", personal);
                    setResult(Permission.PERSONAL_UPDATE_REQUEST, i);
                } else {
                    Personal p = new Personal();
                    p.setCargoId(1);
                    p.setNombrePersonal(nombre);
                    p.setNombreCargo(cargo);
                    String personal = new Gson().toJson(p);
                    Intent i = new Intent().putExtra("personal", personal);
                    setResult(Permission.PERSONAL_INSERT_REQUEST, i);
                }
                finish();
            } else {
                Util.snackBarMensaje(v, "Ingrese cargo");
            }
        } else {
            Util.snackBarMensaje(v, "Ingrese nombre");
        }
    }

    @BindView(R.id.editTextNombre)
    TextInputEditText editTextNombre;
    @BindView(R.id.editTextCargo)
    TextInputEditText editTextCargo;

    @BindView(R.id.fabRegister)
    ExtendedFloatingActionButton fabRegister;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Personal p;
    boolean update = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_personal);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            p = new Gson().fromJson(b.getString("personal"), Personal.class);
            update = b.getBoolean("update");
        }
        bindToolbar(p);
        bindUI(p);
    }

    private void bindToolbar(Personal p) {
        setSupportActionBar(toolbar);
        if (p != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Actualizar");
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Registrar");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void bindUI(Personal p) {
        fabRegister.setOnClickListener(this);
        if (p != null) {
            editTextNombre.setText(p.getNombrePersonal());
            editTextCargo.setText(p.getNombreCargo());
            fabRegister.setText(String.format("%s", "Actualizar"));
        }
    }
}