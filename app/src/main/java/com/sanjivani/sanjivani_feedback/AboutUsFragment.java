package com.sanjivani.sanjivani_feedback;

import static com.sanjivani.sanjivani_feedback.helper.KeyAdapter.API;
import static com.sanjivani.sanjivani_feedback.helper.KeyAdapter.KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.sanjivani.sanjivani_feedback.adapter.AboutUsAdapter;
import com.sanjivani.sanjivani_feedback.model.AboutUsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutUsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseAuth mAuth;
    Context context;
    CardView cvProgressBar;

    //    AboutUs
    AboutUsAdapter aboutUsAdapter;
    List<AboutUsModel> aboutUsModels = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        context = getContext();

        recyclerView = view.findViewById(R.id.recyclerView);
        cvProgressBar = view.findViewById(R.id.cvProgressBar);

        mAuth = FirebaseAuth.getInstance();

        callDevelopmentTeam();


        return view;
    }

    private void callDevelopmentTeam() {
        cvProgressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, API+"aboutUs.php", response -> {
            aboutUsModels.clear();
            for (int i = 0; i < response.length(); i++) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    AboutUsModel aboutUsModel = new AboutUsModel();
                    aboutUsModel.setId(jsonObject.getString("id"));
                    aboutUsModel.setImageUrl(jsonObject.getString("imageUrl"));
                    aboutUsModel.setName(jsonObject.getString("name"));
                    aboutUsModel.setDescribeMember(jsonObject.getString("describeMember"));
                    aboutUsModel.setLinkedInUrl(jsonObject.getString("linkedInUrl"));
                    aboutUsModel.setTwitterUrl(jsonObject.getString("twitterUrl"));
                    aboutUsModel.setGithubUrl(jsonObject.getString("githubUrl"));
                    aboutUsModels.add(aboutUsModel);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cvProgressBar.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            aboutUsAdapter = new AboutUsAdapter(context, aboutUsModels);
            recyclerView.setAdapter(aboutUsAdapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }
}