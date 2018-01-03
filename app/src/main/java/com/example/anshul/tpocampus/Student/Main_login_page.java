package com.example.anshul.tpocampus.Student;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.anshul.tpocampus.Admin.AdminLoginActivity;
import com.example.anshul.tpocampus.R;

public class Main_login_page extends AppCompatActivity {

    Button stu_button, admin_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_page);

        setTitle("MNNIT Allahabad");
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_login_page_toolbar);
//        setSupportActionBar(myToolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }

        stu_button = (Button) findViewById(R.id.stu_button);
        admin_button = (Button) findViewById(R.id.admin_button);

        stu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_login_page.this, LoginActivity.class));
            }
        });

        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_login_page.this, AdminLoginActivity.class));
            }
        });
    }
}
