package ua.notky.notes.listener;

import ua.notky.notes.util.enums.TextState;

public interface OnChangeTextListener {
    void changedText(TextState textState);
}
