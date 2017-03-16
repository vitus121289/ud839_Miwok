package com.example.android.miwok;

/**
 * Created by Vincent on 3/14/2017.
 */

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;
    private int mAudioResourceId;

    /**
     * Constructor of Word class.
     *
     * @param defaultTranslation the original translation of the word.
     * @param miwokTranslation   the miwok translation of the word.
     * @param audioResourceId    the resource ID for the audio on how to pronounce the word.
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Constructor of Word class.
     *
     * @param defaultTranslation the original translation of the word.
     * @param miwokTranslation   the miwok translation of the word.
     * @param imageResourceId    the resource ID of the image that correspond to a specific word.
     * @param audioResourceId    the resource ID for the audio on how to pronounce the word.
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Method to get the default translation of a word.
     */
    public String getDefaultTranslation() {

        return mDefaultTranslation;
    }

    /**
     * Method to get the Miwok translation of a word.
     */
    public String getMiwokTranslation() {

        return mMiwokTranslation;
    }

    /**
     * Method to get the resource ID of an image.
     */
    public int getImageResourceId() {

        return mImageResourceId;
    }


    /**
     * Method to get the resource ID of an audio.
     */
    public int getAudioResourceId() {

        return mAudioResourceId;
    }
}
