package ua.notky.notes.tools.dagger.modules;

import androidx.paging.DataSource;
import dagger.Module;
import dagger.Provides;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenter;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenterImp;
import ua.notky.notes.gui.presenter.fragment.notes.NotesPresenter;
import ua.notky.notes.gui.presenter.fragment.notes.NotesPresenterImp;
import ua.notky.notes.gui.recycler.NoteItemDiffUtilCallback;
import ua.notky.notes.gui.recycler.NotePagedAdapter;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
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
    public NotePagedAdapter getPageAdapter(){
        return new NotePagedAdapter();
    }

    @FragmentScope
    @Provides
    public NoteItemDiffUtilCallback getNoteItemDiffUtilCallback() {return new NoteItemDiffUtilCallback();}

    @FragmentScope
    @Provides
    public DataSource.Factory<Integer, Note> getFactoryDataSource(NoteService service){
        return service.getFactoryDataSource();
    }
}
