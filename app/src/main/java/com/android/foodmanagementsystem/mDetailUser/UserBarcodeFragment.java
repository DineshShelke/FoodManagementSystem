package com.android.foodmanagementsystem.mDetailUser;


import android.content.ReceiverCallNotAllowedException;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mDetail.Global;
import com.android.foodmanagementsystem.mRecycler.Singleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.support.design.R.id.image;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserBarcodeFragment extends Fragment {

    String fetchBarcode = Global.url+"BarcodeDataFetchServlet";
    ImageView imageView;
    private SharedPreferences preferences;
    Button button;
    EditText editText;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    public UserBarcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_barcode, container, false);

        imageView = (ImageView)view.findViewById(R.id.imageView);

       /* preferences = getContext().getSharedPreferences("dataSend", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.commit();
        String barcode = preferences.getString("barcode","0");
        editor.commit();

        Bitmap bitmap = StringToBitMap(barcode);

        Log.d("BarcodeFragment",barcode);
*/
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setBarcode();
    }

    private void setBarcode() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, fetchBarcode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("BarcodeData", response);
                        try {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("Response");
                                for (int i = 0; i<jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String orderid = jsonObject1.getString("order_id");
                                    String barcodeid = jsonObject1.getString("barcode_id");
                                    String data = orderid+":"+barcodeid;
                                    bitmap = TextToImageEncode(data);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                        imageView.setImageBitmap(bitmap);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){};
        Singleton.getInstance(getActivity()).addToRequestque(stringRequest);
    }

    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    Bitmap TextToImageEncode(String data) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    data,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
