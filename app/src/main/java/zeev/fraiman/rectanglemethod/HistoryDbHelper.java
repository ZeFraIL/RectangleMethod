package zeev.fraiman.rectanglemethod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "History.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HistoryContract.HistoryEntry.TABLE_NAME + " (" +
                    HistoryContract.HistoryEntry._ID + " INTEGER PRIMARY KEY,"
                    + HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_ID + " TEXT,"
                    + HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_POINTS + " TEXT,"
                    + HistoryContract.HistoryEntry.COLUMN_NAME_METHOD + " TEXT,"
                    + HistoryContract.HistoryEntry.COLUMN_NAME_RECTANGLE_COUNT + " INTEGER,"
                    + HistoryContract.HistoryEntry.COLUMN_NAME_TOTAL_AREA + " REAL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HistoryContract.HistoryEntry.TABLE_NAME;

    public HistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
