package tp1.dam.powerhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private ProgressDialog pDialog;
    private static final String LOGIN_URL = "http://10.0.2.2:8888/powerhome_server/login.php"; // IP pour l'émulateur
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Connexion en cours...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        // Création de l'objet JSON avec com.google.gson.JsonObject
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);

        Ion.with(this)
                .load("POST", LOGIN_URL)
                .setJsonObjectBody(json) // Envoi du JSON contenant email et password
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        pDialog.dismiss();
                        if (e != null) {
                            Toast.makeText(LoginActivity.this, "Erreur réseau : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d("LOGIN_RESPONSE", "Réponse brute : " + result);

                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            if (jsonResponse.has("token")) {
                                String token = jsonResponse.getString("token");
                                Toast.makeText(LoginActivity.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
                                goToMain(email, token);
                            } else {
                                Toast.makeText(LoginActivity.this, "Échec de connexion : " + jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonException) {
                            Toast.makeText(LoginActivity.this, "Erreur JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goToCreateAccount(View view) {
        Log.d("LOGIN", "goToCreateAccount appelé");
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void goToSplash(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    public void goToMain(String email, String token) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("token", token);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}