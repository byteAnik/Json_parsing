package com.example.new_class1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    ArrayList <HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar);
        listView=findViewById(R.id.listView);

        String url="https://icelandic-things.000webhostapp.com/apps/video.json";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                try {

                    for (int x=0;x<jsonArray.length();x++){
                        JSONObject jsonObject=jsonArray.getJSONObject(x);
                        String tittle=jsonObject.getString("tittle");
                        String video_id=jsonObject.getString("video_id");
                      hashMap=new HashMap<>();
                      hashMap.put("tittle",tittle);
                      hashMap.put("video_id",video_id);
                      arrayList.add(hashMap);
                        Toast.makeText(getApplicationContext(),"JSONArray!",Toast.LENGTH_SHORT).show();

                    }
                    MyAdapter myAdapter=new MyAdapter();
                    listView.setAdapter(myAdapter);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(arrayRequest);
    }
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater=getLayoutInflater();
            View myView=layoutInflater.inflate(R.layout.iteam,null);

            TextView textView=myView.findViewById(R.id.textView);
            ImageView imageView=myView.findViewById(R.id.imageView);

            HashMap<String,String>hashmap=arrayList.get(position);
            String tittle=hashmap.get("tittle");
            String video_id=hashmap.get("video_id");

            String image_url="https://img.youtube.com/vi/"+video_id+"/0.jpg";
            textView.setText(tittle);
            Picasso.get().load(image_url).placeholder(R.drawable.my_image).into(imageView);
            return myView;
        }
    }
}