package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import id.ac.polinema.intentexercise.model.user;

import static id.ac.polinema.intentexercise.RegisterActivity.EMAIL_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.FULLNAME_KEY;

public class ProfileActivity extends AppCompatActivity {

    private TextView fullnameText, emailText, passwordText, conpasswordText, homepageText, aboutText;
    private ImageView imageUri;
    private Bitmap bitmap;
    Uri Uripict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullnameText = findViewById(R.id.label_fullname);
        emailText = findViewById(R.id.label_email);
        homepageText = findViewById(R.id.label_homepage);
        aboutText = findViewById(R.id.label_about);
        imageUri = findViewById(R.id.image_profile);

Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            user usr = bundle.getParcelable("user");
            fullnameText.setText(usr.getFullname());
            emailText.setText(usr.getEmail());
            homepageText.setText(usr.getHomepage());
            aboutText.setText(usr.getAbout());
            Uripict = usr.getUriIm();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uripict);
            }catch (IOException e){
                e.printStackTrace();
            }

            imageUri.setImageBitmap(bitmap);
        }
    }

    public void GoToWebsite(View view) {
        Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + homepageText.getText().toString()));
        startActivity(implicit);
    }
}
