package demo.com.wdmoviedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class Film_Details_Activity extends AppCompatActivity {

    private ImageView image_details;
    private TextView txt_details;
    private RelativeLayout top;
    private TextView details_txt_name;
    private SimpleDraweeView details_sdv_image;
    private TextView details_txt_details;
    private TextView details_txt_prediction;
    private TextView details_txt_stills;
    private TextView details_txt_review;
    private ImageView details_image_back;
    private TextView _details_txt_buy;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film__details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        image_details = (ImageView) findViewById(R.id.image_details);
        txt_details = (TextView) findViewById(R.id.txt_details);
        top = (RelativeLayout) findViewById(R.id.top);
        details_txt_name = (TextView) findViewById(R.id.details_txt_name);
        details_sdv_image = (SimpleDraweeView) findViewById(R.id.details_sdv_image);
        details_txt_details = (TextView) findViewById(R.id.details_txt_details);
        details_txt_prediction = (TextView) findViewById(R.id.details_txt_prediction);
        details_txt_stills = (TextView) findViewById(R.id.details_txt_stills);
        details_txt_review = (TextView) findViewById(R.id.details_txt_review);
        details_image_back = (ImageView) findViewById(R.id.details_image_back);
        _details_txt_buy = (TextView) findViewById(R.id._details_txt_buy);
    }
}
