package com.navneet.news.newsportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class BlankFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ListView thelist;
    private OnFragmentInteractionListener mListener;
    String jsonResponse = "";
    TextView a[] = new TextView[56];
    TextView c[] = new TextView[7];
    AlertDialog alertd;
    static int number=1;
    News_adapter madapter;
    static public int gh = 58;
    static String str = new String();
    final List<News> array_news = new LinkedList<News>();
    public BlankFragment() {
    }

    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        if (gh == 58)
        {
            str =" https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=a401ace2f7944c74904c771608ef9693";
            gh=234;
        }
        Newstask newstask = new Newstask();
        newstask.execute(str);
        thelist=view.findViewById(R.id.list);
       madapter = new News_adapter(this,new LinkedList<News>());
        thelist.setAdapter(madapter);
        thelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String url=array_news.get(i).urlstrings();
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(intent);
            }
        });
return view;
    }

    @Override
    public void onViewCreated(View view,Bundle bundle)
    {
            }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    private class Newstask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            URL url = createURL(str);
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<News> news = null;
            try {
                news = extractFromJson(jsonResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return news;
        }

        private List<News> extractFromJson(String jsonResponse) throws JSONException {
            String title = "";
            String content = "";
            String imageUrl = "";
            String urlstring11="";
            if (TextUtils.isEmpty(jsonResponse))
                return null;
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("articles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentNews = jsonArray.getJSONObject(i);
                    title = currentNews.getString("title");
                    content = currentNews.getString("content");
                    urlstring11 = currentNews.getString("url");
                    imageUrl = currentNews.getString("urlToImage");
                    News news = new News(title, content, imageUrl,urlstring11);
                    array_news.add(news);
                }
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }
            return array_news;
        }

        private String makeHttpConnection(URL url) throws IOException {
            jsonResponse = "";
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = httpURLConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {

                }

            } catch (IOException e) {
                Log.e("No Http Connection", "No Http message");
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        @Override
        protected void onPostExecute(List<News> data) {
           madapter.clear();
           if(data!=null && !data.isEmpty())
           {
               madapter.addAll(data);
           }
        }

        private URL createURL(String str) {
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }
    }
}
