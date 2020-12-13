package ua.notky.notes.util.dagger.components;

import javax.inject.Singleton;

import dagger.Subcomponent;
import ua.notky.notes.api.tasks.LoadTask;
import ua.notky.notes.dao.lite.note.NoteRepositoryLiteImp;
import ua.notky.notes.gui.MainActivity;
import ua.notky.notes.gui.fragment.EditorNoteFragment;
import ua.notky.notes.gui.fragment.NotesFragment;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenterImp;
import ua.notky.notes.gui.presenter.fragment.notes.NotesPresenterImp;
import ua.notky.notes.gui.presenter.main.MainPresenterImp;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.service.NoteServiceImp;

@Singleton
@Subcomponent(modules = {})
public interface AppComponent {
    void injectNoteRepository(NoteRepositoryLiteImp repository);
    void injectNoteService(NoteServiceImp service);
    void injectLoadTask(LoadTask loadTask);

    void injectMainActivity(MainActivity activity);
    void injectMainPresenter(MainPresenterImp presenter);

    void injectEditorNotePresenter(EditorNotePresenterImp presenter);
    void injectEditorNoteFragment(EditorNoteFragment fragment);
    void injectNoteAdapter(NoteAdapter adapter);

    void injectNotesPresenter(NotesPresenterImp presenter);
    void injectNotesFragment(NotesFragment fragment);
}
