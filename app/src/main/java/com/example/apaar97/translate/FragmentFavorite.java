package com.example.apaar97.translate;

import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentFavorite extends Fragment implements TextToSpeech.OnInitListener {

    ArrayList<Word> listWord;
    WordDB wordDB;
    ListView listFavorite;
    TextToSpeech mTextToSpeech;

    private void loadListWord() {
        listWord = new ArrayList<>();
        wordDB = new WordDB(getActivity().getBaseContext());
        Cursor cursor = wordDB.getAllWord();
        if(cursor != null) {
            while (cursor.moveToNext()) {
                Word word = new Word();
                word.setId(cursor.getInt(0));
                word.setCodeFrom(cursor.getString(1));
                word.setCodeTo(cursor.getString(2));
                word.setWordFrom(cursor.getString(3));
                word.setWordTo(cursor.getString(4));
                word.setIsFavorite(cursor.getInt(5));
                if(word.getIsDelete() == 0 && word.getIsFavorite() == 1) {
                    listWord.add(word);
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        listFavorite = (ListView) view.findViewById(R.id.listFavorite);
        loadListWord();
        mTextToSpeech = new TextToSpeech(getActivity().getBaseContext(), this);
        final FavoriteAdapter adapter = new FavoriteAdapter(getActivity(), R.layout.favorite_layout, listWord, mTextToSpeech);
        listFavorite.setAdapter(adapter);
        return view;
    }

    @Override
    public void onInit(int status) {
        Log.e("Inside----->", "onInit");
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(new Locale("en"));
            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(getActivity().getBaseContext(), getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getActivity().getBaseContext(), getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
            mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.e("Inside","OnStart");
                }
                @Override
                public void onDone(String utteranceId) {
                }
                @Override
                public void onError(String utteranceId) {
                }
            });
        } else {
            Log.e("TTS init fail","TTS Initilization Failed");
        }
    }

}
