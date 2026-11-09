package in.market.deltabrief.data.repository;

import androidx.lifecycle.LiveData;
import in.market.deltabrief.data.local.CardDao;
import in.market.deltabrief.data.local.CardEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BriefingRepository {

    private final CardDao cardDao;
    private final ExecutorService executorService;

    public BriefingRepository(CardDao cardDao) {
        this.cardDao = cardDao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<CardEntity>> getAllCards() {
        return cardDao.getAllCards();
    }

    public LiveData<List<CardEntity>> getCardsByCategory(String category) {
        return cardDao.getCardsByCategory(category);
    }

    public void insertCards(List<CardEntity> cards) {
        executorService.execute(() -> cardDao.insertCards(cards));
    }

    public void updateBookmarkStatus(String cardId, boolean isBookmarked) {
        executorService.execute(() -> cardDao.updateBookmarkStatus(cardId, isBookmarked));
    }

    public void clearAllCards() {
        executorService.execute(cardDao::clearAllCards);
    }
}
