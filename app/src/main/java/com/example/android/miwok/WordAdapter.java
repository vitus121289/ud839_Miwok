package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vincent on 3/15/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    /**
     * Constructor of WordAdapter.
     *
     * @param colorResourceId resource ID for the background color of the texts.
     * @param context         context of the current activity the object is in.
     * @param word            an array list of words.
     */
    public WordAdapter(Context context, ArrayList<Word> word, int colorResourceId) {

        // Method to call the superclass' constructor.
        super(context, 0, word);

        mColorResourceId = colorResourceId;
    }

    /**
     * Provides a view for and AdapterView (ListView, GridView, etc.).
     *
     * @param position    the AdapterView position that is requesting a view.
     * @param convertView the recycled view to populate
     * @param parent      the parent ViewGroup that is used for inflation.
     * @return the View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        // Checks if the current view is being reused, if not, inflate the view.
        if (listItemView == null) {
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

        // Finds the image view in the list_item.xml with the ID words_image_view.
        ImageView wordsImageView = (ImageView) listItemView.findViewById(R.id.words_image_view);

        // Sets the image view to the words_image_view.
        wordsImageView.setImageResource(currentWord.getImageResourceId());

        // Finds the linear layout with the ID words_parent_view.
        LinearLayout wordsParentLayout = (LinearLayout) listItemView.findViewById(R.id.words_parent_layout);

        // Sets the background color of the words_parent_layout to the color provided by the resource ID.
        wordsParentLayout.setBackgroundResource(getColorResourceId());

        return listItemView;
    }

    private int getColorResourceId() {

        return mColorResourceId;
    }
}
