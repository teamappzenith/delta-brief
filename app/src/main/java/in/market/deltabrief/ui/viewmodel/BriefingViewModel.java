package in.market.deltabrief.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import in.market.deltabrief.data.local.AppDatabase;
import in.market.deltabrief.data.local.CardEntity;
import in.market.deltabrief.data.repository.BriefingRepository;

import java.util.List;

public class BriefingViewModel extends AndroidViewModel {

    private final BriefingRepository repository;
    private final LiveData<List<CardEntity>> allCards;

    public BriefingViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        repository = new BriefingRepository(database.cardDao());
        allCards = repository.getAllCards();
    }

    public LiveData<List<CardEntity>> getAllCards() {
        return allCards;
    }

    public LiveData<List<CardEntity>> getCardsByCategory(String category) {
        return repository.getCardsByCategory(category);
    }

    public void insertCards(List<CardEntity> cards) {
        repository.insertCards(cards);
    }

    public void updateBookmarkStatus(String cardId, boolean isBookmarked) {
        repository.updateBookmarkStatus(cardId, isBookmarked);
    }

    public void clearAllCards() {
        repository.clearAllCards();
    }
}
