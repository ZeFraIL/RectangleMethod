package zeev.fraiman.rectanglemethod;

import android.provider.BaseColumns;

public final class HistoryContract {

    private HistoryContract() {}

    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_CURVE_ID = "curve_id";
        public static final String COLUMN_NAME_CURVE_POINTS = "curve_points";
        public static final String COLUMN_NAME_METHOD = "method";
        public static final String COLUMN_NAME_RECTANGLE_COUNT = "rectangle_count";
        public static final String COLUMN_NAME_TOTAL_AREA = "total_area";
    }
}
