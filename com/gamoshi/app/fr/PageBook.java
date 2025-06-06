package com.gamoshi.app.fr;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gamoshi.app.ActivityMain1;
import com.gamoshi.app.R;
import com.gamoshi.app.adapt.BlockView;
import com.google.android.exoplayer2.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.prebid.mobile.TargetingParams;

import java.io.InputStream;
import java.util.Set;

public class PageBook extends Fragment {
    LinearLayout root;
    ActivityMain1 main;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        main = (ActivityMain1) getActivity();
        root = new LinearLayout(main);
        root.setOrientation(LinearLayout.VERTICAL);
        return inflater.inflate(R.layout.activity_demo,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.tvTestCaseName) ;
        FrameLayout frameAdWrapper = view.findViewById(R.id.frameAdWrapper);
        View v = LayoutInflater.from(main).inflate(R.layout.page_book, frameAdWrapper);
        BlockView blockView = new BlockView(getActivity());

        FrameLayout banner = v.findViewById(R.id.banner);
        banner.addView(root);
        try {
            JSONObject ob = main.ob.getJSONObject(main.item);
            title.setText(ob.getString("title"));
            TextView author = v.findViewById(R.id.author) ;
            author.setText(ob.getString("author"));
            TextView context = v.findViewById(R.id.context) ;
            context.setText(ob.getString("context"));
            ImageView poster = v.findViewById(R.id.poster) ;
            Uri img = Uri.parse(ob.getString("img"));
            InputStream is = main.getAssets().open(img.getLastPathSegment());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            poster.setImageBitmap(bitmap);
           // Log.e(",mmn m", ""+ img.getLastPathSegment());
          //  TargetingParams.addExtData("page",ob.getString("id"));
           // TargetingParams.addUserKeyword(ob.getString("id"));

            blockView.setLoad(null,ob.getString("id"),ob.getString("id"),true);
            root.addView(blockView);
            //BlockView blockView1 = new BlockView(main);
           // String[] Sp = Home_Page.data.split("\n\n");
            //blockView1.setLoad(main,"    " + Sp[1] +"\n",null,false);
           // root.addView(blockView1);

        } catch ( Exception e) {
           e.printStackTrace();
        }

    }
}
