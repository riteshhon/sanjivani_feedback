package com.sanjivani.sanjivani_feedback;

import static com.sanjivani.sanjivani_feedback.helper.KeyAdapter.API;
import static com.sanjivani.sanjivani_feedback.helper.KeyAdapter.KEY;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.sanjivani.sanjivani_feedback.adapter.FeedbackAdapter;
import com.sanjivani.sanjivani_feedback.model.FeedbackModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    Feedback
    FeedbackAdapter feedbackAdapter;
    List<FeedbackModel> feedbackModels = new ArrayList<>();
    RecyclerView recyclerView;
    CardView cvProgressBar;

    LinearLayout llLinkedIn, llInstagram, llWebsite;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cvProgressBar = view.findViewById(R.id.cvProgressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        llLinkedIn = view.findViewById(R.id.llLinkedIn);
        llInstagram = view.findViewById(R.id.llInstagram);
        llWebsite = view.findViewById(R.id.llWebsite);

        llLinkedIn.setOnClickListener(view1 -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/sanjivani-developer-student-club")));
        });

        llInstagram.setOnClickListener(view1 -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sanjivani.dsc/")));
        });

        llWebsite.setOnClickListener(view1 -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://sanjivanikbpcm.com")));
        });

        mAuth = FirebaseAuth.getInstance();

        callFeedback();

        return view;
    }


    private void callFeedback() {
        cvProgressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, API+"feedback.php", response -> {
            feedbackModels.clear();
            for (int i = 0; i < response.length(); i++) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    FeedbackModel feedbackModel = new FeedbackModel();
                    feedbackModel.setFeedbackImage(jsonObject.getString("feedbackImage"));
                    feedbackModel.setFeedbackTitle(jsonObject.getString("feedbackName"));
                    feedbackModel.setFeedbackDescribe(jsonObject.getString("feedbackDescribe"));
                    feedbackModel.setFeedbackUrl(jsonObject.getString("feedbackFormUrl"));
                    feedbackModels.add(feedbackModel);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cvProgressBar.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            feedbackAdapter = new FeedbackAdapter(getContext(), feedbackModels);
            recyclerView.setAdapter(feedbackAdapter);

        }, error -> {

        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("key", KEY);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

}