package com.sanjivani.sanjivani_feedback;

import static android.content.Context.MODE_PRIVATE;

import static com.sanjivani.sanjivani_feedback.helper.KeyAdapter.API;
import static com.sanjivani.sanjivani_feedback.helper.Utils.openCustomTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseAuth mAuth;
    Context context;
    LinearLayout llShare;
    Button btnLogout;
    ProgressDialog progressDialog;

    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
    MaterialCardView cvPrivacyPolicy, cvTermsCondition, cvAboutUs, cvGiveUsFeedback, cvMoreApp, cvRateUs;

    TextView tvUserName, tvUserEmailId;
    CircleImageView userImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();

        context = getContext();

        customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.secondary_color));

        cvAboutUs = view.findViewById(R.id.cvAboutUs);
        cvGiveUsFeedback = view.findViewById(R.id.cvGiveUsFeedback);
        cvMoreApp = view.findViewById(R.id.cvMoreApp);
        cvRateUs = view.findViewById(R.id.cvRateUs);
        llShare = view.findViewById(R.id.llShare);
        btnLogout = view.findViewById(R.id.btnLogout);

        cvPrivacyPolicy = view.findViewById(R.id.cvPrivacyPolicy);
        cvTermsCondition = view.findViewById(R.id.cvTermsCondition);

        userImage = view.findViewById(R.id.userImage);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmailId = view.findViewById(R.id.tvUserEmailId);

        tvUserName.setText(mAuth.getCurrentUser().getDisplayName());
        tvUserEmailId.setText(mAuth.getCurrentUser().getEmail());
        Glide.with(getContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(userImage);

        OnClick();
        callSupport();

        return view;
    }


    private void OnClick() {

        cvPrivacyPolicy.setOnClickListener(view -> {
            String uriString = "https://sanjivanikbpcm.com/sanjivani_feedback//privacy-policy.html";

            openCustomTab(context, customIntent.build(), Uri.parse(uriString));
        });

        cvTermsCondition.setOnClickListener(view -> {
            String uriString = "https://sanjivanikbpcm.com/sanjivani_feedback//terms-and-condition.html";

            openCustomTab(context, customIntent.build(), Uri.parse(uriString));
        });

        cvAboutUs.setOnClickListener(view -> {
            FragmentActivity.fragment = "AboutUs";
            startActivity(new Intent(context, FragmentActivity.class));
        });

        cvGiveUsFeedback.setOnClickListener(view -> {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@dsc.sanjivanikbpcm.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Sanjivani Feedback");
            intent.putExtra(Intent.EXTRA_TEXT, "Hello team, \n\n I am having using this Sanjivani Feedback app, Mentioned your message here..");
            startActivity(intent);
            try {
                startActivity(Intent.createChooser(intent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

        cvRateUs.setOnClickListener(view -> {
            Uri url2 = Uri.parse("https://play.google.com/store/apps/details?id=" + getContext().getPackageName()); // missing 'http://' will cause crashed
            Intent intent2 = new Intent(Intent.ACTION_VIEW, url2);
            startActivity(intent2);
        });

        llShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey,\nDownload this Sanjivani Feedback app to give feedback for Sanjivani Group of Institutes. To download the app below is the link\nhttps://play.google.com/store/apps/details?id=" + getContext().getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        btnLogout.setOnClickListener(view -> {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progressbar_loading);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            Handler handler = new Handler();
            Runnable runnable = () -> {
                progressDialog.dismiss();
                mAuth.signOut();
                SharedPreferences sh = getContext().getSharedPreferences("BasicDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                getActivity().finishAffinity();
            };
            handler.postDelayed(runnable, 2000);

        });
    }

    private void callSupport() {
        StringRequest request = new StringRequest(Request.Method.GET, API + "support.json", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String developerPageUrl = jsonObject.getString("developerPage");

                cvMoreApp.setOnClickListener(view -> {
                    Uri uri = Uri.parse(developerPageUrl); // missing 'http://' will cause crashed
                    Intent intents = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intents);
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}