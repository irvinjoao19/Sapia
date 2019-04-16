package com.dsige.sapia.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dsige.sapia.R;
import com.dsige.sapia.data.viewModel.PersonalViewModel;
import com.dsige.sapia.data.viewModel.ViewModelFactory;
import com.dsige.sapia.helper.Permission;
import com.dsige.sapia.helper.Util;
import com.dsige.sapia.data.local.model.Personal;
import com.dsige.sapia.ui.adapters.PersonalAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class PersonalActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabPersonal:
                startActivityForResult(new Intent(this, RegisterPersonalActivity.class), Permission.PERSONAL_REQUEST);
                break;
        }
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fabPersonal)
    FloatingActionButton fabPersonal;

    @Inject
    ViewModelFactory viewModelFactory;

    PersonalViewModel personalViewModel;
    PersonalAdapter personalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        personalViewModel = ViewModelProviders.of(this, viewModelFactory).get(PersonalViewModel.class);
        bindToolbar();
        bindUI();
    }

    private void bindToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Personal");
    }

    private void bindUI() {
        fabPersonal.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        personalAdapter = new PersonalAdapter(new PersonalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Personal p, View v, int position) {
                String personal = new Gson().toJson(p);
                startActivityForResult(new Intent(PersonalActivity.this, RegisterPersonalActivity.class)
                                .putExtra("personal", personal)
                                .putExtra("update", true)

                        , Permission.PERSONAL_REQUEST);
            }

            @Override
            public void onLongClick(Personal p, View v, int position) {
                deleteMensaje(p);
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(personalAdapter);
        LiveData<List<Personal>> personalData = personalViewModel.getPersonals();
        personalData.observe(this, personals -> personalAdapter.addItems(personals));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Permission.PERSONAL_REQUEST) {
            if (resultCode == Permission.PERSONAL_INSERT_REQUEST) {
                String personal = data.getStringExtra("personal");
                Personal p = new Gson().fromJson(personal, Personal.class);
                Completable completable = personalViewModel.insertPersonal(p);
                completable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Util.toastMensaje(PersonalActivity.this, "Personal Agregado");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("TAG", e.toString());
                            }
                        });
//                aprobacionAdapter.clearByPosition(position);
            } else if (resultCode == Permission.PERSONAL_UPDATE_REQUEST) {
                String personal = data.getStringExtra("personal");
                Personal p = new Gson().fromJson(personal, Personal.class);
                Completable completable = personalViewModel.updatePersonal(p);
                completable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Util.toastMensaje(PersonalActivity.this, "Personal Actualizado");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("TAG", e.toString());
                            }
                        });
            }

        }
    }

    private void deleteMensaje(Personal p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje")
                .setMessage(String.format("Deseas elimar a %s ?", p.getNombrePersonal().toUpperCase()))
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    personalViewModel.deletePersonal(p)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Util.toastMensaje(PersonalActivity.this, "Eliminado");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}