package com.ly.cc.view.custcollect.listview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ly.cc.R;
import com.ly.cc.custview.listview.horizonListView.HorizontalListView;
import com.ly.cc.utils.T;

import butterknife.ButterKnife;

public class HorizontalListViewActivity extends ActionBarActivity {

    private HorizontalListView mHlvSimpleList;
    private HorizontalListView mHlvCustomList;
    private HorizontalListView mHlvCustomListWithDividerAndFadingEdge;

    private String[] mSimpleListValues = new String[]{"Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2"};

    private CustomData[] mCustomData = new CustomData[]{
            new CustomData(Color.RED, "Red"),
            new CustomData(Color.DKGRAY, "Dark Gray"),
            new CustomData(Color.GREEN, "Green"),
            new CustomData(Color.LTGRAY, "Light Gray"),
            new CustomData(Color.WHITE, "White"),
            new CustomData(Color.RED, "Red"),
            new CustomData(Color.BLACK, "Black"),
            new CustomData(Color.CYAN, "Cyan"),
            new CustomData(Color.DKGRAY, "Dark Gray"),
            new CustomData(Color.GREEN, "Green"),
            new CustomData(Color.RED, "Red"),
            new CustomData(Color.LTGRAY, "Light Gray"),
            new CustomData(Color.WHITE, "White"),
            new CustomData(Color.BLACK, "Black"),
            new CustomData(Color.CYAN, "Cyan"),
            new CustomData(Color.DKGRAY, "Dark Gray"),
            new CustomData(Color.GREEN, "Green"),
            new CustomData(Color.LTGRAY, "Light Gray"),
            new CustomData(Color.RED, "Red"),
            new CustomData(Color.WHITE, "White"),
            new CustomData(Color.DKGRAY, "Dark Gray"),
            new CustomData(Color.GREEN, "Green"),
            new CustomData(Color.LTGRAY, "Light Gray"),
            new CustomData(Color.WHITE, "White"),
            new CustomData(Color.RED, "Red"),
            new CustomData(Color.BLACK, "Black"),
            new CustomData(Color.CYAN, "Cyan"),
            new CustomData(Color.DKGRAY, "Dark Gray"),
            new CustomData(Color.GREEN, "Green"),
            new CustomData(Color.LTGRAY, "Light Gray"),
            new CustomData(Color.RED, "Red"),
            new CustomData(Color.WHITE, "White"),
            new CustomData(Color.BLACK, "Black"),
            new CustomData(Color.CYAN, "Cyan"),
            new CustomData(Color.DKGRAY, "Dark Gray"),
            new CustomData(Color.GREEN, "Green"),
            new CustomData(Color.LTGRAY, "Light Gray")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_list_view);
        ButterKnife.inject(this);

        // Get references to UI widgets
        mHlvSimpleList = (HorizontalListView) findViewById(R.id.hlvSimpleList);
        mHlvCustomList = (HorizontalListView) findViewById(R.id.hlvCustomList);
        mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView) findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);

        setupSimpleList();
        setupCustomLists();
    }

    private void setupSimpleList() {
        // Make an array adapter using the built in android layout to render a list of strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, mSimpleListValues);

        // Assign adapter to the HorizontalListView
        mHlvSimpleList.setAdapter(adapter);

        mHlvSimpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                T.showShort(HorizontalListViewActivity.this, mSimpleListValues[position]);
            }
        });
    }

    private void setupCustomLists() {
        // Make an array adapter using the built in android layout to render a list of strings
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, mCustomData);

        // Assign adapter to HorizontalListView
        mHlvCustomList.setAdapter(adapter);
        mHlvCustomListWithDividerAndFadingEdge.setAdapter(adapter);

        mHlvCustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                T.showShort(HorizontalListViewActivity.this, mCustomData[position].getText());
            }
        });

        mHlvCustomListWithDividerAndFadingEdge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                T.showShort(HorizontalListViewActivity.this, mCustomData[position].getText());
            }
        });
    }


    class CustomData {
        private int mBackgroundColor;
        private String mText;

        public CustomData(int backgroundColor, String text) {
            mBackgroundColor = backgroundColor;
            mText = text;
        }

        /**
         * @return the backgroundColor
         */
        public int getBackgroundColor() {
            return mBackgroundColor;
        }

        /**
         * @return the text
         */
        public String getText() {
            return mText;
        }
    }

    class CustomArrayAdapter extends ArrayAdapter<CustomData> {
        private LayoutInflater mInflater;

        public CustomArrayAdapter(Context context, CustomData[] values) {
            super(context, R.layout.custom_data_view, values);
            mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                // Inflate the view since it does not exist
                convertView = mInflater.inflate(R.layout.custom_data_view, parent, false);

                // Create and save off the holder in the tag so we get quick access to inner fields
                // This must be done for performance reasons
                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            // Populate the text
            holder.textView.setText(getItem(position).getText());

            // Set the color
            convertView.setBackgroundColor(getItem(position).getBackgroundColor());

            return convertView;
        }

        /**
         * View holder for the views we need access to
         */
        private class Holder {
            public TextView textView;
        }
    }
}
