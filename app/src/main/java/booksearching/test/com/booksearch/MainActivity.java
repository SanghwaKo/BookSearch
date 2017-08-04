package booksearching.test.com.booksearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String B_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String EX_QUERY = "q=\"Elizabeth+Bennet\"+Darcy-Austen";
    /*
     to search for all entries that contain the exact phrase "Elizabeth Bennet" and the word "Darcy"
      but don't contain the word "Austen", use the following query parameter value:
     */
    private ArrayList<Book> mBooks;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mBooks = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, B_URL + EX_QUERY, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has(Constant.TAG_ITEMS)){
                            try{
                                JSONArray itemArray = response.getJSONArray(Constant.TAG_ITEMS);

                                for(int i=0; i<itemArray.length(); i++){
                                    Book newBook = new Book();
                                    JSONObject jsonObj = itemArray.getJSONObject(i);
                                    if(jsonObj.has(Constant.TAG_VOL_INFO)){
                                        JSONObject volInfoObj = jsonObj.getJSONObject(Constant.TAG_VOL_INFO);
                                        if(volInfoObj.has(Constant.TAG_TITLE)){
                                            newBook.setTitle(volInfoObj.getString(Constant.TAG_TITLE));
                                        }
                                        if(volInfoObj.has(Constant.TAG_IMAGE_LINK)){
                                            JSONObject imgLinkObj = volInfoObj.getJSONObject(Constant.TAG_IMAGE_LINK);
                                            if(imgLinkObj.has(Constant.TAG_THUM_IMG)){
                                                newBook.setCover(imgLinkObj.getString(Constant.TAG_THUM_IMG));
                                            }
                                        }
                                    }
                                    mBooks.add(newBook);
                                }

                            }catch (JSONException ex){
                                Log.e("MainActivity", ex.getLocalizedMessage());
                            }finally {
                                setListItems();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity", error.getLocalizedMessage());
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void setListItems(){
      //  Collections.sort(mBooks);
        ListAdapter adapter = new ListAdapter(this, mBooks);
        mRecyclerView.setAdapter(adapter);
    }
}
