package com.example.adrian.androiddatabinding.utilerias;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.provider.Settings.Secure;
import android.net.wifi.WifiManager;

import 	java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Utilerias {


    public static String errorVolley(VolleyError error) {
        String message = "NO SE PUEDE CONECTAR A INTERNET... COMPRUEBE SU CONEXIÓN!";
        if (error instanceof NetworkError) {
            message = "¡ERROR DE RED! INTÉNTALO DE NUEVO MAS TARDE!!";
        } else if (error instanceof ServerError) {
            message = "NO SE PUDO ENCONTRAR EL SERVIDOR. INTÉNTALO DE NUEVO MAS TARDE!!";
        } else if (error instanceof AuthFailureError) {
            message = "¡ERROR DE AUTENTIFICACIÓN! EL USUARIO NECESITA CREDENCIALES!";
        } else if (error instanceof ParseError) {
            message = "¡ERROR DE SINTÁXIS! INTÉNTALO DE NUEVO MAS TARDE!!";
        } else if (error instanceof TimeoutError) {
            message = "¡EL TIEMPO DE CONEXIÓN EXPIRO! POR FAVOR REVISE SU CONEXIÓN A INTERNET.";
        }
        return message;
    }


    public static boolean isOnline(Context prContext) {
        try
        {
			/*ConnectivityManager cm =
					(ConnectivityManager) prContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			return netInfo != null && netInfo.isConnectedOrConnecting();*/
            return true;
        }
        catch (Exception e) {
            // TODO: handle exception
            Log.d("Error checar internet:",e.getMessage());
            return true;
        }
    }
    public static boolean isActiveBluetooth(Context prContext) {
        try
        {
            BluetoothAdapter oBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            return oBluetoothAdapter.isEnabled();

        }
        catch (Exception e) {
            // TODO: handle exception
            Log.d("Error checar bluetooth:",e.getMessage());
            return true;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateComplete()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(new Date());
    }

    //obtener el imei del divice
    public static String getIMEI(Context prContext){
        String myAndroidDeviceId;
        TelephonyManager oTelephonyManager = (TelephonyManager) prContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (oTelephonyManager.getDeviceId() != null){
            myAndroidDeviceId = oTelephonyManager.getDeviceId();
        }else{//el device no es un telefono
            myAndroidDeviceId = getMacAddr();

            if(myAndroidDeviceId.equals(""))
                myAndroidDeviceId = Secure.getString(prContext.getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

        }
        return myAndroidDeviceId;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String sd = Integer.toHexString(b & 0xFF);
                    res1.append( sd.length() == 1 ? "0" + sd + ":" : sd + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }

    //obtener la marca del divice
    public static String getMarca(){
        return Build.BRAND;
    }

    /** Returns the consumer friendly device name  Modelo divice*/
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    public static String getFabricante(){
        return Build.MANUFACTURER;
    }

    public static String getModelo(){
        return  Build.MODEL;
    }

    public static String getNumeroSerie(){
        /*if(Build.VERSION.SDK_INT == 26){
            return Build.getSerial();
        }else{*/
        return Build.SERIAL;
        //}
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

//	        String phrase = "";
        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
//	                phrase += Character.toUpperCase(c);
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
//	            phrase += c;
            phrase.append(c);
        }

        return phrase.toString();
    }
    //eliminar caracteres especiales a un string
    public static String formatearString(String prNombre){
        String nueva = prNombre.replaceAll("[^\\dA-Za-z]", "");

        nueva = nueva.replaceAll("[\\W]|_", "");

        return nueva.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

}

