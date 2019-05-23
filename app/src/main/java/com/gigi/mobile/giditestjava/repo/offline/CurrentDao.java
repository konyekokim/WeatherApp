package com.gigi.mobile.giditestjava.repo.offline;

import com.gigi.mobile.giditestjava.models.CurrentResponse;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface CurrentDao {

    @Query("select * from currentWeathers where ids=1")
    Single<CurrentResponse> getCurrent();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCurrent(CurrentResponse response);

}
