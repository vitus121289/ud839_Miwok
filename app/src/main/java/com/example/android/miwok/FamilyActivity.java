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

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManagerFamily;

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
        words.add(new Word("Father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("Younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("Older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("Younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("Grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("Grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        // Creates an array adapter with a preset layout from android and with our words array list as its data source.
        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_family);

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
                mAudioManagerFamily = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                // Requests for an audio focus.
                int audioFocusRequestResult = mAudioManagerFamily.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // If audio focus is granted instantiate a MediaPlayer object then play the audio file.
                if (audioFocusRequestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, words.get(i).getAudioResourceId());
                    mMediaPlayer.start();

                    // Sets a completion listener for mMediaPlayer object
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

            //Releases the audio focus.
            mAudioManagerFamily.abandonAudioFocus(audioFocusChangeListener);
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
