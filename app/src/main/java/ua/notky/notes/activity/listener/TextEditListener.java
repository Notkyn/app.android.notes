package ua.notky.notes.activity.listener;

import android.text.Editable;
import android.text.TextWatcher;

import ua.notky.notes.util.enums.TextState;

public class TextEditListener implements TextWatcher {
    private final OnChangeTextListener onChangeTextListener;
    private String text;

    public TextEditListener(OnChangeTextListener onChangeTextListener, String text) {
        this.onChangeTextListener = onChangeTextListener;
        this.text = text;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(text.contentEquals(charSequence)){
            onChangeTextListener.changedText(TextState.CURRENT);
        } else {
            onChangeTextListener.changedText(TextState.CHANGED);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setText(String text) {
        this.text = text;
    }
}
