package com.ikue.japanesedictionary.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikue.japanesedictionary.database.DictionaryDatabase;
import com.ikue.japanesedictionary.models.DictionarySearchResultItem;

import java.lang.Character.UnicodeBlock;

/**
 * Created by luke_c on 08/02/2017.
 */

public class SearchResultFragment extends Fragment {
    private static final String ARG_SEARCH_TERM = "SEARCH_TERM";

    private static DictionaryDatabase helper;
    private static AsyncTask task;
    private static DictionarySearchResultItem searchResults;

    private static final int KANA_TYPE = 0;
    private static final int ROMAJI_TYPE = 1;
    private static final int KANJI_TYPE = 2;
    private static final int ENGLISH_TYPE = 3;

    public static SearchResultFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_TERM, query);

        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain the fragment so rotation does not repeatedly fire off new AsyncTasks
        setRetainInstance(true);

        // Get a database on startup.
        helper = DictionaryDatabase.getInstance(this.getActivity());

        String searchQuery = getArguments().getString(ARG_SEARCH_TERM, null);
        int searchType = getSearchType(searchQuery);

        task = new GetSearchResultsTask(searchQuery, searchType).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        // Cancel the AsyncTask if it is running when Activity is about to close
        // cancel(false) is safer and doesn't force an instant cancellation
        if(task !=null) {
            task.cancel(false);
        }

        // Close the SQLiteHelper instance
        helper.close();
        super.onDestroy();
    }

    private void updateViews() {

    }

    // TODO: Support for Romaji
    // Get what type the search term is. Can either be Kanji, Kana, or English.
    private int getSearchType(String searchTerm) {
        boolean containsKana = false;

        // Check every character of the string
        for(char c : searchTerm.toCharArray()) {
            // If the current character is a Kanji (or Chinese/Korean character)
            if(UnicodeBlock.of(c) == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                // Once we find a single Kanji character, we know to search the Kanji Element
                return KANJI_TYPE;
                // If the current character is a Hiragana or Katakana character
            } else if(UnicodeBlock.of(c) == UnicodeBlock.HIRAGANA || UnicodeBlock.of(c) == UnicodeBlock.KATAKANA) {
                containsKana = true;
            }
        }
        // If by the end there was no Kana characters, search in English
        return containsKana ? KANA_TYPE : ENGLISH_TYPE;
    }

    // The types specified here are the input data type, the progress type, and the result type
    private class GetSearchResultsTask extends AsyncTask<Void, Void, DictionarySearchResultItem> {

        private String searchQuery;
        private int type;

        public GetSearchResultsTask(String searchQuery, int type) {
            this.searchQuery = searchQuery;
            this.type = type;
        }

        @Override
        protected DictionarySearchResultItem doInBackground(Void... params) {
            switch (type) {
                case KANA_TYPE:
                    return helper.searchByKana(searchQuery);
                case ROMAJI_TYPE:
                    break;
                case KANJI_TYPE:
                    return helper.searchByKanji(searchQuery);
                case ENGLISH_TYPE:
                    return helper.searchByEnglish(searchQuery);
                default:
                    break;
            }
            return new DictionarySearchResultItem();
        }

        @Override
        protected void onPostExecute(DictionarySearchResultItem result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            searchResults = result;
            updateViews();
        }
    }
}
