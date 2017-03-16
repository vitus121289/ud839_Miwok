/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    ArrayList<Word> words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        // Creates an ArrayList of words with a custom class of Word.
        words = new ArrayList<Word>();

        // Populates the ArrayList with english and miwok word translation.
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes I'm coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("i'm coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let's go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        // Creates an array adapter with a preset layout from android and with our words array list as its data source.
        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_phrases);

        // Finds a list view object from the view hierarchy.
        ListView listView = (ListView) findViewById(R.id.list);

        // Sets the array adapter to be displayed on the list view.
        listView.setAdapter(itemsAdapter);

        // Sets a click listener for the list view.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // When a view is clicked an audio is played corresponding to the word that was clicked-on.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MediaPlayer mediaPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(i).getAudioResourceId());
                mediaPlayer.start();
            }
        });
    }
}
