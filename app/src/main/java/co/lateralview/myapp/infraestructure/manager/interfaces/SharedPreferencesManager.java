package co.lateralview.myapp.infraestructure.manager.interfaces;

import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface SharedPreferencesManager
{
    Completable save(String key, boolean value);

    Completable save(String key, String value);

    Completable save(String key, int value);

    Single<Boolean> getBoolean(String key);

    Single<Boolean> getBoolean(String key, boolean defaultValue);

    Single<String> getString(String key);

    Single<String> getString(String key, String defaultValue);

    Single<Integer> getInt(String key);

    Single<Integer> getInt(String key, int defaultValue);

    <T> Completable save(String key, T model);

    <T> Single get(String key, Class<T> type);

    <T> Single get(String key, Type type);

    Completable clear();

    Completable remove(String key);
}
