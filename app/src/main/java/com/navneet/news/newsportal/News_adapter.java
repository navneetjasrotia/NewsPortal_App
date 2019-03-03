package com.navneet.news.newsportal;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.ImageReader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class News_adapter extends ArrayAdapter<News> {
    Context context;
    LayoutInflater inflater;
    ImageView imageView;
    List<News> mnews;
    public News_adapter(@NonNull BlankFragment c, LinkedList<News> resource) {
        super(c.getContext(), 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        News w=getItem(position);
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.listview,parent,false);
        }
        TextView t1=(TextView)listItemView.findViewById(R.id.headline);
        TextView t2=(TextView)listItemView.findViewById(R.id.para);
        ImageView i1=(ImageView)listItemView.findViewById(R.id.image);
        t1.setText(w.string1());
        if(w.string2()==null)
            t2.setText("No description available");
        else
            t2.setText(w.string2());
        Picasso.with(this.getContext()).load(w.urlimage()).into(i1);
        return listItemView;
    }

}
