package teamawesome.cs180frontend.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teamawesome.cs180frontend.API.Models.ClassBundle;
import teamawesome.cs180frontend.API.Models.Professor;
import teamawesome.cs180frontend.API.RetrofitSingleton;
import teamawesome.cs180frontend.API.Services.Callbacks.GetProfessorsCallback;
import teamawesome.cs180frontend.Misc.DataSingleton;
import teamawesome.cs180frontend.Misc.Utils;
import teamawesome.cs180frontend.R;

public class WriteReviewActivity extends AppCompatActivity {

    @Bind(R.id.write_professor_et) EditText mProfessorName;
    @Bind(R.id.write_class_et) EditText mClassName;
    @Bind(R.id.write_review_et) EditText mReviewText;
    @Bind(R.id.write_rate_1) Button mStar1;
    @Bind(R.id.write_rate_2) Button mStar2;
    @Bind(R.id.write_rate_3) Button mStar3;
    @Bind(R.id.write_rate_4) Button mStar4;
    @Bind(R.id.write_rate_5) Button mStar5;
    @Bind(R.id.write_submit_bt) Button mSubmit;

    private Button[] mStars;
    private int mClassId;
    private int mProfId;
    private int mRating;
    private String mReview;

    private List<Professor> professors;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mStars = new Button[] {mStar1, mStar2, mStar3, mStar4, mStar5};

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (Utils.getSchoolId(this) != 0) {
            RetrofitSingleton.getInstance()
                    .getMatchingService()
                    .getProfs(Utils.getSchoolId(this))
                    .enqueue(new GetProfessorsCallback());
        } else {
            finish();
        }
    }

    @OnClick(R.id.write_submit_bt)
    public void submitReview() {
        String professorName = mProfessorName.getText().toString();
        String className = mClassName.getText().toString();
        String reviewText = mReviewText.getText().toString();
        if(reviewText.length() >= 32) {
            getClasses();
            getProfessors();

        } else {
            Toast.makeText(this, getResources().getString(R.string.review_not_long_enough), Toast.LENGTH_SHORT).show();
        }
    }

    public void getClasses() {
    }

    public void getProfessors() {

    }

    @OnClick(R.id.write_rate_2)
    public void writeRating2() {
        setStarColor(2);
    }

    @OnClick(R.id.write_rate_3)
    public void writeRating3() {
        setStarColor(3);
    }

    @OnClick(R.id.write_rate_4)
    public void writeRating4() {
        setStarColor(4);
    }

    @OnClick(R.id.write_rate_5)
    public void writeRating5() {
        setStarColor(5);
    }
    private void setStarColor(int count) {
        mRating = count;
        for(int i = 0; i < 5; i++){
            if(i < count) {
                mStars[i].setTextColor(getApplicationContext().getResources().getColor(R.color.colorGreen));
            } else {
                mStars[i].setTextColor(getApplicationContext().getResources().getColor(R.color.colorGrey));
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onResponse(List<Professor> profs) {
        progressDialog.dismiss();
        professors = new ArrayList<>();
        professors.addAll(profs);
    }

    @Subscribe
    public void onFailure(Integer code) {
        progressDialog.dismiss();
        finish();
    }
}
