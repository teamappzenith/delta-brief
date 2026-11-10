package in.market.deltabrief.model;

public class HubItem {

    public static final int TYPE_SESSION_HUB = 1;
    public static final int TYPE_SECTION_HEADER = 2;
    public static final int TYPE_TOOL_CARD = 3;

    private int viewType;
    private String title;
    private String subtitle;
    private String description;
    private String category;
    private int iconResId;
    private int badgeCount;
    private String colorHex;

    public HubItem(int viewType, String title, String subtitle, String description, String category, int iconResId, int badgeCount, String colorHex) {
        this.viewType = viewType;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.iconResId = iconResId;
        this.badgeCount = badgeCount;
        this.colorHex = colorHex;
    }

    public int getViewType() { return viewType; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public int getIconResId() { return iconResId; }
    public int getBadgeCount() { return badgeCount; }
    public String getColorHex() { return colorHex; }
}
