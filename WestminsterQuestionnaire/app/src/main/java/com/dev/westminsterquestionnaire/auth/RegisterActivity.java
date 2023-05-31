package com.dev.westminsterquestionnaire.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.dev.westminsterquestionnaire.MainActivity;
import com.dev.westminsterquestionnaire.R;
import com.dev.westminsterquestionnaire.databinding.ActivityRegisterBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    String imageUri = "";
    int PICK_IMAGE_GALLERY = 124;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference dbReference;
    StorageReference storageRef, imageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Going Good...");
        progressDialog.setMessage("It takes Just a few Seconds... ");
        progressDialog.setIcon(R.drawable.happy);
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance(
                "https://westminster-questionnaire-default-rtdb.firebaseio.com").getReference("UsersData");
        storageRef = FirebaseStorage.getInstance().getReference("ProfileImages");

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setTitle("Going Good...");
                progressDialog.setMessage("It takes Just a few Seconds... ");
                progressDialog.setIcon(R.drawable.happy);
                progressDialog.setCancelable(false);

                String name = binding.etName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String phoneNumber = binding.etPhone.getText().toString();
                String password = binding.etPassword.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please enter email in correct format", Toast.LENGTH_SHORT).show();
                } else if (phoneNumber.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (imageUri.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            imageReference = storageRef.child(Uri.parse(imageUri).getLastPathSegment());
                            imageReference.putFile(Uri.parse(imageUri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            String uploadedImgURL = uri.toString();

                                            RegisterModel model = new RegisterModel(name, email, phoneNumber, password, uploadedImgURL,"","","");
                                            dbReference.child(auth.getCurrentUser().getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialog.dismiss();
                                                    showToast("Successfully Registered");
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    showToast(e.getLocalizedMessage());
                                                }
                                            });

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            showToast(e.getLocalizedMessage());
                        }
                    });
                }
            }
        });

        binding.ivProfile.setOnClickListener(v -> {

            ImagePicker.with(this)
                    .compress(512)
                    .maxResultSize(512, 512)
                    .start();

            Intent pickPhoto = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Getting Gallery Image uri
            Uri uriImage = data.getData();
            try {
                binding.ivProfile.setImageURI(uriImage);
                imageUri = uriImage.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}