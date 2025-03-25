package tp1.dam.powerhome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView affiche;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        affiche = findViewById(R.id.toosla);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String nom = bundle.getString("email");
        String prenom = bundle.getString("prenom");

        affiche.setText("Hello" + nom + "your passwpord" +prenom+"!");

    }
}
