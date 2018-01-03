package com.example.anshul.tpocampus.Student;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ANSHUL KISHORE on 06-10-2017.
 */

public class frag_photo_resume extends Fragment{

    private  Button ph_choose_photo, ph_upload_photo, ph_choose_resume, ph_upload_resume;
    private  ImageView ph_imageView;
    private Uri filePath_photo, filePath_resume;
    private static final String DEFAULT = "N/A";
    public static final int PICK_IMAGE_REQUEST = 234, PICK_FILE_REQUEST = 123;

    String regno, user_batch, user_branch;

    private StorageReference storageReference;
    private DatabaseReference pwd_mDatabase;

    SharedPreferences sharedPreferences;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    UserInfo_personal uInfo_personal = null;

    public frag_photo_resume() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_photo_resume, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Upload Photo/Resume");

        storageReference = FirebaseStorage.getInstance().getReference();
        pwd_mDatabase = FirebaseDatabase.getInstance().getReference();

        ph_choose_photo = (Button) getActivity().findViewById(R.id.ph_photo);
        ph_upload_photo = (Button) getActivity().findViewById(R.id.ph_upload_photo);
        ph_choose_resume = (Button) getActivity().findViewById(R.id.ph_resume);
        ph_upload_resume = (Button) getActivity().findViewById(R.id.ph_upload_resume);
        ph_imageView = (ImageView) getActivity().findViewById(R.id.ph_imageView);

        ph_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open file chooser
                showFileChooser_photo();
            }
        });

        ph_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload to firebase storage
                uploadphoto();
            }
        });

        ph_choose_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser_file();
            }
        });

        ph_upload_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadfile();
            }
        });
    }

    public void showFileChooser_photo(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a photo"), PICK_IMAGE_REQUEST);
    }

    public void showFileChooser_file(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a file"), PICK_FILE_REQUEST );
    }

    public void uploadphoto(){
        if(filePath_photo != null) {

            sharedPreferences = getActivity().getSharedPreferences("edit_profile", Context.MODE_PRIVATE);
            final String is_for_editing = sharedPreferences.getString("is_to_edit_profile", "no");

            final ProgressDialog progressDialog = new ProgressDialog(this.getActivity());

            if(is_for_editing.equalsIgnoreCase("yes")){
                SharedPreferences f_per_sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
                regno = f_per_sharedPreferences1.getString("logged_in", DEFAULT);
                user_batch = f_per_sharedPreferences1.getString("logged_in_batch", DEFAULT);
            }
            else {
                SharedPreferences ph_sharedPreferences1 = this.getActivity().getSharedPreferences("My_data_personal", Context.MODE_PRIVATE);
                regno = ph_sharedPreferences1.getString("regno", DEFAULT);
                user_batch = ph_sharedPreferences1.getString("batch", DEFAULT);
                user_branch = ph_sharedPreferences1.getString("branch", DEFAULT);
            }

            progressDialog.setTitle("Uploading Profile Photo");
            progressDialog.show();

            StorageReference riversRef = storageReference.child(user_batch).child("users").child(regno).child("images/profile.jpg");

            riversRef.putFile(filePath_photo)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "File Uploaded!!", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")Uri downloadUrl_photo = taskSnapshot.getDownloadUrl();
                            pwd_mDatabase.child(user_batch).child("users").child(regno).child("photo").setValue(downloadUrl_photo.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage((int)progress + "% Uploaded...");
                }
            });

        }
        else{
            Toast.makeText(getActivity(), "No File Chosen!!", Toast.LENGTH_LONG).show();
        }
    }

    public void uploadfile(){
        if(filePath_resume != null) {

            sharedPreferences = getActivity().getSharedPreferences("edit_profile", Context.MODE_PRIVATE);
            final String is_for_editing = sharedPreferences.getString("is_to_edit_profile", "no");

            final ProgressDialog progressDialog = new ProgressDialog(this.getActivity());

            if(is_for_editing.equalsIgnoreCase("yes")){
                SharedPreferences f_per_sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
                regno = f_per_sharedPreferences1.getString("logged_in", DEFAULT);
                user_batch = f_per_sharedPreferences1.getString("logged_in_batch", DEFAULT);

                /*mDatabase = mFirebaseDatabase.getReference();

                uInfo_personal = new UserInfo_personal();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        uInfo_personal = dataSnapshot.child(user_batch).child("users").child(regno).child("personal").getValue(UserInfo_personal.class);
                        //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                        user_branch = uInfo_personal.getBranch();

                        progressDialog.setTitle("Uploading Resume");
                        progressDialog.show();

                        StorageReference riversRef = storageReference.child("user_batch").child("users").child(regno).child("Resume/resume.pdf");

                        riversRef.putFile(filePath_resume)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Get a URL to the uploaded content
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "File Uploaded!!", Toast.LENGTH_LONG).show();
                                        @SuppressWarnings("VisibleForTests")Uri downloadUrl_resume = taskSnapshot.getDownloadUrl();
                                        pwd_mDatabase.child(user_batch).child("users").child(user_branch).child(regno).child("resume").setValue(downloadUrl_resume.toString());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage((int)progress + "% Uploaded...");
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
            }
            else {
                SharedPreferences ph_sharedPreferences1 = this.getActivity().getSharedPreferences("My_data_personal", Context.MODE_PRIVATE);
                regno = ph_sharedPreferences1.getString("regno", DEFAULT);
                user_batch = ph_sharedPreferences1.getString("batch", DEFAULT);
                user_branch = ph_sharedPreferences1.getString("branch", DEFAULT);SharedPreferences.Editor ph_editor = ph_sharedPreferences1.edit();
            }

            progressDialog.setTitle("Uploading Resume");
            progressDialog.show();

            StorageReference riversRef = storageReference.child(user_batch).child("users").child(regno).child("Resume/resume.pdf");

            riversRef.putFile(filePath_resume)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "File Uploaded!!", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")Uri downloadUrl_resume = taskSnapshot.getDownloadUrl();
                            pwd_mDatabase.child(user_batch).child("users").child(regno).child("resume").setValue(downloadUrl_resume.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage((int)progress + "% Uploaded...");
                }
                });
        }
        else{
            Toast.makeText(getActivity(), "No File Chosen!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath_photo = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath_photo);
                    ph_imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath_resume = data.getData();
                ph_imageView.setImageResource(R.drawable.file);
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
