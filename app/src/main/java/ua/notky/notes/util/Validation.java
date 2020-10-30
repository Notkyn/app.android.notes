package ua.notky.notes.util;

import ua.notky.notes.model.AbstractBaseEntity;
import ua.notky.notes.util.exception.IllegalEntityException;
import ua.notky.notes.util.exception.NotFoundDataException;

public class Validation {
    public static <T extends AbstractBaseEntity> void checkNotNull(T object){
        if (object == null)
            throw new AssertionError("Object cannot be null");
    }

    public static <T extends AbstractBaseEntity> void checkNew(T entity) {
        if (!entity.isNew()) {
            throw new IllegalEntityException(entity + " must be new (id=null)");
        }
    }

    public static <T extends AbstractBaseEntity> void checkUpdated(T entity) {
        if (entity.isNew()) {
            throw new IllegalEntityException(entity + " must have id");
        }
    }

    public static <T> T checkNotFoundWithId(T object, long id) {
        return checkNotFound(object, "id=" + id);
    }

    private static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    private static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundDataException(arg);
        }
    }
}
