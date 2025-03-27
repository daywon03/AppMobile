package tp1.dam.powerhome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailForgot;
    private static final String FORGOT_URL = "http://10.0.2.2:8888/powerhome_server/forgotPassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailForgot = findViewById(R.id.emailForgot);

        findViewById(R.id.btnForgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailForgot.getText().toString().trim();
                if (email.isEmpty()) {
                    emailForgot.setError("Veuillez entrer votre email");
                    return;
                }
                forgotPassword(email);
            }
        });
    }

    private void forgotPassword(String email) {
        JsonObject json = new JsonObject();
        json.addProperty("email", email);

        Ion.with(this)
                .load("POST", FORGOT_URL)
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(ForgotPasswordActivity.this, "Erreur réseau : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("FORGOT_RESPONSE", "Result: " + result);
                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.has("message")) {
                                String tempPassword = response.getString("tempPassword");
                                Toast.makeText(ForgotPasswordActivity.this, "Mot de passe temporaire généré", Toast.LENGTH_SHORT).show();
                                // Lancer ResetPasswordActivity en pré-remplissant le champ temporaire
                                Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                                intent.putExtra("tempPassword", tempPassword);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Erreur : " + response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(ForgotPasswordActivity.this, "Erreur lors du traitement de la réponse", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
