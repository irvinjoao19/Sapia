package com.dsige.sapia.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
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
import com.dsige.sapia.context.repository.RoomRepository;
import com.dsige.sapia.context.retrofit.ApiRetrofit;
import com.dsige.sapia.context.retrofit.apiInterfaces.SapiaInterfaces;
import com.dsige.sapia.helper.Permission;
import com.dsige.sapia.helper.Util;
import com.dsige.sapia.model.Personal;
import com.dsige.sapia.ui.adapters.PersonalAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

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

    RoomRepository roomRepository;
    SapiaInterfaces sapiaInterfaces;
    PersonalAdapter personalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        roomRepository = new RoomRepository(this, this);
        bindToolbar();
        bindUI();
    }

    private void bindToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Personal");
    }

    private void bindUI() {
        fabPersonal.setOnClickListener(this);
        sapiaInterfaces = new ApiRetrofit().getAPI().create(SapiaInterfaces.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        personalAdapter = new PersonalAdapter((p, v, position) ->
                startActivityForResult(new Intent(this, RegisterPersonalActivity.class), Permission.PERSONAL_REQUEST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(personalAdapter);
        LiveData<List<Personal>> personalData = roomRepository.getPersonals();
        personalData.observe(this, personals -> personalAdapter.addItems(personals));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Permission.PERSONAL_REQUEST) {
            if (resultCode == Permission.PERSONAL_INSERT_REQUEST) {
                String personal = data.getStringExtra("personal");
                Personal p = new Gson().fromJson(personal, Personal.class);
                Completable completable = roomRepository.insertPersonal(p);
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
            }
        }
    }
}