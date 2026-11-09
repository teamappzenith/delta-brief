package in.market.deltabrief.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards ORDER BY timestamp DESC")
    LiveData<List<CardEntity>> getAllCards();

    @Query("SELECT * FROM cards WHERE category = :category ORDER BY timestamp DESC")
    LiveData<List<CardEntity>> getCardsByCategory(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCards(List<CardEntity> cards);

    @Query("UPDATE cards SET isBookmarked = :isBookmarked WHERE cardId = :cardId")
    void updateBookmarkStatus(String cardId, boolean isBookmarked);

    @Query("DELETE FROM cards")
    void clearAllCards();
}
