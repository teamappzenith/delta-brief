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
            "Pre-Market",
            "BULLISH",
            "Indian equity markets are primed for a strong opening following positive global cues and robust FII inflows. Asian indices are trading in the green, driven by tech sector rallies. Domestic institutional investors continue to maintain steady allocations, supporting market liquidity.",
            "• FII Net Buying: ₹1,850 Cr\n• SGX Nifty Trading +120 points premium\n• Crude oil prices stabilized around $78/bbl",
            "• Nifty Support: 22,200 | Resistance: 22,550\n• BankNifty Support: 47,800 | Resistance: 48,500",
            currentTime - 3600000L * 4,
            false
        ));

        cards.add(new CardEntity(
            "card_002",
            "11 AM Options Strategy: High IV in Weekly Expiry",
            "Implied Volatility (IV) has spiked across ATM strikes. Straddle sellers should maintain strict stop-losses.",
            "Stabilized",
            "NEUTRAL",
            "As we approach mid-morning trading, Implied Volatility (IV) across weekly options contracts has expanded significantly. The market is consolidating near key pivot zones, creating favorable conditions for range-bound non-directional option strategies while keeping tight risk management.",
            "• Spike in ATM IVs to 16.5%\n• Max Call OI concentrated at 22,500\n• Max Put OI concentrated at 22,200",
            "• Preferred Strategy: Short Iron Condor\n• Stop-Loss Target: 25% on combined premium",
            currentTime - 3600000L * 3,
            true
        ));

        cards.add(new CardEntity(
            "card_003",
            "Mid-Day Macro Update: RBI Holds Repo Rate Unchanged",
            "In line with expectations, RBI MPC maintains repo rate at 6.5% with a focused stance on inflation target.",
            "Mid-Day",
            "BEARISH",
            "The Reserve Bank of India Monetary Policy Committee has unanimously voted to keep the policy Repo Rate unchanged at 6.50%. The central bank reiterated its commitment to aligning inflation with the target 4% level while remaining watchful of food price dynamics.",
            "• Policy Repo Rate unchanged at 6.50%\n• FY25 GDP Growth projected at 7.0%\n• CPI Inflation projected at 4.5%",
            "• Rate-sensitive sectors (Realty, Auto, Banking) experiencing minor profit booking\n• Key Support: Banking index testing 50-DMA",
            currentTime - 3600000L * 2,
            false
        ));

        cards.add(new CardEntity(
            "card_004",
            "Post-Market Wrap: IT & Auto Drive Indices Higher",
            "Benchmark indices closed near the day's high led by heavy buying in IT majors and select auto stocks.",
            "Post-Market",
            "BULLISH",
            "Indian benchmark indices ended significantly higher today, extending their winning streak. IT and Automotive heavyweights led the rally, benefiting from improved earnings expectations and favorable foreign fund inflows. Market breadth favored advances 3:1.",
            "• Nifty 50 closed at 22,480 (+0.85%)\n• Sector Top Gainers: Nifty IT (+1.8%), Nifty Auto (+1.4%)\n• Total Market Turnover: ₹1.12 Lakh Crore",
            "• Immediate Technical Outlook: Bullish continuation pattern intact\n• Primary Target: 22,650 zone for upcoming session",
            currentTime,
            false
        ));

        return cards;
    }
}
