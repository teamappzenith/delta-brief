package in.market.deltabrief.util;

import in.market.deltabrief.data.local.CardEntity;
import java.util.ArrayList;
import java.util.List;

public class SampleDataProvider {

    public static List<CardEntity> getSampleCards() {
        List<CardEntity> cards = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        cards.add(new CardEntity(
            "card_001",
            "Pre-Market Equity Briefing: Nifty & BankNifty Open Strong",
            "Global cues are positive with SGX Nifty indicating a gap-up opening. FIIs turned net buyers yesterday.",
            "Equity",
            currentTime - 3600000L * 4,
            false
        ));

        cards.add(new CardEntity(
            "card_002",
            "11 AM Options Strategy: High IV in Weekly Expiry",
            "Implied Volatility (IV) has spiked across ATM strikes. Straddle sellers should maintain strict stop-losses as directional breakout is expected.",
            "Options",
            currentTime - 3600000L * 3,
            true
        ));

        cards.add(new CardEntity(
            "card_003",
            "Mid-Day Macro Update: RBI Holds Repo Rate Unchanged",
            "In line with expectations, RBI MPC maintains repo rate at 6.5% with a focused withdrawal of accommodation stance.",
            "Macro",
            currentTime - 3600000L * 2,
            false
        ));

        cards.add(new CardEntity(
            "card_004",
            "Post-Market Wrap: IT & Auto Drive Indices Higher",
            "Benchmark indices closed near the day's high led by heavy buying in IT majors and select auto stocks. Advance-decline ratio favored bulls.",
            "Equity",
            currentTime,
            false
        ));

        return cards;
    }
}
