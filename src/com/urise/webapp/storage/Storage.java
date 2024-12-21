package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {
    //общий для любых хранилищ
    void clear();

    // общий для любых хранилищ, но РАЗНЫМ будет getIndex
    // который вызывается этим методом
    void update(Resume r);

    // разный, т.к. в обычном хранилище добавление в конец, а в сортированном в какое-то место
    void save(Resume r); //

    // общий для любых хранилищ, потому что РАЗНЫМ будет способ поиска
    // целевого резюме внутри метода update (getIndex)
    Resume get(String uuid);

    // разный, т.к. ArrayStorage не требует параллельного сдвига
    void delete(String uuid);

    //общий для любых хранилищ
    Resume[] getAll();

    //общий для любых хранилищ
    int size();

}
