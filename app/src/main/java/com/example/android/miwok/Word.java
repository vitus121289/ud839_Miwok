package com.example.android.miwok;

/**
 * Created by Vincent on 3/14/2017.
 */

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;

    /**
     * Constructor of Word class.
     *
     * @param defaultTranslation the original translation of the word.
     * @param miwokTranslation   the miwok translation of the word.
     */
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    /**
     * Constructor of Word class.
     *
     * @param defaultTranslation the original translation of the word.
     * @param miwokTranslation   the miwok translation of the word.
     * @param imageResourceId    the resource ID of the image that correspond to a specific word.
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
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
}
