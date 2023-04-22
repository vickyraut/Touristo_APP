package com.polytechnic.touristo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.polytechnic.touristo_app.Constants.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    TextView homg_tv_name;
    TextView homg_tv_email;
    TextView homg_tv_update_Address;
    TextView homg_tv_update_noti;
    TextView homg_tv_update_payment;
    TextView home_tv_large_Name, home_tv_large_Email;
    CircleImageView img_profile;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String login_email;

    String id, Name, Email;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        login_email = preferences.getString("email", "");

        homg_tv_name = view.findViewById(R.id.homg_et_name);
        homg_tv_email = view.findViewById(R.id.homg_et_email);
        homg_tv_update_payment = view.findViewById(R.id.homg_et_update_payment);
        homg_tv_update_Address = view.findViewById(R.id.homg_et_update_Address);
        homg_tv_update_noti = view.findViewById(R.id.homg_et_update_noti);
        home_tv_large_Name = view.findViewById(R.id.textView4);
        home_tv_large_Email = view.findViewById(R.id.textView6);
        img_profile = view.findViewById(R.id.profimage);


        String address = preferences.getString("address", "Address");
        homg_tv_update_Address.setText(address);

        ConstraintLayout Cl_your_details = view.findViewById(R.id.Cl_your_details);
        ConstraintLayout Cl_manage_add = view.findViewById(R.id.Cl_manage_add);
        ConstraintLayout Cl_notification = view.findViewById(R.id.Cl_notification);
        ConstraintLayout cl_paymentDetails = view.findViewById(R.id.cl_paymentDetails);

        TextView tv_edit = view.findViewById(R.id.tv_edit);
        ImageView upload_img = view.findViewById(R.id.imageView13);
        CardView cv_your_details = view.findViewById(R.id.Cv_yourdetails);

        getMydetails();

        cv_your_details.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_tv_name.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_tv_name.setVisibility(v);
                homg_tv_email.setVisibility(v);
            }
        });

        Cl_manage_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_tv_update_Address.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_tv_update_Address.setVisibility(v);
//                homg_tv_email.setVisibility(v);
//                homg_tv_Pass.setVisibility(v);
            }
        });

        Cl_notification.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_tv_update_noti.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_tv_update_noti.setVisibility(v);
//                homg_tv_email.setVisibility(v);
//                homg_tv_Pass.setVisibility(v);
            }
        });

        cl_paymentDetails.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int v = (homg_tv_update_payment.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(Cl_your_details, new AutoTransition());
                homg_tv_update_payment.setVisibility(v);
//                homg_tv_email.setVisibility(v);
//                homg_tv_Pass.setVisibility(v);
            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getContext(), Edit_ProfileActivity.class);
                startActivity(i1);
            }
        });

        return view;
    }

    private void getMydetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email", login_email);

        client.post(Urls.urlgetMyDetails, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getMyDetails");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getString("id");
                        String firstname = jsonObject.getString("firstname");
                        String lastname = jsonObject.getString("lastname");
                        String image_name = jsonObject.getString("myimage");
                        Email = jsonObject.getString("email");

                        Name = firstname + " " + lastname;

                        editor.putString("id", id).commit();

                        homg_tv_name.setText(Name);
                        homg_tv_email.setText(Email);
                        home_tv_large_Name.setText(Name);
                        home_tv_large_Email.setText(Email);

                        Picasso.get()
                                .load(Urls.UserImageAddress + image_name)
                                .error(R.drawable.default_profile)
                                .into(img_profile);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}