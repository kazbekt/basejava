import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    int storageSize = 0;

    void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    void save(Resume r) {
        if (storageSize == storage.length) {
            System.out.println("Хранилище заполнено. Добавление резюме невозможно");
            return;
        }
        if (storageSize != 0) {
            for (int i = 0; i < storageSize; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Резюме ранее добавлено в хранилище");
                    return;
                }
            }
        }
        storage[storageSize++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Резюме " + uuid + " в хранилище не содержится");
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[storageSize - 1];
                storage[storageSize - 1] = null;
                System.out.println("Резюме " + uuid + " удалено из хранилища");
                storageSize--;
                return;
            }
        }
        System.out.println("Резюме " + uuid + " в хранилище не содержится");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, storageSize);
    }

    int size() {
        return storageSize;
    }
}
