package com.example.cycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class QR extends AppCompatActivity {

    String uri;
    ImageView qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        qr = findViewById(R.id.qr);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            uri = bundle.getString("uri");
        }

//        try {
//            Bitmap bitmap = encodeAsBitmap(uri);
//            qr.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
        Bitmap res = generateQRCode(uri);
        qr.setImageBitmap(res);

    }


    Bitmap encodeAsBitmap(String str) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 1200, 1200);
        Log.d("QR", "encodeAsBitmap: " + str);
        Log.d("QR", "encodeAsBitmap: " + bitMatrix.getHeight());
        Log.d("QR", "encodeAsBitmap: " + bitMatrix.getWidth());

        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pixels[y * w + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }

    public Bitmap generateQRCode(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            HashMap<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 1200, 1200, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap generateQRCode2(String text) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 0, 0, hints);

        StringBuilder sbPath = new StringBuilder();
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BitArray row = new BitArray(width);
        for(int y = 0; y < height; ++y) {
            row = bitMatrix.getRow(y, row);
            for(int x = 0; x < width; ++x) {
                if (row.get(x)) {
                    sbPath.append(" M"+x+","+y+"h1v1h-1z");
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" viewBox=\"0 0 ").append(width).append(" ").append(height).append("\" stroke=\"none\">\n");
        sb.append("<style type=\"text/css\">\n");
        sb.append(".black {fill:#000000;}\n");
        sb.append("</style>\n");
        sb.append("<path class=\"black\"  d=\"").append(sbPath.toString()).append("\"/>\n");
        sb.append("</svg>\n");
    }
}