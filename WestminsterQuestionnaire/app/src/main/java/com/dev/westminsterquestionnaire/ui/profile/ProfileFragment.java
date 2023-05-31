package com.dev.westminsterquestionnaire.ui.profile;

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
import com.dev.westminsterquestionnaire.databinding.FragmentProfileBinding;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference dbReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
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

    private void showData() {
        //Show Progress Dialog
        progressDialog.setTitle("Going Good...");
        progressDialog.setMessage("It takes Just a few Seconds... ");
        progressDialog.setIcon(R.drawable.happy);
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Add Listener & Show Values
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String getImage = snapshot.child("image").getValue(String.class);
                Glide.with(binding.ivProfile).load(getImage).into(binding.ivProfile);
                binding.tvName.setText(snapshot.child("name").getValue(String.class));
                binding.tvEmail.setText(snapshot.child("email").getValue(String.class));
                binding.tvPhone.setText(snapshot.child("phone").getValue(String.class));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}