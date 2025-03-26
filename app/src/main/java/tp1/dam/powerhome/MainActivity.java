package tp1.dam.powerhome;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView userInfoTextView;
    private static final String PROFILE_URL = "http://10.0.2.2:8888/powerhome_server/getProfile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfoTextView = findViewById(R.id.userInfoTextView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String token = extras.getString("token");
            Log.d("TOKEN", "Token reçu : " + token);
            getUserProfile(token);
        }
    }

    private void getUserProfile(String token) {
        Ion.with(this)
                .load(PROFILE_URL + "?token=" + token)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, "Erreur réseau : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d("PROFILE_RESPONSE", "Réponse brute : " + result);

                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            if (jsonResponse.has("error")) {
                                Toast.makeText(MainActivity.this, "Erreur : " + jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                            } else {
                                String userInfo = "Nom : " + jsonResponse.getString("firstname") + " " + jsonResponse.getString("lastname") +
                                        "\nEmail : " + jsonResponse.getString("email") +
                                        "\nTéléphone : " + jsonResponse.optString("phone", "Non renseigné") +
                                        "\nAdresse : " + jsonResponse.optString("address", "Non renseignée");
                                userInfoTextView.setText(userInfo);
                            }
                        } catch (JSONException jsonException) {
                            Toast.makeText(MainActivity.this, "Erreur JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
