package com.gigi.mobile.giditestjava.repo.offline;

import com.gigi.mobile.giditestjava.models.CurrentResponse;
import com.gigi.mobile.giditestjava.models.Response;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Response.class, CurrentResponse.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ForecastDao getDataDao();
    public abstract CurrentDao getCurrentDao();
}
