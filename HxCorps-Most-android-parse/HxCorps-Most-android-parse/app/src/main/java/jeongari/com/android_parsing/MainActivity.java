package jeongari.com.android_parsing;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    Button getActionBtn;
    Runnable task;
    String html[];

    String givenUrl = "http://news.joins.com/article/22123706";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        textview = (TextView) findViewById(R.id.textview);

        getActionBtn = (Button) findViewById(R.id.getActionBtn);
        getActionBtn.setOnClickListener(mClickListener);


        task = new Runnable(){

            public void run(){


                html = getContext(givenUrl);
            }

        };

    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Thread thread = new Thread(task);
            thread.start();

            try{

                thread.join();  // 쓰레드 작업 끝날때까지 다른 작업들은 대기

            }catch(Exception e){



            }
            textview.setText("");

            SpannableString header = new SpannableString(html[0]);
            header.setSpan(new AbsoluteSizeSpan(20, true),0,html[0].length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            header.setSpan(new ForegroundColorSpan(Color.BLACK),0, html[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            header.setSpan(new StyleSpan(Typeface.BOLD),0,html[0].length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textview.append(header);
            textview.append("\n\n");

            String sourcedate = html[1]+" "+html[2]+ " " + html[3];
            SpannableString date = new SpannableString(sourcedate);
            date.setSpan(new AbsoluteSizeSpan(13, true),0, sourcedate.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            date.setSpan(new ForegroundColorSpan(Color.GRAY),0, sourcedate.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textview.append(date);

            textview.append("\n\n");

            textview.append(html[3]);


        }
    };

    public String[] getContext(String sourceUrlString) {
        List<Element> h1, div, p;
        Element article;
        String contents[] = new String[10];

        try {

             URL sUrl = new URL(sourceUrlString);
             InputStream is = sUrl.openStream();
             Source source= new Source(new InputStreamReader(is,"utf-8"));

            // 전체 소스 구문을 분석한다.
            source.fullSequentialParse();
            // HTML markup에서 필요한 부분만 가져와서 source로 변환한다.

            h1 =source.getAllElements(HTMLElementName.H1);
            div = source.getAllElements(HTMLElementName.DIV);

            // 각각의 content를 가져와서 contents string array에 넣는다

            for(int i =0; i<h1.size();i++)
            {
                Element element = (Element)h1.get(i);
                contents[0] = element.getContent().toString();
            }

            for(int i=0;i<div.size();i++){
                Element element = div.get(i);
                String attributeValue = element.getAttributeValue("class");
                if(attributeValue.equalsIgnoreCase("source_date")){
                    List<Element> elements = element.getAllElements(HTMLElementName.SPAN);

                    for(int j=0; j<elements.size();j++){
                        Element element2 = elements.get(j);
                        contents[j+1] = element2.getContent().toString();
                    }
                }
            }

            article = source.getElementById("article_body");
            contents[3] = article.getTextExtractor().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }


}
