package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vincent on 3/15/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Context context, ArrayList<Word> word){
        super(context, 0, word);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        // Checks if the current view is being reused if not inflate the view.
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Gets the item located at this position.
        Word currentWord = getItem(position);

        // Finds the text view in the list_item.xml with the ID default_text_view.
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        // Sets the default word translation to the default_text_view.
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Finds the text view in the list_item.xml with the ID miwok_text_view.
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        // Sets the miwok word translation to the miwok_text_view.
        miwokTextView.setText(currentWord.getMiwokTranslation());

        return listItemView;
    }
}
