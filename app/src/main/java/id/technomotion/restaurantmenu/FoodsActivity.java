package id.technomotion.restaurantmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by omayib on 06/04/16.
 */
public class FoodsActivity extends AppCompatActivity {

    ListView listView;
    List<String> items=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        listView= (ListView) findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);

        String token=getIntent().getStringExtra("data_token");

        RestaurantClient.Foods(token).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString=response.body().string();
                JSONObject jsonObject;
                try {
                    jsonObject=new JSONObject(jsonString);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objFood=jsonArray.getJSONObject(i);
                        items.add(objFood.getString("nama"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
