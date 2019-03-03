package com.navneet.news.newsportal;

public class News  {
    private String str11,str2="",urlImage;
    public String urlString="";
    News()
    {
    }
    public News(String str,String str1,String urlimage,String urlstring)
    {
        str11=str;
        if(str1!=null&&str2==""){

            for(int i=0;i<str1.length();i++)
            {
                if(str1.charAt(i)>='A'&&str1.charAt(i)<='Z'||str1.charAt(i)>='a'&&str1.charAt(i)<='z'||str1.charAt(i)==' ')
                    str2=str2+str1.charAt(i);
                else if(str1.charAt(i)=='.')
                    break;
            }
        }
        else if(str1==null)
        {
            str2="No Description Available";
        }
        urlImage=urlimage;
        urlString=urlstring;

    }
    public String string1()
    {
        return str11;
    }
    public String string2()
    {
        return str2;
    }
    public String urlimage()
    {
        return urlImage;
    }
    public String urlstrings()
    {
        return urlString;
    }
}
