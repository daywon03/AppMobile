package tp1.dam.powerhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
    }

    public void goToCreateAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void goToSplash(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    public void goToMain(View view) {
     Intent intent = new Intent(this, MainActivity.class);
     Bundle bundle = new Bundle();
     bundle.putString("email", "damon");
     bundle.putString("prenom", "sterrr");
     intent.putExtras(bundle);
     startActivity(intent);
    }
}
