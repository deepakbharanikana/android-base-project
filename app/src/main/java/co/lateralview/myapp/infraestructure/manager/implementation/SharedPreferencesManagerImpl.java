package co.lateralview.myapp.infraestructure.manager.implementation;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Type;

import co.lateralview.myapp.infraestructure.manager.interfaces.ParserManager;
import co.lateralview.myapp.infraestructure.manager.interfaces.SharedPreferencesManager;

public class SharedPreferencesManagerImpl implements SharedPreferencesManager
{
    public static final String DEFAULT_FILE_NAME = "co.lateralview.myapp.sharedPreferences";
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

    public void save(String key, boolean value)
    {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void save(String key, String value)
    {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public void save(String key, int value)
    {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public boolean saveBlocking(String key, boolean value)
    {
        return mSharedPreferences.edit().putBoolean(key, value).commit();

    }

    @Override
    public boolean saveBlocking(String key, String value)
    {
        return mSharedPreferences.edit().putString(key, value).commit();
    }

    @Override
    public boolean saveBlocking(String key, int value)
    {
        return mSharedPreferences.edit().putInt(key, value).commit();

    }

    public boolean getBoolean(String key)
    {
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue)
    {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public String getString(String key)
    {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue)
    {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key)
    {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue)
    {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public <T> void save(String key, T model)
    {
        mSharedPreferences.edit().putString(key, mParserManager.toJson(model)).apply();
    }

    @Override
    public <T> boolean saveBlocking(String key, T model)
    {
        return mSharedPreferences.edit().putString(key, mParserManager.toJson(model)).commit();
    }

    public <T> T get(String key, Class<T> type)
    {
        String json = getString(key);
        return json != "" ? mParserManager.fromJson(json, type) : null;
    }

    @Override
    public <T> T get(String key, Type type)
    {
        String json = getString(key);
        return json != "" ? (T) mParserManager.fromJson(json, type) : null;
    }

    @Override
    public void clear()
    {
        mSharedPreferences.edit().clear().apply();
    }

    @Override
    public boolean clearBlocking()
    {
        return mSharedPreferences.edit().clear().commit();
    }

    @Override
    public void remove(String key)
    {
        mSharedPreferences.edit().remove(key).apply();
    }

    @Override
    public boolean removeBlocking(String key)
    {
        return mSharedPreferences.edit().remove(key).commit();
    }
}
