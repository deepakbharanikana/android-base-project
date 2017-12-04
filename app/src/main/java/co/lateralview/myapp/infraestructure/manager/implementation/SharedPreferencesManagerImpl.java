package co.lateralview.myapp.infraestructure.manager.implementation;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Type;

import co.lateralview.myapp.infraestructure.manager.interfaces.ParserManager;
import co.lateralview.myapp.infraestructure.manager.interfaces.SharedPreferencesManager;
import co.lateralview.myapp.infraestructure.util.Optional;
import io.reactivex.Completable;
import io.reactivex.Single;

public class SharedPreferencesManagerImpl implements SharedPreferencesManager
{
    public static final String DEFAULT_FILE_NAME = "co.lateralview.attender.sharedPreferences";
    private SharedPreferences mSharedPreferences;
    private ParserManager mParserManager;

    public SharedPreferencesManagerImpl(Context context, ParserManager parserManager,
            String fileName)
    {
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        mParserManager = parserManager;
    }

    public SharedPreferencesManagerImpl(Context context, ParserManager parserManager)
    {
        this(context, parserManager, DEFAULT_FILE_NAME);
    }

    @Override
    public Completable save(String key, boolean value)
    {
        return Completable.create(e -> mSharedPreferences.edit().putBoolean(key, value).apply());
    }

    @Override
    public Completable save(String key, String value)
    {
        return Completable.create(e -> mSharedPreferences.edit().putString(key, value).apply());
    }

    @Override
    public Completable save(String key, int value)
    {
        return Completable.create(e -> mSharedPreferences.edit().putInt(key, value).apply());
    }

    @Override
    public Single<Boolean> getBoolean(String key)
    {
        return getBoolean(key, false);
    }

    @Override
    public Single<Boolean> getBoolean(String key, boolean defaultValue)
    {
        return Single.just(mSharedPreferences.getBoolean(key, defaultValue));
    }

    @Override
    public Single<String> getString(String key)
    {
        return getString(key, "");
    }

    @Override
    public Single<String> getString(String key, String defaultValue)
    {
        return Single.just(mSharedPreferences.getString(key, defaultValue));
    }

    @Override
    public Single<Integer> getInt(String key)
    {
        return getInt(key, -1);
    }

    @Override
    public Single<Integer> getInt(String key, int defaultValue)
    {
        return Single.just(mSharedPreferences.getInt(key, defaultValue));
    }

    @Override
    public <T> Completable save(String key, T model)
    {
        return Completable.create(e ->
                mSharedPreferences.edit().putString(key, mParserManager.toJson(model)).apply());
    }

    @Override
    public <T> Single get(String key, Class<T> type)
    {
        return getString(key).map(json -> !json.equals("") ?
                mParserManager.fromJson(json, type) : new Optional<T>(null));
    }

    @Override
    public <T> Single get(String key, Type type)
    {
        return getString(key).map(json -> !json.equals("") ?
                (T) mParserManager.fromJson(json, type) : new Optional<T>(null));
    }

    @Override
    public Completable clear()
    {
        return Completable.create(e -> mSharedPreferences.edit().clear().apply());
    }

    @Override
    public Completable remove(String key)
    {
        return Completable.create(e -> mSharedPreferences.edit().remove(key).apply());
    }
}
