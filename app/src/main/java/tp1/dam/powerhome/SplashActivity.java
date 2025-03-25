package tp1.dam.powerhome;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupération de l'élément ImageView pour l'animation
        ImageView splashLogo = findViewById(R.id.splash_logo);
        startFadeInAnimation(splashLogo);

        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(splashLogo, "rotation", 0f, 360f);
        rotateAnimation.setDuration(3000); //
        rotateAnimation.start();

        // Délai avant de passer à l'écran suivant
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }

    // Méthode pour démarrer l'animation de fondu
    private void startFadeInAnimation(View view) {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1500); // Durée de l'animation en ms
        fadeIn.setFillAfter(true);
        view.startAnimation(fadeIn);
    }
}
