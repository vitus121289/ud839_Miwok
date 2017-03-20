package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManagerColors;

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

    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        // Creates an ArrayList of words with a custom class of Word.
        final ArrayList<Word> words = new ArrayList<Word>();

        // Populates the ArrayList with english and miwok word translation.
        words.add(new Word("Red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("Green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("Brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("Gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("Black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("White", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("Dusty yellow", "topiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("Mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // Creates an array adapter with a preset layout from android and with our words array list as its data source.
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        // Finds a list view object from the view hierarchy.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

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
                mAudioManagerColors = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

                // Requests for an audio focus.
                int audioFocusRequestResult = mAudioManagerColors.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // If audio focus is granted instantiate a MediaPlayer object then play the audio file.
                if (audioFocusRequestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), words.get(i).getAudioResourceId());
                    mMediaPlayer.start();

                    // Sets a completion listener for mMediaPlayer object
                    mMediaPlayer.setOnCompletionListener(mMediaPlayerOnComplete);
                }
            }
        });

        return rootView;
    }

    /**
     * Callback method when the app is on the stopped state of its lifecycle.
     * <p>
     * Overridden to call the releaseMediaPlayer method to release resources from the media player when the app
     * enters the stopped state.
     */

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
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

            // Releases auto focus.
            mAudioManagerColors.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
