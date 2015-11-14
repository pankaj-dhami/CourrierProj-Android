package pankaj.com.courrierproj.AccountActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import pankaj.com.courrierproj.MainActivity;
import pankaj.com.courrierproj.Model.UserModel;
import pankaj.com.courrierproj.R;
import pankaj.com.courrierproj.Utility.ApplicationConstant;

public class Register extends Activity {
    View view;
    ImageView imgProfilePic;
    ToggleButton btnSavePic;
    Button btnCancelPic;
    UserModel thisUser;
    byte[] imageByteArray;

    EditText txtName;
    EditText txtEmail;
    EditText txtMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);

        btnSavePic = (ToggleButton) findViewById(R.id.btnSavePic);
        btnCancelPic = (Button) findViewById(R.id.btnCancelPic);

        txtName=(EditText)findViewById(R.id.txtName);
        txtEmail=(EditText)findViewById(R.id.txtEmail);
        txtMobile=(EditText)findViewById(R.id.txtMobile);


        if (ApplicationConstant.thisUser!=null)
        {
            UserModel thisUser=ApplicationConstant.thisUser;
            String  personPhotoUrl = thisUser.PictureUrl.substring(0,
                  thisUser.PictureUrl.length() - 2)
                    + 150;

            txtName.setText(thisUser.Name);
            txtEmail.setText(thisUser.Email);

            new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

        }
    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mIcon11.compress(Bitmap.CompressFormat.JPEG, 5, stream);
                imageByteArray = stream.toByteArray();

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
          //  bmp = BitmapFactory.decodeByteArray(thisUser.PicData, 0, thisUser.PicData.length);
            bmImage.setImageBitmap(result);
        }
    }
}
