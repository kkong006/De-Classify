package teamawesome.cs180frontend.Misc.ViewHolders;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import teamawesome.cs180frontend.R;

public class SimpleViewHolder {
    @Bind(R.id.simple_textview) public TextView textView;

    public SimpleViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

}
