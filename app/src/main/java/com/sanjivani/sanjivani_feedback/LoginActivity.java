package com.sanjivani.sanjivani_feedback;

import static com.sanjivani.sanjivani_feedback.helper.KeyAdapter.API;
import static com.sanjivani.sanjivani_feedback.helper.Utils.openCustomTab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sanjivani.sanjivani_feedback.helper.KeyAdapter;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    MaterialCardView cv_SigninWithGoogle;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    LinearLayout LL_ProgressBar;
    TextView tv_privacyPolicy, tv_termsOfUse;

    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cv_SigninWithGoogle = findViewById(R.id.cv_SigninWithGoogle);
        LL_ProgressBar = findViewById(R.id.ll_ProgressBar);
        tv_privacyPolicy = findViewById(R.id.tv_privacyPolicy);
        tv_termsOfUse = findViewById(R.id.tv_termsOfUse);

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        cv_SigninWithGoogle.setOnClickListener(view -> {
            signIn();
            mGoogleSignInClient.signOut();
        });

        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {

        }

        tv_privacyPolicy.setOnClickListener(view -> {
            String uriString = "https://sanjivanikbpcm.com/diplomamate/privacy-policy.html";

            openCustomTab(this, customIntent.build(), Uri.parse(uriString));
        });
        tv_termsOfUse.setOnClickListener(view -> {
            String uriString = "https://sanjivanikbpcm.com/diplomamate/terms-and-condition.html";

            openCustomTab(this, customIntent.build(), Uri.parse(uriString));
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken(), account.getEmail());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                LL_ProgressBar.setVisibility(View.GONE);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken, String emailId) {
        LL_ProgressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    AddToBackend();
                } else {
                    // If sign in fails, display a message to the user.
                    LL_ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AddToBackend() {

        StringRequest request = new StringRequest(Request.Method.POST, API+"register.php", response -> {

            Toast.makeText(this, "" + response, Toast.LENGTH_SHORT).show();
            LL_ProgressBar.setVisibility(View.GONE);
            // Sign in success, update UI with the signed-in user's information
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            mAuth.getCurrentUser();

        }, error -> {
            LL_ProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> map = new HashMap<>();
                map.put("key", KeyAdapter.KEY);
                map.put("name", mAuth.getCurrentUser().getDisplayName());
                map.put("emailid", mAuth.getCurrentUser().getEmail());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

}