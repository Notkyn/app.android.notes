package ua.notky.notes.tools.dagger.modules;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenter;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenterImp;
import ua.notky.notes.gui.presenter.fragment.notes.NotesPresenter;
import ua.notky.notes.gui.presenter.fragment.notes.NotesPresenterImp;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.gui.recycler.NoteDiffUtilCallback;
import ua.notky.notes.tools.dagger.scopes.FragmentScope;

@Module
public class FragmentModule {

    @FragmentScope
    @Provides
    public EditorNotePresenter getEditorNotePresenter(){
        return new EditorNotePresenterImp();
    }

    @FragmentScope
    @Provides
    public NotesPresenter getNotesPresenter(){
        return new NotesPresenterImp();
    }

    @FragmentScope
    @Provides
    public NoteAdapter getAdapter(){
        return new NoteAdapter();
    }

    @FragmentScope
    @Provides
    public NoteDiffUtilCallback getNoteDiffUtilCallback(){
        return new NoteDiffUtilCallback();
    }
}
