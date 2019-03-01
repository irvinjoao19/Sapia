package com.dsige.sapia.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsige.sapia.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Util {

    public static final String FolderImg = "INSPECCION_IMG";
    private static String FechaActual;
    private static Date date;

    private static final int img_height_default = 800;
    private static final int img_width_default = 600;

    public static String getFechaActual() {
        date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        FechaActual = format.format(date);
        return FechaActual;
    }

    public static boolean getCompareFecha(String fecha) {
        date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        FechaActual = format.format(date);


        Date date1 = null;
        try {
            date1 = format.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = format.parse(FechaActual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(date1).before(date2);

    }

    public static String getFechaEditar() {
        date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        FechaActual = format.format(date);
        return FechaActual;
    }

    public static String getFechaPdf() {
        date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy_HHmmssSSS");
        FechaActual = format.format(date);
        return FechaActual;
    }

    public static String getFechaActualForPhoto(int pais, int proyecto, int delegacion, int grupo, int usuarioId) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy_HHmmssSSS");
        FechaActual = format.format(date);
        return pais + "_" + grupo + "_" + delegacion + "_" + proyecto + "_" + usuarioId + "_" + FechaActual + ".jpg";
    }

    public static void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                                  String msg) {
        textInputLayout.setError(msg);
        if (msg == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
        }
    }

    // TODO SOBRE ADJUNTAR PHOTO

    private static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source;
        FileChannel destination;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        destination.close();
    }

    private static String getRealPathFromURI(Context context, Uri contentUri) {
        String result = null;
        String[] proj = {MediaStore.Video.Media.DATA};
        @SuppressLint("Recycle") Cursor cursor = Objects.requireNonNull(context).getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }


    public static File getFolder() {
        File folder = new File(Environment.getExternalStorageDirectory(), FolderImg);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (success) {
                Log.i("Tag", "FOLDER CREATED");
            }
        }
        return folder;
    }

    public static String getFolderAdjunto(String file, Context context, Intent data) {
        String result = "";
        String imagepath = Environment.getExternalStorageDirectory() + "/" + FolderImg + "/" + file;
        File f = new File(imagepath);
        if (!f.exists()) {
            try {
                boolean success = f.createNewFile();
                if (success) {
                    Log.i("TAG", "FILE CREATED");
                }

                copyFile(new File(getRealPathFromURI(context, data.getData())), f);
                result = imagepath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    // TODO SOBRE FOTO

    public static Mensaje comprimirImagen(String PathFile) {
        try {
            return getRightAngleImage(PathFile);
        } catch (Exception ex) {
            Log.i("exception", ex.getMessage());
            return null;
        }
    }


    public static String getDateTimeFormatString(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss a");
        return df.format(date);
    }


    public static Bitmap ProcessingBitmap_SetDATETIME(Bitmap bm1, String captionString) {
        //Bitmap bm1 = null;
        Bitmap newBitmap = null;
        try {

            Bitmap.Config config = bm1.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);

            Canvas newCanvas = new Canvas(newBitmap);
            newCanvas.drawBitmap(bm1, 0, 0, null);

            if (captionString != null) {

                Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                paintText.setColor(Color.RED);
                paintText.setTextSize(22);
                paintText.setStyle(Paint.Style.FILL);
                paintText.setShadowLayer(0.7f, 0.7f, 0.7f, Color.YELLOW);

                Rect rectText = new Rect();
                paintText.getTextBounds(captionString, 0, captionString.length(), rectText);
                newCanvas.drawText(captionString, 0, rectText.height(), paintText);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newBitmap;
    }


    private static String CopyBitmatToFile(String filename, Bitmap bitmap) {
        try {
            File f = new File(filename);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            return "true";

        } catch (IOException ex) {
            return ex.getMessage();
        }
    }


    private static Bitmap ShrinkBitmap(String file) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inJustDecodeBounds = true;

        int heightRatio = (int) Math.ceil(options.outHeight / (float) Util.img_height_default);
        int widthRatio = (int) Math.ceil(options.outWidth / (float) Util.img_width_default);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                options.inSampleSize = heightRatio;
            } else {
                options.inSampleSize = widthRatio;
            }
        }

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(file, options);

    }


    private static Mensaje ShrinkBitmapOnlyReduce(String file) {

        Mensaje mensaje = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inJustDecodeBounds = true;

        int heightRatio = (int) Math.ceil(options.outHeight / (float) Util.img_height_default);
        int widthRatio = (int) Math.ceil(options.outWidth / (float) Util.img_width_default);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                options.inSampleSize = heightRatio;
            } else {
                options.inSampleSize = widthRatio;
            }
        }

        options.inJustDecodeBounds = false;

        try {

            Bitmap b = BitmapFactory.decodeFile(file, options);
            FileOutputStream fOut = new FileOutputStream(file);
            String imageName = file.substring(file.lastIndexOf("/") + 1);
            String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);

            FileOutputStream out = new FileOutputStream(file);
            if (imageType.equalsIgnoreCase("png")) {
                b.compress(Bitmap.CompressFormat.PNG, 70, out);
            } else if (imageType.equalsIgnoreCase("jpeg") || imageType.equalsIgnoreCase("jpg")) {
                b.compress(Bitmap.CompressFormat.JPEG, 70, out);
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            mensaje = new Mensaje(file, stream.toByteArray().length);

            fOut.flush();
            fOut.close();
            b.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mensaje;

    }

    // TODO SOBRE ROTAR LA PHOTO

    private static Mensaje getRightAngleImage(String photoPath) {

        Mensaje mensaje = null;
        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree;

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    degree = 0;
                    break;
                default:
                    degree = 90;
            }

            mensaje = rotateImage(degree, photoPath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mensaje;
    }

    private static Mensaje rotateImage(int degree, String imagePath) {
        Mensaje mensaje = null;

        if (degree <= 0) {
            return ShrinkBitmapOnlyReduce(imagePath);
        }
        try {
            //Bitmap b = BitmapFactory.decodeFile(imagePath);
            Bitmap b = ShrinkBitmap(imagePath);
            Matrix matrix = new Matrix();
            if (b.getWidth() > b.getHeight()) {
                matrix.setRotate(degree);
                b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
                        matrix, true);
            }

            FileOutputStream fOut = new FileOutputStream(imagePath);
            String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);

            FileOutputStream out = new FileOutputStream(imagePath);
            if (imageType.equalsIgnoreCase("png")) {
                b.compress(Bitmap.CompressFormat.PNG, 70, out);
            } else if (imageType.equalsIgnoreCase("jpeg") || imageType.equalsIgnoreCase("jpg")) {
                b.compress(Bitmap.CompressFormat.JPEG, 70, out);
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            mensaje = new Mensaje(imagePath, stream.toByteArray().length);

            fOut.flush();
            fOut.close();
            b.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mensaje;

    }


    // TODO SOBRE OBTENER IMEI Y VERSION

    public static String getVersion(Context context) {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getImei(Context context) {
        String deviceUniqueIdentifier;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceUniqueIdentifier = telephonyManager.getImei();
            } else {
                deviceUniqueIdentifier = telephonyManager.getDeviceId();
            }
        } else {
            deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }

//    Enviar Correo
//    Intent emailIntent = new Intent(Intent.ACTION_SEND);
//    emailIntent.setType("text/plain");
//    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gongorairvin@gmail.com,david.dsige@gmail.com"}); // recipients
//    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
//    emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
//    File myFile = new File(Environment.getExternalStorageDirectory() + "/" + Util.FolderPdf + "/HOLA.pdf");
//    Uri uri = Uri.fromFile(myFile);
//    emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
//    startActivity(emailIntent);

    // TODO MENSAJES

    public static void dialogMensaje(Context context, String mensaje) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));
//        builder.setTitle(R.string.message);
//        builder.setMessage(mensaje);
//        builder.setPositiveButton(R.string.aceptar, (d, which) -> d.dismiss());
//        AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
    }

    public static void toastMensaje(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }

    public static void snackBarMensaje(View view, String mensaje) {
        Snackbar mSnackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_SHORT);
        mSnackbar.setAction("Ok", v -> mSnackbar.dismiss());
        mSnackbar.show();
    }

    // TODO CLOSE TECLADO


    public static void hideKeyboard(Activity activity) {
        // TODO FOR ACTIVITIES
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void hideKeyboardFrom(Context context, View view) {
        // TODO FOR FRAGMENTS
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getDescripcion(String descripcion) {
        String result = "";
        switch (descripcion) {
            case "Sin Anomalia":
                result = "Normal";
                break;
            case "Anomalia baja":
                result = "Bajo";
                break;
            case "Anomalia media":
                result = "Medio";
                break;
            case "Anomalia alta":
                result = "Alto";
                break;
        }
        return result;
    }

    public static void getLocationName(Context context, TextInputEditText input, Location location, ProgressBar progressBar) {
        String[] nombre = {""};
        try {
            Observable<Address> addressObservable = Observable.just(new Geocoder(context).getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0));
            addressObservable.subscribeOn(Schedulers.io())
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Address>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Address address) {
                            nombre[0] = address.getAddressLine(0);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Util.toastMensaje(context, context.getString(R.string.try_again));
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onComplete() {
                            input.setText(nombre[0]);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } catch (IOException e) {
            Util.toastMensaje(context, e.toString());
            progressBar.setVisibility(View.GONE);
        }
    }
}