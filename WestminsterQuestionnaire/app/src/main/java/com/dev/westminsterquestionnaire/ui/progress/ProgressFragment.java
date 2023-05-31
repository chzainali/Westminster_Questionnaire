package com.dev.westminsterquestionnaire.ui.progress;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dev.westminsterquestionnaire.R;
import com.dev.westminsterquestionnaire.databinding.FragmentProgressBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProgressFragment extends Fragment {

    private FragmentProgressBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference dbReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(requireContext());
        auth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance(
                "https://westminster-questionnaire-default-rtdb.firebaseio.com").getReference("UsersData").child(auth.getCurrentUser().getUid());

        showData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showData() {
        //Show Progress Dialog
        progressDialog.setTitle("Going Good...");
        progressDialog.setMessage("It takes Just a few Seconds... ");
        progressDialog.setIcon(R.drawable.happy);
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Add Listener & Show Values
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String getScience = snapshot.child("scienceProgress").getValue(String.class);
                String getEnglish = snapshot.child("englishProgress").getValue(String.class);
                String getMath = snapshot.child("mathProgress").getValue(String.class);
                progressDialog.dismiss();

                if (Objects.equals(getScience,"")){
                    binding.tvScienceProgress.setText("Not Attempted Yet");
                }else{
                    binding.tvScienceProgress.setText(getScience);
                }

                if (Objects.equals(getEnglish,"")){
                    binding.tvEnglishProgress.setText("Not Attempted Yet");
                }else{
                    binding.tvEnglishProgress.setText(getEnglish);
                }

                if (Objects.equals(getMath,"")){
                    binding.tvMathProgress.setText("Not Attempted Yet");
                }else{
                    binding.tvMathProgress.setText(getMath);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}