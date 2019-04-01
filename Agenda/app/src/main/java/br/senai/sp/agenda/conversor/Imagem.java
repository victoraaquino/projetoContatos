package br.senai.sp.agenda.conversor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Imagem {

    public static Bitmap arrayToBitmap (byte[] byteArray){
        Bitmap imagemBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        return imagemBitmap;
    }

    public static byte[] bitmapToArray (Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

}
