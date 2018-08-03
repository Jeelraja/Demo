package com.builderfly.parsing_app;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CommonUtils {

    public final static Pattern INVALID_EMAIL_PATTERN = Pattern.compile("^[0-9-]+[_0-9-]*(\\.[_0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    public final static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9-]+[_A-Za-z0-9-]*(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    public final static Pattern PASSWORD_VALIDATION = Pattern.compile("[A-Za-z0-9\\@\\#\\_\\'\\^\\*\\=\\:\\-\\+\\`]+$");
    public final static Pattern FIRST_LAST_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9]+[A-Za-z-\\.\\-\\_\\']*$");
    public static final int REQUEST_CAMERA = 1234;
    public static final int SELECT_FILE = 4321;
    public static Uri imageURI;
    public static String mCurrentPhotoPath;
    private static File mFinalFile;

    /**
     * changes status bar color
     *
     * @param statusColor value of color want to set to status bar
     */
    public static void setStatusBarColor(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusColor);
        }
    }

    /**
     * checks if the string is blank.
     *
     * @param string the string to be checked.
     * @return true if string is blank else false
     */
    public static boolean isNullString(String string) {
        try {
            return string == null || string.trim().equalsIgnoreCase("null") || string.trim().length() < 0
                    || string.trim().equals("");
        } catch (Exception e) {
            return true;
        }
    }


    /**
     * loads image in the imageview passed in parameter
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
                .into(imageView);
    }

    /**
     * to display toast on screen
     *
     * @Date :  22/9/17
     */
    public static Void displayToast(Context context, String strToast) {
        Toast.makeText(context, strToast, Toast.LENGTH_SHORT).show();
        return null;
    }

    /**
     * checks if the email is correct or not
     *
     * @return true if email is correct else false
     */
    public static boolean checkEmail(String email) {
        return !INVALID_EMAIL_PATTERN.matcher(email).matches() && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * checks if the password is correct or not
     *
     * @return true if password matches the pattern
     */
    public static boolean checkPassword(String password) {
        return PASSWORD_VALIDATION.matcher(password).matches();
    }

    /**
     * to check if there is internet connection or not
     */
    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                displayToast(context, "No Internet");
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * checks if the name is correct or not
     *
     * @return true if name matches the pattern
     */
    public static boolean checkFirstLastName(String name) {
        return FIRST_LAST_NAME_PATTERN.matcher(name).matches();
    }

    /**
     * loads image in the imageview passed in parameter
     */
    /*public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.support_bg))
                .into(imageView);
    }*/

    /**
     * This method is used to Format date in desired format.
     *
     * @return formatted date in string form
     */
    public static String dateFormatter(String dateFromJSON, String expectedFormat, String oldFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date date;
        String convertedDate = null;
        try {
            date = dateFormat.parse(dateFromJSON);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
            convertedDate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    /**
     * to hide keyboard
     */
    public static void hideSoftKeyboard(Activity context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ignored) {
//            ignored.printStackTrace();
        }
    }

}