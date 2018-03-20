package com.example.ashwani.incredibleindia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChatBox extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private List<ChatModel> list_chat=new ArrayList<>();
    private ImageView btn_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.list_msg);
        editText=(EditText)findViewById(R.id.user_msg);
        btn_send_message = (ImageView) findViewById(R.id.sendMsg);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=editText.getText().toString();
                ChatModel model=new ChatModel(text,true);
                list_chat.add(model);
                new SimsimiAPI().execute(list_chat);

                editText.setText("");
            }
        });

    }

    private class SimsimiAPI extends AsyncTask<List<ChatModel>,Void,String> {

        String stream = null;
        List<ChatModel> models;
        String text = editText.getText().toString();
        @Override
        protected String doInBackground(List<ChatModel>... params) {
            String url = String.format("http://sandbox.api.simsimi.com/request.p?key=%s&lc=en&ft=1.0&text=%s",getString(R.string.simsimi_api),text);
            models=params[0];
            HttpDataHandler httpDataHandler=new HttpDataHandler();
            stream=httpDataHandler.GetHTTPData(url);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            SimsimiModel response = gson.fromJson(s,SimsimiModel.class);

            ChatModel chatModel = new ChatModel(response.getResponse(),false); // get response from simsimi
            models.add(chatModel);
            CustomAdapter adapter = new CustomAdapter(models,getApplicationContext());
            listView.setAdapter(adapter);

        }
    }

}
