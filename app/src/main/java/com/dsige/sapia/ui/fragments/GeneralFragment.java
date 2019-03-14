package com.dsige.sapia.ui.fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dsige.sapia.R;
import com.dsige.sapia.helper.Mensaje;
import com.dsige.sapia.helper.Permission;
import com.dsige.sapia.helper.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class GeneralFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabGeneral:
                break;
        }
    }

    private String Direccion;
    private File folder;
    private String nameImg;

    private static String ARG_PARAM1 = "param1";
    private static String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    @BindView(R.id.editTextDetalle)
    TextInputEditText editTextDetalle;
    @BindView(R.id.fabGeneral)
    FloatingActionButton fabGeneral;


    private Unbinder unbinder;

    public GeneralFragment() {
        // Required empty public constructor
    }

    public static GeneralFragment newInstance(String param1, String param2) {
        GeneralFragment fragment = new GeneralFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        bindUI(view);
        return view;
    }

    private void bindUI(View view) {
        unbinder = ButterKnife.bind(this, view);
        fabGeneral.setOnClickListener(this);
        microTouchListener(editTextDetalle, Permission.SPEECH_REQUEST_DETALLE, getString(R.string.detalle));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }

//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void microPhone(String titulo, int permission, View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, titulo);

        try {
            startActivityForResult(intent, permission);
        } catch (ActivityNotFoundException e) {
            Util.snackBarMensaje(view, getString(R.string.compatible_device));
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void microTouchListener(TextInputEditText input, int permission, String title) {
        input.setOnTouchListener((v, event) -> {
            int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (input.getRight() - input
                        .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    microPhone(title, permission, v);

                    return true;
                }
            }
            return false;
        });
    }

    private void createImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            folder = Util.getFolder();
            nameImg = Util.getFechaActualForPhoto();
            File image = new File(folder, nameImg);
            Direccion = folder + "/" + nameImg;
            Uri uriSavedImage = Uri.fromFile(image);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    String valor = "disableDeathOnFileUriExposure";
                    Method m = StrictMode.class.getMethod(valor);
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            startActivityForResult(takePictureIntent, Permission.CAMERA_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Permission.SPEECH_REQUEST_DETALLE && resultCode == RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String y = result.get(0).replace(" ", "");
            editTextDetalle.setText(y);
        } else if (requestCode == Permission.CAMERA_REQUEST && resultCode == RESULT_OK) {
            Mensaje mensaje = Util.comprimirImagen(Direccion);
            if (mensaje == null) {
                Util.snackBarMensaje(getView(), "Error");
            } else {
//                saveDetalleFotoInspeccion(anomaliaId, ResultadoId, DetalleInspeccionId, nameImg, mensaje.getHeader());
            }
        }
    }
}