package com.navneet.news.newsportal;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView a[] = new TextView[56];
    TextView c[] = new TextView[7];
    AlertDialog alertd;
    static public int er=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
if(networkInfo==null)
{
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    Menu menu = navigationView.getMenu();
    MenuItem tool1= menu.findItem(R.id.title1);
    MenuItem tool2= menu.findItem(R.id.title2);
    SpannableString s1 = new SpannableString(tool1.getTitle());
    SpannableString s2 = new SpannableString(tool2.getTitle());
    s1.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s1.length(), 0);
    s2.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s2.length(), 0);
    tool1.setTitle(s1);
    tool2.setTitle(s2);
    navigationView.setNavigationItemSelectedListener(this);
    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
}
        else if(networkInfo.isConnected()&&networkInfo!=null) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new BlankFragment()).commit();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            MenuItem tool1= menu.findItem(R.id.title1);
            MenuItem tool2= menu.findItem(R.id.title2);
            SpannableString s1 = new SpannableString(tool1.getTitle());
            SpannableString s2 = new SpannableString(tool2.getTitle());
            s1.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s1.length(), 0);
            s2.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s2.length(), 0);
            tool1.setTitle(s1);
            tool2.setTitle(s2);
            navigationView.setNavigationItemSelectedListener(this);
        } else {
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.refresh)
        {
            startActivity(new Intent(this,MainActivity.class));
            this.finish();
        }

       else if (id == R.id.Country) {
            final View mView = getLayoutInflater().inflate(R.layout.countrydialog, null);
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setView(mView);
            alertd = mBuilder.create();
            alertd.show();
            for (int i = 1; i <= 54; i++)
            {
                String str2="text"+i;
                int resID = getResources().getIdentifier(str2, "id", getPackageName());
                a[i] = ((TextView) mView.findViewById(resID));
                a[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View mView){
                        er=1;
                        oni(mView);
                    }
                });
            }
        }
        else if(id==R.id.Categories)
        {
            final BlankFragment obj=new BlankFragment();
            obj.gh = 599;
            final View mView1 = getLayoutInflater().inflate(R.layout.sub_categories, null);
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setView(mView1);
            alertd = mBuilder.create();
            alertd.show();
            for (int i = 1; i<=6; i++)
            {
                String str1="cat"+i;
                int resI = getResources().getIdentifier(str1, "id", getPackageName());
                c[i] = ((TextView) mView1.findViewById(resI));
                c[i].setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View mView1) {
                    if(er==1)
                        category(mView1);
                    else
                    {
                        Toast.makeText(MainActivity.this,"Select Country First",Toast.LENGTH_SHORT).show();
                        alertd.cancel();
                    }
                    }
                });
            }
        }
        else if(id==R.id.home)
        {
            BlankFragment blankFragment=new BlankFragment();
            blankFragment.gh=58;
            startActivity(new Intent(this,MainActivity.class));
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void oni(View v) {
        BlankFragment blankFragment=new BlankFragment();
        blankFragment.gh = 500;
        for (int i = 1; i <= 54; i++) {
            if (a[i].getId() == v.getId()) {
                if (i == 1) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=ar&apiKey=a401ace2f7944c74904c771608ef9693";

                } else if (i == 2) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=au&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 3) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=at&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 4) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=be&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 5) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=br&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 6) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=bg&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 7) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=ca&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 8) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=cn&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 9) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=co&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 10) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=cu&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 11) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=cz&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 12) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=eg&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 13) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=fr&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 14) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=de&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 15) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=gr&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 16) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=hk&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 17) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=hu&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 18) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=in&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 19) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=id&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 20) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=ie&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 21) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=il&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 22) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=it&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 23) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=jp&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 24) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=lv&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 25) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=lt&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 26) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=my&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 27) {
                    blankFragment. str = "https://newsapi.org/v2/top-headlines?country=mx&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 28) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=ma&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 29) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=nl&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 30) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=nz&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 31) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=ng&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 32) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=no&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 33) {
                    blankFragment.   str = " https://newsapi.org/v2/top-headlines?country=ph&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 34) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=pl&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 35) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=pt&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 36) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=ro&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 37) {
                    blankFragment.  str = " https://newsapi.org/v2/top-headlines?country=ru&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 38) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=sa&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 39) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=rs&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 40) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=sg&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 41) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=sk&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 42) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=si&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 43) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=za&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 44) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=kr&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 45) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=se&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 46) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=ch&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 47) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=tw&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 48) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=th&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 49) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=tr&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 50) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=ae&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 51) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=ua&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 52) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=gb&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 53) {
                    blankFragment. str = " https://newsapi.org/v2/top-headlines?country=us&apiKey=a401ace2f7944c74904c771608ef9693";
                } else if (i == 54) {
                    blankFragment.str = " https://newsapi.org/v2/top-headlines?country=ve&apiKey=a401ace2f7944c74904c771608ef9693";
                }
                blankFragment.gh=234;
            }
        }
       startActivity(new Intent(this,MainActivity.class));
        this.finish();
        alertd.cancel();
    }
    public void category(View mView1) {
        BlankFragment blankFragment = new BlankFragment();
        String st = new String();
            for (int i = 1; i <= 6; i++) {
                if (c[i].getId() == mView1.getId()) {
                    if (i == 1) {
                        st = "";
                        for (int j = 0; j < blankFragment.str.length(); j++) {
                            if (blankFragment.str.charAt(j) == '&') {
                                st = st + "&category=business&";
                            } else
                                st = st + blankFragment.str.charAt(j);
                        }

                    } else if (i == 2) {
                        st = "";
                        for (int j = 0; j < blankFragment.str.length(); j++) {
                            if (blankFragment.str.charAt(j) == '&') {
                                st = st + "&category=entertainment&";
                            } else
                                st = st + blankFragment.str.charAt(j);
                        }
                    } else if (i == 3) {
                        st = "";
                        for (int j = 0; j < blankFragment.str.length(); j++) {
                            if (blankFragment.str.charAt(j) == '&') {
                                st = st + "&category=health&";
                            } else
                                st = st + blankFragment.str.charAt(j);
                        }

                    } else if (i == 4) {
                        st = "";
                        for (int j = 0; j < blankFragment.str.length(); j++) {
                            if (blankFragment.str.charAt(j) == '&') {
                                st = st + "&category=science&";
                            } else
                                st = st + blankFragment.str.charAt(j);
                        }

                    } else if (i == 5) {
                        st = "";
                        for (int j = 0; j < blankFragment.str.length(); j++) {
                            if (blankFragment.str.charAt(j) == '&') {
                                st = st + "&category=sports&";
                            } else
                                st = st + blankFragment.str.charAt(j);
                        }

                    } else if (i == 6) {
                        st = "";
                        for (int j = 0; j < blankFragment.str.length(); j++) {
                            if (blankFragment.str.charAt(j) == '&') {
                                st = st + "&category=technology&";
                            } else
                                st = st + blankFragment.str.charAt(j);
                        }

                    }
                }
            }
            blankFragment.str = st;
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
            alertd.cancel();


    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        BlankFragment blankFragment=new BlankFragment();
        String st=new String();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        }
        else if(id==R.id.about)
        {
            String st11="https://navneetjasrotia.github.io";
            Intent intent= null;
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(st11));
                startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else if(id==R.id.contact)
        {
            String st11="navneetjasrotia88@gmail.com";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"+"navneetjasrotia88@gmail.com"));
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else if (id == R.id.abc) {er=0;

            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=abc-news&apiKey=a401ace2f7944c74904c771608ef9693";
        } else if (id == R.id.bbc) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=a401ace2f7944c74904c771608ef9693";

        } else if (id == R.id.cnbc) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=cnbc&apiKey=a401ace2f7944c74904c771608ef9693";

        } else if (id == R.id.espn) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=espn&apiKey=a401ace2f7944c74904c771608ef9693";

        }
        else if (id == R.id.google) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=a401ace2f7944c74904c771608ef9693";

        }
        else if (id == R.id.hindu) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=the-hindu&apiKey=a401ace2f7944c74904c771608ef9693";

        }
        else if (id == R.id.news23) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=news24&apiKey=a401ace2f7944c74904c771608ef9693";

        }
        else if (id == R.id.times) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=the-times-of-india&apiKey=a401ace2f7944c74904c771608ef9693";
        }
        else if (id == R.id.usa) {er=0;
            blankFragment.str =" https://newsapi.org/v2/top-headlines?sources=usa-today&apiKey=a401ace2f7944c74904c771608ef9693";

        }
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
