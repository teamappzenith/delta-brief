package in.market.deltabrief.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MarketSnapshotDao {
    @Query("SELECT * FROM market_snapshots")
    LiveData<List<MarketSnapshotEntity>> getAllMarketSnapshots();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSnapshots(List<MarketSnapshotEntity> snapshots);
}
