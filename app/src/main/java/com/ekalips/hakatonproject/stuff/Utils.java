package com.ekalips.hakatonproject.stuff;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.internal.util.Predicate;
import com.ekalips.hakatonproject.MyApplication;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.auth.SignUpActivity;
import com.ekalips.hakatonproject.events.TakePictureFromCameraEvent;
import com.ekalips.hakatonproject.user.User;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by wldev on 12/28/16.
 */


public class Utils {

    private static final String TAG = Utils.class.getSimpleName();


    public static void showToastMessage(@StringRes int messageResId) {
        showToastMessage(MyApplication.get(), messageResId, Toast.LENGTH_SHORT);
    }

    public static void showToastMessage(String messageRes) {
        showToastMessage(MyApplication.get(), messageRes, Toast.LENGTH_SHORT);
    }

    public static void showToastMessage(Context context, @StringRes int messageResId) {
        showToastMessage(context, messageResId, Toast.LENGTH_SHORT);
    }

    public static void showToastMessage(Context context, String messageRes) {
        showToastMessage(context, messageRes, Toast.LENGTH_SHORT);
    }

    public static void showToastMessage(Context context, @StringRes int messageResId, int length) {
        showToastMessage(context, context.getString(messageResId), length);
    }

    public static void showToastMessage(Context context, String message, int length) {
        Toast t = Toast.makeText(context, (message), length);
        t.show();
    }

    public static int parseHexToDec(String hex) {
        return (int) Long.parseLong(hex.replace("#", "").toUpperCase(), 16);
    }

    public static String parseMillsToDate(long mills) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.US);
        return format.format(new Date(mills));
    }

    public static String parseMillsToDate(long mills, boolean useUNIX) {
//        Log.d("parseMillsToDate", "parseMillsToDate: " + mills + " use: " + useUNIX);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.US);
        if (useUNIX)
            mills *= 1000;
        return format.format(new Date(mills));
    }

    public static String parseMillsToRelativeDate(long mills) {
        if (DateUtils.isToday(mills))
            return "Today";
        if (DateUtils.isToday(mills + DateUtils.DAY_IN_MILLIS))
            return "Yesterday";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.US);
        return format.format(new Date(mills));
    }

    public static String parseMillsToRelativeDate(long mills, boolean useUNIX) {
        if (useUNIX)
            mills *= 1000;
        if (DateUtils.isToday(mills))
            return "Today";
        if (DateUtils.isToday(mills + DateUtils.DAY_IN_MILLIS))
            return "Yesterday";

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.US);
        return format.format(new Date(mills));
    }

    public static String parseMillsToHoursAndMins(long mills) {
        Date date = new Date(mills);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.UK);
        return format.format(date);
    }

    public static String parseMillsToDateAndHoursAndMins(long mills, boolean useUNIX, boolean use24Hours) {
        if (useUNIX)
            mills *= 1000;
        Date date = new Date(mills);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy, HH:mm" + (use24Hours ? "" : " a"), Locale.UK);
        return format.format(date);
    }

    public static String parseMillsToHoursAndMins(long mills, boolean useUNIX, boolean use24Hours) {
        if (useUNIX)
            mills *= 1000;
        Date date = new Date(mills);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm" + (use24Hours ? "" : " a"), Locale.UK);
        return format.format(date);
    }

    public static String parseMillsToRelativeHoursAndMins(long mills, boolean useUNIX) {
        if (useUNIX)
            mills *= 1000;
        if (DateUtils.isToday(mills))
            return parseMillsToHoursAndMins(mills, false, true);
        else
            return parseMillsToRelativeDate(mills);
    }

    public static void hideKeyboard(AppCompatActivity appCompatActivity) {
        View view = (appCompatActivity).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static int floatToInt(float f) {
        return (int) f;
    }


    public static boolean validateEmail(String email) {
        return new EmailValidator().apply(email);
    }

    public static boolean validatePhone(String phone) {
        return new PhoneValidator().apply(phone);
    }

    public static boolean stringNotEmpty(String s) {
        return !s.trim().replace(" ", "").isEmpty();
    }

    public static boolean validateAndPut(final @NonNull String key, String val, Predicate<String> tester,
                                         Map<String, String> dst) {
        if (tester.apply(val)) {
            dst.put(key, val);
            return true;
        } else {
            return false;
        }
    }

    public static void aggreOrDeclineDialog(Context context, String title, String message, String acceptText, String declineText, DialogInterface.OnClickListener accept, DialogInterface.OnClickListener decline) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(acceptText, accept);
        builder.setNegativeButton(declineText, decline);
        builder.show();
    }

    public static void aggreOrDeclineDialog(Context context, String title, String message, DialogInterface.OnClickListener accept, DialogInterface.OnClickListener decline) {
        aggreOrDeclineDialog(context, title, message, context.getString(android.R.string.yes), context.getString(android.R.string.no), accept, decline);
    }

    public static void infoDialog(Context context, String title, String message) {
        infoDialog(context, title, message, context.getString(android.R.string.yes));
    }

    public static void infoDialog(Context context, String title, String message, String okString) {
        infoDialog(context, title, message, okString, null);
    }

    public static void infoDialog(Context context, String title, String message, String okString, DialogInterface.OnClickListener onOnClick) {
        infoDialog(context, title, message, okString, onOnClick, null);
    }

    public static void infoDialog(Context context, String title, String message, String okString, DialogInterface.OnClickListener onOnClick, DialogInterface.OnCancelListener onCancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(okString, onOnClick);
        builder.setOnCancelListener(onCancelListener);
        builder.show();
    }

    private static void restart(Context context) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
        User.getInstance().clear();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        System.exit(0);
    }



    public static int colorFromString(String string) {
        return ColorGenerator.MATERIAL.getColor(string == null ? "" : string);
    }


    public static boolean isSameDay(long date1, long date2) {
        return isSameDay(new Date(date1), new Date(date2));
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public static String getPathFromCharRole(int role, boolean isForFetching) {
        if (isForFetching) {
            switch (role) {
                case 0:
                    return "messages";
                case 1:
                    return "chat";
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            switch (role) {
                case 0:
                    return "client-chat";
                case 1:
                    return "admin-chat";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public static String intArrayToString(int[] array) {
        StringBuilder builder = new StringBuilder();
        for (int i :
                array) {
            builder.append(i);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    public static int getInt(Object o) {
        if (o instanceof String)
            return Integer.valueOf((String) o);
        else
            return (int) o;
    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }

    public static MultipartBody.Part createFilePart(Uri uri, File file, String partName) {
        final RequestBody requestFile = RequestBody.create(MediaType.parse(MyApplication.get().getContentResolver().getType(uri)), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static MultipartBody.Part createFilePart(Uri uri, File file, String partName, String fileType) {
        final RequestBody requestFile = RequestBody.create(MediaType.parse(fileType), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    public static File dispatchTakePictureIntent(final Activity activity, final int requestCode) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Utils.aggreOrDeclineDialog(activity, MyApplication.get().getString(R.string.permission_request_title), MyApplication.get().getString(R.string.permission_request_message_camera), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, requestCode);
                }
            }, null);
            return null;
        }

        File exitImageFile = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(MyApplication.get().getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                exitImageFile = createTemporaryImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (exitImageFile != null) {
                EventBus.getDefault().post(new TakePictureFromCameraEvent(exitImageFile));
                Uri photoURI = FileProvider.getUriForFile(MyApplication.get(),
                        MyApplication.get().getPackageName() + ".provider",
                        exitImageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                ActivityCompat.startActivityForResult(activity, takePictureIntent, requestCode, null);
            }
        }
        return exitImageFile;
    }

    public static File dispatchTakePictureIntent(final Fragment fragment, final int requestCode) {
        if (ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Utils.aggreOrDeclineDialog(fragment.getContext(), MyApplication.get().getString(R.string.permission_request_title), MyApplication.get().getString(R.string.permission_request_message_camera), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
                }
            }, null);
            return null;
        }

        File exitImageFile = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(MyApplication.get().getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                exitImageFile = createTemporaryImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (exitImageFile != null) {
                EventBus.getDefault().post(new TakePictureFromCameraEvent(exitImageFile));
                Uri photoURI = FileProvider.getUriForFile(MyApplication.get(),
                        MyApplication.get().getPackageName() + ".provider",
                        exitImageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fragment.startActivityForResult(takePictureIntent, requestCode, null);
            }
        }
        return exitImageFile;
    }

    public static File createTemporaryImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(new Date());
        String imageFileName = "Image" + timeStamp + "";
        File storageDir = new File(MyApplication.get().getFilesDir(), "images");
        if (!storageDir.exists())
            storageDir.mkdirs();
        File result = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        result.deleteOnExit();
        return result;
    }


    public static void showSnackBarMessage(View view, @StringRes int messageRes) {
        showSnackBarMessage(view, MyApplication.get().getString(messageRes));
    }

    public static void showSnackBarMessage(View view, @StringRes int messageRes, @StringRes int actionMessageRes, View.OnClickListener actionOnClick) {
        showSnackBarMessage(view, MyApplication.get().getString(messageRes), MyApplication.get().getString(actionMessageRes), actionOnClick);
    }

    public static void showSnackBarMessage(View view, @StringRes int messageRes, @StringRes int actionMessageRes, View.OnClickListener actionOnClick, int lenght) {
        showSnackBarMessage(view, MyApplication.get().getString(messageRes), MyApplication.get().getString(actionMessageRes), actionOnClick, lenght);
    }

    public static void showSnackBarMessage(View view, String message) {
        showSnackBarMessage(view, message, null, null);
    }

    public static void showSnackBarMessage(View view, String message, String actionMessage, View.OnClickListener actionOnClick) {
        showSnackBarMessage(view, message, actionMessage, actionOnClick, Snackbar.LENGTH_SHORT);
    }

    public static void showSnackBarMessage(View view, String message, final String actionMessage, final View.OnClickListener actionOnClick, int lenght) {
        final Snackbar snackbar = Snackbar.make(view, message, lenght);
        if (actionMessage != null) {
            View.OnClickListener realActionOnClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionOnClick != null)
                        actionOnClick.onClick(v);
                    snackbar.dismiss();
                }
            };
            snackbar.setAction(actionMessage, realActionOnClick);
        }
        snackbar.show();
    }

    public static void startCreateTeamProcess(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.putExtra(StepperActivity.EXTRA_STEP,new int[]{StepType.createTeam.getType(),StepType.createUser.getType()});
        context.startActivity(intent);
    }

    public static void startCreateUserProcess(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.putExtra(StepperActivity.EXTRA_STEP,new int[]{StepType.createUser.getType()});
        context.startActivity(intent);
    }

    public static class EmailValidator implements Predicate<String> {
        @Override
        public boolean apply(String s) {
            return (s != null) && Patterns.EMAIL_ADDRESS.matcher(s).matches();
        }
    }

    public static class NonEmptyStringValidator implements Predicate<String> {
        @Override
        public boolean apply(String s) {
            return (s != null) && (s.replace(" ", "").length() > 0);
        }
    }

    public static class PassValidator implements Predicate<String> {
        @Override
        public boolean apply(String s) {
            return (s.length() >= 5);
        }
    }

    public static class PhoneValidator implements Predicate<String> {
        //public static final String PHONE_SIGN = "+";
        //private static final int MIN_PHONE_LENGTH = 8;
        @Override
        public boolean apply(String s) {
            return Patterns.PHONE.matcher(s).matches();
        }
    }
}
