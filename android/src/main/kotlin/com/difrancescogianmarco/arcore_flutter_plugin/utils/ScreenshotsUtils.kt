package com.difrancescogianmarco.arcore_flutter_plugin.utils

import java.nio.ByteBuffer
import java.io.File
import java.io.OutputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import android.content.pm.PackageManager;
import android.view.PixelCopy
import android.graphics.Canvas
import android.os.Handler
import android.os.Environment
import android.os.Build
import android.Manifest;
import android.graphics.Bitmap
import android.app.Activity
import android.util.Log
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream
import io.flutter.plugin.common.MethodChannel
import com.google.ar.sceneform.ArSceneView

class ScreenshotsUtils {

    companion object {

        fun getByteArrayFromImage(bitmap: Bitmap): ByteArray {

            var byteArray = ByteArrayOutputStream().toByteArray();

            try{
                val stream = ByteArrayOutputStream()

                // Compress bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream)

                byteArray = stream.toByteArray()

           }catch (e: Exception){
                e.printStackTrace()
            }

            return byteArray

        }

        fun onGetSnapshot(arSceneView: ArSceneView?, result: MethodChannel.Result){

            if(arSceneView == null){
                Log.i("Sreenshot", "Ar Scene View is NULL!");

                result.success(null);

                return;
            }


            try {

                val view = arSceneView!!;

                val bitmapImage: Bitmap = Bitmap.createBitmap(
                                view.getWidth(),
                                view.getHeight(),
                                Bitmap.Config.ARGB_8888
                        );
                Log.i("Sreenshot", "PixelCopy requesting now...");
                PixelCopy.request(view, bitmapImage, { copyResult -> 
                      if (copyResult == PixelCopy.SUCCESS) {
                        Log.i("Sreenshot", "PixelCopy request SUCESS. ${copyResult}");

                        var imageBytes: ByteArray = getByteArrayFromImage(bitmapImage);

                        Log.i("Sreenshot", "Saved on path: ${imageBytes}");
                        result.success(imageBytes);

                      }else{
                          Log.i("Sreenshot", "PixelCopy request failed. ${copyResult}");
                          result.success(null);
                      }

                    }, 
                    Handler());

            } catch (e: Exception){

                e.printStackTrace()
            }


        }
    }
}