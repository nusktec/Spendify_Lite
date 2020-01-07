package com.rscbyte.spendifylite.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.GoogleMap;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.activities.Dashboard;
import com.rscbyte.spendifylite.databinding.DialogInfoBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class Tools {
    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static void setSystemBarColorDialog(Context act, Dialog dialog, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void setSystemBarLightDialog(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = dialog.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void clearSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = act.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(act, R.color.colorPrimaryDark));
        }
    }

    /**
     * Making notification bar transparent
     */
    public static void setSystemBarTransparent(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //set global header
    public static void setHeaderColor(Activity activity) {
        Tools.setSystemBarColor(activity, R.color.light_white);
        Tools.setSystemBarLight(activity);
    }

    //get passed month
    public static String getVariesTimeStamp(int difference) {
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));
        now.add(Calendar.MONTH, difference);
        String str_date = 1 + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR) + " " + 00 + ":" + 00 + ":" + 00;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = null;
        try {
            date = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return date.getTime() + "";
    }

    //compare date for instance equality
    public static int timepstamp2Month(Long tmpstmp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(tmpstmp);
        return cal.get(Calendar.MONTH) + 1;
    }

    //get time stamp
    public static String getDateTimeStamp() {
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));
        String str_date = getDay() + "-" + getMonth() + "-" + getYear() + " " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = null;
        try {
            date = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return date.getTime() + "";
    }

    //Get single of date and
    public static int getDay() {
        java.util.Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        return day;
    }

    public static int getMonth() {
        java.util.Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month + 1;
    }

    public static int getYear() {
        java.util.Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static String getFormattedDateSimple() {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return newFormat.format(new Date());
    }

    public static String getFormattedDateEvent(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("EEE, MMM dd yyyy");
        return newFormat.format(new Date(dateTime));
    }

    public static String getFormattedTimeEvent(Long time) {
        SimpleDateFormat newFormat = new SimpleDateFormat("h:mm a");
        return newFormat.format(new Date(time));
    }

    public static String getEmailFromName(String name) {
        if (name != null && !name.equals("")) {
            String email = name.replaceAll(" ", ".").toLowerCase().concat("@mail.com");
            return email;
        }
        return name;
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static GoogleMap configActivityMaps(GoogleMap googleMap) {
        // set map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(true);
        // Enable / Disable Rotate gesture
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        // Enable / Disable zooming functionality
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        return googleMap;
    }

    public static void copyToClipboard(Context context, String data) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clipboard", data);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public static String getFromClipBoard(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            if (clipboard != null) {
                return clipboard.getText().toString();
            }
        } catch (NullPointerException np) {
        }
        return "";
    }

    public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
        nested.post(new Runnable() {
            @Override
            public void run() {
                nested.scrollTo(500, targetView.getBottom());
            }
        });
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    public static boolean toggleArrow(boolean show, View view) {
        return toggleArrow(show, view, true);
    }

    public static boolean toggleArrow(boolean show, View view, boolean delay) {
        if (show) {
            view.animate().setDuration(delay ? 200 : 0).rotation(180);
            return true;
        } else {
            view.animate().setDuration(delay ? 200 : 0).rotation(0);
            return false;
        }
    }

    public static void changeNavigateionIconColor(Toolbar toolbar, @ColorInt int color) {
        Drawable drawable = toolbar.getNavigationIcon();
        drawable.mutate();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public static void changeMenuIconColor(Menu menu, @ColorInt int color) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable == null) continue;
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void changeOverflowMenuIconColor(Toolbar toolbar, @ColorInt int color) {
        try {
            Drawable drawable = toolbar.getOverflowIcon();
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String toCamelCase(String input) {
        input = input.toLowerCase();
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static String insertPeriodically(String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            builder.append(prefix);
            prefix = insert;
            builder.append(text, index, Math.min(index + period, text.length()));
            index += period;
        }
        return builder.toString();
    }


    public static void rateAction(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static String getMarketLink(Activity activity) {
        return "http://play.google.com/store/apps/details?id=" + activity.getPackageName();
    }

    /**
     * Break long sentence and capitalize each
     *
     * @param words
     * @return
     * @throws IndexOutOfBoundsException
     */
    public static String capsWords(String words) throws IndexOutOfBoundsException {
        String[] wordsSplit = words.split(" ");
        StringBuilder rebuildWords = new StringBuilder();
        for (String wd : wordsSplit) {
            rebuildWords.append(String.valueOf(wd.substring(0, 1).toUpperCase() + wd.substring(1) + " "));
        }
        return rebuildWords.toString();
    }

    /**
     * Campitalize first latter
     *
     * @param str string
     * @return
     * @throws IndexOutOfBoundsException
     */
    public static String capsfLetter(String str) throws IndexOutOfBoundsException {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Generate random number with custom length
     *
     * @param length length of string
     * @return
     */
    public static String getRandomStr(int length) {
        int lenght = length;
        if (lenght > 36) {
            lenght = 36;
        }
        String STR_DATA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int MAX_INT = STR_DATA.length() - 1;
        int MIN_INT = 1;
        StringBuilder STR_HOLDER = new StringBuilder();
        for (int i = 0; i < length; i++) {
            STR_HOLDER.append(STR_DATA.charAt(new Random().nextInt(MAX_INT) + MIN_INT));
        }
        return STR_HOLDER.toString();
    }

    public static Calendar timeStamp(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c;
    }

    public static String timeStampStr(Long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
        return formatter.format(time);
    }

    @SuppressLint("DefaultLocale")
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static int getMonthAscNumeric(String sdate) {
        if (sdate.contains("January"))
            return 1;
        if (sdate.contains("February"))
            return 2;
        if (sdate.contains("March"))
            return 3;
        if (sdate.contains("April"))
            return 4;
        if (sdate.contains("May"))
            return 5;
        if (sdate.contains("June"))
            return 6;
        if (sdate.contains("July"))
            return 7;
        if (sdate.contains("August"))
            return 8;
        if (sdate.contains("September"))
            return 9;
        if (sdate.contains("October"))
            return 10;
        if (sdate.contains("November"))
            return 11;
        if (sdate.contains("December"))
            return 12;
        return 1;
    }

    public static String getMonthAscAlpha(int sdate) {
        if (sdate == 1) {
            return "Jan";
        }
        if (sdate == 2) {
            return "Feb";
        }
        if (sdate == 3) {
            return "Mar";
        }
        if (sdate == 4) {
            return "Apr";
        }
        if (sdate == 5) {
            return "May";
        }
        if (sdate == 6) {
            return "Jun";
        }
        if (sdate == 7) {
            return "Jul";
        }
        if (sdate == 8) {
            return "Aug";
        }
        if (sdate == 9) {
            return "Sep";
        }
        if (sdate == 10) {
            return "Oct";
        }
        if (sdate == 11) {
            return "Nov";
        }
        if (sdate == 12) {
            return "Dec";
        }
        return "Jan";
    }

    /**
     * Format current
     */
    public static String doCuurency(double amount) {
        return amount > 0 ? String.format("%,.01f", (double) amount) : "0";
    }

    public static String doFloat(double amount) {
        return String.format("%.01f", (double) amount);
    }

    public static boolean stringContainsNumber(String s) {
        if (s != null || !s.equals("")) {
            return Pattern.compile("[0-9]").matcher(s).find();
        }
        return false;
    }

    /**
     * Share text to external
     *
     * @param activity
     * @param subject
     * @param body
     */
    public static void shareText(Activity activity, String subject, String body) {
        Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
        txtIntent.setType("text/plain");
        txtIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        activity.startActivity(Intent.createChooser(txtIntent, "Share"));
    }

    /**
     * Support gradient header
     *
     * @param activity Do not supply activity is null, already instantiated activity will be fine
     */
    public static void supportGradientHeader(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = activity.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    public static void supportGradientHeader(Activity activity, Boolean aBoolean) {
        Window w = activity.getWindow();
        w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (aBoolean) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                w.setNavigationBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
            }
        }
    }

    public interface aniTypeSeq {
        void precedesChar(String string);
    }

    //Enable strict mode
    public static void enabledStrickMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    //public please wait
    public static void pleaseWait(int timeMS, final OnWait onWait) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onWait.onComplete();
            }
        }, timeMS);
    }

    public interface OnWait {
        void onComplete();
    }

    //Bitmap conversions
    public static String encodeTobase64(Bitmap image, EncodedListener encodedListener) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        encodedListener.converted(imageEncoded);
        return imageEncoded;
    }

    public interface EncodedListener {
        void converted(String base64);
    }

    //Bitmap conversions
    public static String encodeTobase64(String image, ImageListener listener) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(image);

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        lastVal = encodedFile;
        listener.onFinished(true, lastVal);
        return image;
    }

    public interface ImageListener {
        void onFinished(boolean status, String sbitmap);
    }

    public interface BitMapListener {
        void onBitMap(Bitmap img);
    }

    //Bitmap constructor
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        return bitmap;
    }

    public static Bitmap decodeBase64(String input, BitMapListener listener) {
        byte[] decodedByte = Base64.decode(input, 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        listener.onBitMap(bitmap);
        return bitmap;
    }

    //Circle image
    public static Bitmap getCircleImage(Bitmap bitmap) {
        try {
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();
            final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            final Path path = new Path();
            path.addCircle(
                    (float) (width / 2)
                    , (float) (height / 2)
                    , (float) Math.min(width, (height / 2))
                    , Path.Direction.CCW);

            final Canvas canvas = new Canvas(outputBitmap);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap, 0, 0, null);
            return outputBitmap;
        } catch (NullPointerException np) {
            //keep my secrete
        }
        return null;
    }

    //Notification Service
    public static int Notification(Context context, String title, String small_title, String message, int id, Class aClass, String meta_data) {
        Bitmap largeIcon = Tools.getCircleImage(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            largeIcon = Tools.getCircleImage(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Logic for platforms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String noti_id = String.valueOf(id);
            final CharSequence noti_name = "Spenify Alert";
            final int noti_importance = NotificationManager.IMPORTANCE_HIGH;

            //Apply it to a channel
            NotificationChannel notificationChannel = new NotificationChannel(noti_id, noti_name, noti_importance);
            notificationChannel.setDescription("It's your money");
            notificationChannel.setShowBadge(true);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        PendingIntent pendingIntent = null;
        if (aClass != null) {
            Intent intent = new Intent(context, aClass).putExtra("data", meta_data);
            // Creating a pending intent and wrapping our intent
            pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            Intent intent = new Intent(context, Dashboard.class);
            // Creating a pending intent and wrapping our intent
            pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        //Now General Notification setup
        Notification.Builder notification = new Notification.Builder(context);
        notification.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setSubText(small_title)
                .setStyle(new Notification.BigTextStyle().bigText(message))
                .setContentText(message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(String.valueOf(id));
        }
        notification.setContentIntent(pendingIntent);
        assert notificationManager != null;
        notificationManager.notify(id, notification.build());

        return id;
    }

    /**
     * @param act
     * @param msg
     */
    public static void showToast(Activity act, String msg) {
        View layout = act.getLayoutInflater().inflate(R.layout.util_custom_tool, (ViewGroup) act.findViewById(R.id.custom_toast_layout_id));
        TextView text = layout.findViewById(R.id.text);
        text.setText(msg);
        Toast toast = new Toast(act.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static Dialog warningDialog(Activity act, String msg, Drawable iconSrc, boolean touchCancel, View.OnClickListener callBack) {
        final Dialog dialog = new Dialog(act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(touchCancel);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.findViewById(R.id.bt_close).setOnClickListener(callBack);

        dialog.show();
        dialog.getWindow().setAttributes(lp);

        return dialog;
    }


    public static void showPickerLight(final Activity activity, final getDateItemsClick ItemsClick) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MONTH, monthOfYear + 1);
                        long date_ship_millis = calendar.getTimeInMillis();
                        //((TextView) findViewById(R.id.result)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                        //int m = monthOfYear + 1;
                        ItemsClick.getString(Tools.getFormattedDateSimple());
                        ItemsClick.getDate(year, monthOfYear + 1, dayOfMonth);
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DATE)
        );

        //set dark light
        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ItemsClick.onCancel();
            }
        });
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(activity.getResources().getColor(R.color.app_color_2));
        datePicker.setOkText("Search");
        //datePicker.setMinDate(cur_calender);
        datePicker.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    //message dialog
    public static void msgDialog(Context ctx, String title, String msg, int icon, int color) {
        final Dialog dialog = new Dialog(ctx);
        DialogInfoBinding infoBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.dialog_info, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(infoBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        infoBinding.bgLayout.setBackgroundColor(ctx.getResources().getColor(color));
        infoBinding.title.setText(title);
        infoBinding.content.setText(msg);
        infoBinding.icon.setImageDrawable(ctx.getResources().getDrawable(icon));
        infoBinding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //display info
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    //service runner
    public static boolean isMyServiceRunning(Context ctx, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Interface listener
     */
    public interface getDateItemsClick {
        void getDate(int y, int m, int d);

        void getString(String dateString);

        void onCancel();
    }

}
