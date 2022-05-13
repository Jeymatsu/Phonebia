package com.example.splash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splash.Db.Password_Database;


public class LockScreen extends AppCompatActivity {
    Button btn;
    TextView status;
    utils utils;

    /* renamed from: t1 */
    EditText f78t1;

    /* renamed from: v1 */
   // String f79v1;
    String pass="";

    /* renamed from: v2 */
    String f80v2;
    Password_Database f79v1= new Password_Database(this);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_lock_screen);
        this.btn = (Button) findViewById(R.id.unlock_btn);
        this.f78t1 = findViewById(R.id.unlock_pass);
        this.status = (TextView) findViewById(R.id.App_name);
        Bundle extras = getIntent().getExtras();
        Cursor allData2 = this.f79v1.getAllData();
        while (allData2.moveToNext()) {
            this.pass = allData2.getString(0);
        }
        if (extras != null) {

            this.f80v2 = extras.getString("pack");

        }
       this.btn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
                if (LockScreen.this.pass.equals(LockScreen.this.f78t1.getText().toString())) {
                    Intent launchIntentForPackage = LockScreen.this.getPackageManager().getLaunchIntentForPackage(LockScreen.this.f80v2);
                    launchIntentForPackage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    LockScreen.this.startActivity(launchIntentForPackage);
                    LockScreen.this.finish();
                    Toast.makeText(LockScreen.this, "Password Correct", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(LockScreen.this, "wrong password", Toast.LENGTH_LONG).show();
                LockScreen.this.f78t1.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


