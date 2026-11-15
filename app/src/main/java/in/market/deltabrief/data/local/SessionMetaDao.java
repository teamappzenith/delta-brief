package in.market.deltabrief.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface SessionMetaDao {
    @Query("SELECT * FROM session_meta")
    LiveData<List<SessionMetaEntity>> getAllSessions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSessions(List<SessionMetaEntity> sessions);
}
