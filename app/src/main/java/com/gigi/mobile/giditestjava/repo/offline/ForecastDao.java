package com.gigi.mobile.giditestjava.repo.offline;

import com.gigi.mobile.giditestjava.models.Response;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ForecastDao {
    @Query("select * from forecasts where id=1")
    Single<Response> getDataById();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertForeCastData(Response data);
}
