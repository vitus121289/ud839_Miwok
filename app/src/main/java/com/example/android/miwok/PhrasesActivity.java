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

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManagerPhrases;

    // Creates a private interface implementation called mMediaPlayerOnComplete that calls the releaseMediaPlayer() method
    // when the audio file has finished playing.
    private MediaPlayer.OnCompletionListener mMediaPlayerOnComplete = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

            releaseMediaPlayer();
        }
    };

    /**
     * Creates an instance of the AudioManager.OnAudioFocusChangeListener called audioFocusChangeListener
     * and implements the callback method onAudioFocusChange.
     */
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                // AUDIOFOCUS_LOSS_TRANSIENT and AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK states shall be
                // handled similarly, in which they are both paused when audio focus is loss temporarily
                // then resumed from the beginning when audio focus is returned.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                // Release the resources used by the MediaPlayer when audio focus is loss
                // indefinitely.
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                // Plays the audio file when auto focus is gained.
                mMediaPlayer.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        // Enables up button on this activity.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Creates an ArrayList of words with a custom class of Word.
        final ArrayList<Word> words = new ArrayList<Word>();

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

                // Releases the resources being used by the media player before playing an audio file.
                releaseMediaPlayer();

                // Creates an AudioManager object called audioManagerNumbers.
                mAudioManagerPhrases = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                // Requests for an audio focus.
                int audioFocusRequestResult = mAudioManagerPhrases.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // If audio focus is granted instantiate a MediaPlayer object then play the audio file.
                if (audioFocusRequestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(i).getAudioResourceId());
                    mMediaPlayer.start();

                    // Sets a completion listener for mMediaPlayer object.
                    mMediaPlayer.setOnCompletionListener(mMediaPlayerOnComplete);
                }
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Releases the audio focus.
            mAudioManagerPhrases.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    /**
     * Callback method when the app is on the stopped state of its lifecycle.
     * <p>
     * Overridden to call the releaseMediaPlayer method to release resources from the media player when the app
     * enters the stopped state.
     */
    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }
}
