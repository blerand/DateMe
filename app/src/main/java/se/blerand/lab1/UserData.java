package se.blerand.lab1;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Blerand Bahtiri on 2016-11-18.
 */

public class UserData {

    private static String USER_DATABASE = MainActivity.STORAGE_NAME;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(USER_DATABASE, Context.MODE_PRIVATE);
    }

    public static String getString(Context context, String key) {
        return getPrefs(context).getString(key, "");
    }
    public static void setString(Context context,String key, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, input);
        editor.apply();
    }
    public static void deleteString(Context context, String key) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove(key).apply();
        //return getPrefs(context).getString(key, "");
    }

    public static int getID(Context context, String key) {
        return getPrefs(context).getInt(key,0);
    }

    public static void setID(Context context,String key, int input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(key, input);
        editor.apply();
    }

    public static Boolean getBoo(Context context, String key) {
        return getPrefs(context).getBoolean(key,false);
    }

    public static void setBoo(Context context,String key, Boolean input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(key, input);
        editor.apply();
    }

    public static void deleteInfo (Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.clear().apply();
    }
    public static boolean checkInfo (Context context, String key){
        return getPrefs(context).contains(key);
    }

}
