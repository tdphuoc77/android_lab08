package com.example.lab08_2105;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoodActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnHappy, btnUnhappy, btnNormal;
    private Button btnFinish;
    private String userId;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);
        btnHappy = findViewById(R.id.happy);
        btnUnhappy = findViewById(R.id.unhappy);
        btnNormal = findViewById(R.id.nor);
        btnFinish = findViewById(R.id.btnFinish);
        db = FirebaseFirestore.getInstance();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userId = null;
            } else {
                userId= extras.getString("USERID");
            }
        } else {
            userId= (String) savedInstanceState.getSerializable("USERID");
        }

        documentReference = db.collection("Users").document(userId);
        getData();

        btnHappy.setOnClickListener(this);
        btnUnhappy.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    private void getData(){
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.happy:{
                int happy= user.getHappy()+1;
                user.setHappy(happy);
                Toast.makeText(MoodActivity.this, "Happy :)", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.unhappy:{
                int unhappy=user.getUnhappy()+1;
                user.setUnhappy(unhappy);
                Toast.makeText(MoodActivity.this, "Unhappy :(", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nor:{
                int nor=user.getNormal()+1;
                user.setNormal(nor);
                Toast.makeText(MoodActivity.this, "Normal", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnFinish:{
                documentReference.update(
                        "happy", user.getHappy(),
                        "unhappy", user.getUnhappy(),
                        "normal", user.getNormal()
                );
                Toast.makeText(MoodActivity.this, "Update successful!!!", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
}
