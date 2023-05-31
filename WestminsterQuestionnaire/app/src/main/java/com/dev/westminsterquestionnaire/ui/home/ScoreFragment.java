package com.dev.westminsterquestionnaire.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev.westminsterquestionnaire.R;
import com.dev.westminsterquestionnaire.databinding.FragmentScoreBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScoreFragment extends Fragment {

    FragmentScoreBinding binding;
    String subject = "";
    String score = "";
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference dbReference;

    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentScoreBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Going Good...");
        progressDialog.setMessage("It takes Just a few Seconds... ");
        progressDialog.setIcon(R.drawable.happy);
        progressDialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance(
                "https://westminster-questionnaire-default-rtdb.firebaseio.com").getReference("UsersData").child(auth.getCurrentUser().getUid());


        if (getArguments() != null){
            subject = getArguments().getString("subject");
            score = getArguments().getString("score");
            if (Objects.equals(subject, "Science")){
                binding.tvTotalScore.setText("Total Score : 15");
                binding.tvYourScore.setText("Your Score : "+score);
                int percentage = (int) Math.round((Double.parseDouble(score)/15)*100);
                binding.tvProgress.setText("Progress : "+percentage+"%");
                updateValue("scienceProgress", percentage+"%");
            }else if (Objects.equals(subject, "English")){
                binding.tvTotalScore.setText("Total Score : 10");
                binding.tvYourScore.setText("Your Score : "+score);
                int percentage = (int) Math.round((Double.parseDouble(score)/10)*100);
                binding.tvProgress.setText("Progress : "+percentage+"%");
                updateValue("englishProgress", percentage+"%");
            }else if (Objects.equals(subject, "Math")){
                binding.tvTotalScore.setText("Total Score : 15");
                binding.tvYourScore.setText("Your Score : "+score);
                int percentage = (int) Math.round((Double.parseDouble(score)/15)*100);
                binding.tvProgress.setText("Progress : "+percentage+"%");
                updateValue("mathProgress", percentage+"%");
            }
        }

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_home);
            }
        });

    }

    private void updateValue(String key, String value){
        progressDialog.show();
        Map<String, Object> update = new HashMap<String, Object>();
        update.put(key, value);
        dbReference.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });
    }


}