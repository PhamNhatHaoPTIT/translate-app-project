package com.example.apaar97.translate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class HistoryAdapter extends ArrayAdapter {
    Context context;
    int layoutId;
    ArrayList<Word> listWord;
    private TextToSpeech mTextToSpeech;
    WordDB wordDB;


    public HistoryAdapter(@NonNull Context context, int layoutId, ArrayList<Word> listWord, TextToSpeech mTextToSpeech) {
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
        TextView fromText = (TextView) convertView.findViewById(R.id.fromText);
        TextView toText = (TextView) convertView.findViewById(R.id.toText);
        fromText.setText(listWord.get(position).getWordFrom());
        toText.setText(listWord.get(position).getWordTo());
        final ImageView favorite = (ImageView) convertView.findViewById(R.id.favorite_img);
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
        ImageView speak = (ImageView) convertView.findViewById(R.id.speak_img);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut(listWord.get(position));
            }
        });
        ImageView remove = (ImageView) convertView.findViewById(R.id.remove_img);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault);
                builder.setTitle("Are you want delete this word exit ?");
                builder.setIcon(android.R.drawable.ic_dialog_dialer);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                        wordDB.delete(listWord.get(position));
                        listWord.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        return  convertView;
    }

    private void speakOut(Word word){
        int result = mTextToSpeech.setLanguage(new Locale(word.getCodeTo()));
        if(result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(context, "Not supported language",Toast.LENGTH_SHORT).show();
        } else {
            String textMessage = word.getWordTo();
            mTextToSpeech.speak(textMessage, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}
