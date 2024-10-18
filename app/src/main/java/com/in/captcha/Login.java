package com.in.captcha;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Login extends AppCompatActivity {
    private String generatedCaptcha;
    private ImageView captchaImageView;
    private EditText captchaInput;
    private TextView refreshCaptcha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        captchaImageView = findViewById(R.id.captcha_image);
        captchaInput = findViewById(R.id.captcha_input);
        refreshCaptcha = findViewById(R.id.refresh_captcha);

        // Generate the first captcha
        generatedCaptcha = generateCaptcha();
        updateCaptchaImage(generatedCaptcha);

        refreshCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatedCaptcha = generateCaptcha();
                updateCaptchaImage(generatedCaptcha);
            }
        });
    }

    // Method to generate a random captcha string
    private String generateCaptcha() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder captcha = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            captcha.append(characters.charAt(random.nextInt(characters.length())));
        }
        return captcha.toString();
    }

    // Method to generate and update captcha image in ImageView
    private void updateCaptchaImage(String captchaText) {
        Bitmap bitmap = Bitmap.createBitmap(200, 50, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        // Background
        canvas.drawColor(Color.WHITE);

        // Draw random lines for noise
        Random random = new Random();
        paint.setColor(Color.RED);
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(random.nextInt(200), random.nextInt(50),
                    random.nextInt(200), random.nextInt(50), paint);
        }

        // Draw text
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText(captchaText, 40, 40, paint);

        // Set the captcha bitmap to ImageView
        captchaImageView.setImageBitmap(bitmap);
    }

    // Validation (Call this when validating the form)
    private boolean isCaptchaValid() {
        String enteredCaptcha = captchaInput.getText().toString().trim();
        return generatedCaptcha.equals(enteredCaptcha);
    }
}
