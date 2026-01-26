package zeev.fraiman.rectanglemethod;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView curvesListView;
    private HistoryDbHelper dbHelper;
    private ArrayList<String> curveIds = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new HistoryDbHelper(this);
        curvesListView = findViewById(R.id.curvesListView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, curveIds);
        curvesListView.setAdapter(adapter);

        curvesListView.setOnItemClickListener((parent, view, position, id) -> {
            String curveId = curveIds.get(position);
            Intent intent = new Intent(HistoryActivity.this, CurveDetailsActivity.class);
            intent.putExtra("CURVE_ID", curveId);
            startActivity(intent);
        });

        loadCurveIds();
    }

    private void loadCurveIds() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(true, HistoryContract.HistoryEntry.TABLE_NAME, 
                                 new String[]{HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_ID}, 
                                 null, null, HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_ID, 
                                 null, HistoryContract.HistoryEntry._ID + " DESC", null);

        while (cursor.moveToNext()) {
            curveIds.add(cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_ID)));
        }
        cursor.close();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
