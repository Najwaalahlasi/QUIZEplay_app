import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.geoquize.Question;


public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME ="geo_db";
    public static final int DB_VERSION=1;
    public MyDatabase(Context context) {
        super(context, DB_NAME , null,  DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE geo (id INTEGER PRIMARY KEY AUTOINCREMENT ," + "question TEXT  , answer TEXT ,distancePerLetter REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert (Question question ){

    }
}
