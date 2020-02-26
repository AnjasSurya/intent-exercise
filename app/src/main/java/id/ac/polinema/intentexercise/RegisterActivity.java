package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.BitSet;

import id.ac.polinema.intentexercise.model.user;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";

    private TextInputEditText fullnameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText conpasswordInput;
    private TextInputEditText homepageInput;
    private TextInputEditText aboutInput;
    private ImageView pictImage;

    private Uri Uripict;
    private Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameInput =findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passwordInput = findViewById(R.id.text_password);
        conpasswordInput = findViewById(R.id.text_confirm_password);
        homepageInput = findViewById(R.id.text_homepage);
        aboutInput = findViewById(R.id.text_about);
        pictImage =findViewById(R.id.image_profile);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            return;
        }
        if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null){
                try {
                    Uripict = data.getData();
                    bit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uripict);
                    pictImage.setImageBitmap(bit);
                }catch (IOException e){
                    Toast.makeText(this, "Tidak bisa mengunggah gambar!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleSubmit(View view){
        String fullname = fullnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String conpassword = conpasswordInput.getText().toString();
        String homepage = homepageInput.getText().toString();
        String about = aboutInput.getText().toString();

        if(fullname.isEmpty()) {
            fullnameInput.setError("Isi Nama Dulu Guys");
        }else if(email.isEmpty()) {
            emailInput.setError("isi Email Dulu Guys");
        }else if(password.isEmpty()) {
            passwordInput.setError("Isi Password Dulu Guys");
        }else  if (! conpassword.equals(password)) {
            conpasswordInput.setError("Samakan Dong!");
        }else  if (homepage.isEmpty()) {
            homepageInput.setError("Isi Homepage Dulu Guys");
        }else if (about.isEmpty()) {
            aboutInput.setError("Isi About Dulu Guys");
        }else {

            user usr = new user(fullname, email, password, conpassword, homepage, about, Uripict);
            Intent intent = new Intent(this, ProfileActivity.class);

            intent.putExtra("user", usr);
            startActivity(intent);
        }
    }

    public void changeProfile(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
}
