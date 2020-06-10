package com.example.apaar97.translate;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.apaar97.translate.GlobalVars.BASE_REQ_URL;

public class FavoriteAdapter extends ArrayAdapter {
    Context context;
    int layoutId;
    ArrayList<Word> listWord;
    private TextToSpeech mTextToSpeech;                     //    Text to Speech Engine
    WordDB wordDB;


    public FavoriteAdapter(@NonNull Context context, int layoutId, ArrayList<Word> listWord, TextToSpeech mTextToSpeech) {
        super(context, layoutId);
        this.context = context;
        this.layoutId = layoutId;
        this.listWord = listWord;
        this.mTextToSpeech = mTextToSpeech;
    }

    @Override
    public int getCount() {
        return listWord.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater.from(context));
        convertView = inflater.inflate(layoutId, null);
        TextView fromText = (TextView) convertView.findViewById(R.id.f_fromText);
        TextView toText = (TextView) convertView.findViewById(R.id.f_toText);
        fromText.setText(listWord.get(position).getWordFrom());
        toText.setText(listWord.get(position).getWordTo());
        final ImageView favorite = (ImageView) convertView.findViewById(R.id.f_favorite_img);
        if(listWord.get(position).getIsFavorite() == 1) {
            favorite.setImageResource(R.drawable.ic_favorite);
        }
        wordDB = new WordDB(context);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listWord.get(position).getIsFavorite() == 0) {
                    favorite.setImageResource(R.drawable.ic_favorite);
                    listWord.get(position).setIsFavorite(1);
                    wordDB.change(listWord.get(position));
                    Toast.makeText(context, "Added to your favorite", Toast.LENGTH_SHORT).show();
                } else {
                    listWord.get(position).setIsFavorite(0);
                    wordDB.change(listWord.get(position));
                    favorite.setImageResource(R.drawable.ic_favorite_none);
                    Toast.makeText(context, "Removed from your favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView speak = (ImageView) convertView.findViewById(R.id.f_speak_img);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut(listWord.get(position));
            }
        });
        return  convertView;
    }

    private void speakOut(Word word){
        int result = mTextToSpeech.setLanguage(new Locale(word.getCodeTo()));
        if(result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(getContext(), "Not supported language",Toast.LENGTH_SHORT).show();
        } else {
            String textMessage = word.getWordTo();
            mTextToSpeech.speak(textMessage, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


}
