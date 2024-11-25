import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        storage = sortedStorage(storage);
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(r.uuid)) {
                System.out.println("Резюме ранее добавлено в хранилище");
                break;
            }
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume != null && resume.toString().equals(uuid)) {
                return resume;
            }
        }
        System.out.println("Резюме " + uuid + " в хранилище не содержится");
        return null;
    }

    void delete(String uuid) {
        boolean resumeDeleted = false;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                resumeDeleted = true;
                System.out.println("Резюме " + uuid + " удалено из хранилища");
            }
            if (!resumeDeleted && i == storage.length - 1)
                System.out.println("Резюме " + uuid + " в хранилище не содержится");
        }
        storage = sortedStorage(storage);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        storage = sortedStorage(storage);
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int resumeSize = 0;
        storage = sortedStorage(storage);
        for (Resume resume : storage) {
            if (resume != null)
                resumeSize++;
        }
        return resumeSize;
    }

    Resume[] sortedStorage(Resume[] storage) {
        Resume[] sortedStorage = new Resume[10000];
        int resumeNumber = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                sortedStorage[resumeNumber++] = resume;
            }
        }
        return sortedStorage;
    }
}
