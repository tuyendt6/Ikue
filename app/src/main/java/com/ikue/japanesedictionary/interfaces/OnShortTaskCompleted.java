package com.ikue.japanesedictionary.interfaces;

import com.ikue.japanesedictionary.models.DictionaryItem;

/**
 * Created by luke_c on 15/02/2017.
 */

public interface OnShortTaskCompleted {
    void onResult(DictionaryItem result);
}
