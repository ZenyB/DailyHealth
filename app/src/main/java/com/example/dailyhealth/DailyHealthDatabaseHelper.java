//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DailyHealthDatabaseHelper extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME = "daily_health.db";
//    private static final int DATABASE_VERSION = 1;
//
//    public DailyHealthDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(DailyHealthContract.NguoiDung.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.LoiNhac.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.BaiTapLon.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.BaiTapNho.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.ChiTietBaiTap.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.KinhNguyet.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.LuongNuoc.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.GiacNgu.CREATE_TABLE);
//        db.execSQL(DailyHealthContract.TapLuyen.CREATE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(DailyHealthContract.NguoiDung.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.LoiNhac.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.BaiTapLon.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.BaiTapNho.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.ChiTietBaiTap.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.KinhNguyet.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.LuongNuoc.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.GiacNgu.DELETE_TABLE);
//        db.execSQL(DailyHealthContract.TapLuyen.DELETE_TABLE);
//        onCreate(db);
//    }
//}
