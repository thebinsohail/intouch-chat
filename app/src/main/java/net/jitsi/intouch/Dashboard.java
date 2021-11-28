package net.jitsi.intouch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.URL;

public class Dashboard extends AppCompatActivity {

    EditText secretBox;
    Button joinButton,shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        secretBox=findViewById(R.id.secretBox);
        joinButton=findViewById(R.id.joinButton);
        shareButton=findViewById(R.id.shareButton);

        URL url;

        try{
            url=new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions=
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(url)
                            .setVideoMuted(true)
                            .setAudioMuted(true)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        }catch (Exception exception){
            exception.printStackTrace();
        }


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("secretcode", secretBox.getText());
                clipboard.setPrimaryClip(clip);

                if(clipboard.hasText()){
                    Toast.makeText(Dashboard.this,"Copied",Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(Dashboard.this,"Nothing to copy!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JitsiMeetConferenceOptions options=
                        new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretBox.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(Dashboard.this,options);


            }
        });
    }
}











