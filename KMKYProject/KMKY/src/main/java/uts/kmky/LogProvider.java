package uts.kmky;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.List;

/**
 * Created by FrederikKastrup on 24/09/13.
 */
public class LogProvider extends ContentProvider {

    //Variable to hold an instance of Database
    private MySQLiteHelper dbHelper;

    //Used for UriMatcher

    //For all rows
    private static final int LOGS = 1;

    //For Specific row
    private static final int LOGS_ID = 2;

    private  static final String AUTHORITY = "uts.kmky.database.contentprovider";

    private static final String BASE_PATH = "database";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);




    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, LOGS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", LOGS_ID);
    }


    @Override
    public boolean onCreate() {

        //Instantiate the database
        //dbHelper = new MySQLiteHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //Open the database
        SQLiteDatabase database;
        try
        {
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLException ex)
        {
            database = dbHelper.getReadableDatabase();
        }

        //SQL Statements


        String having = null;
        String groupBy = null;


        //SQLite Query Builder for simplifying construction
        //of the database query
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        //If this is a row query, limit the result set to the passed in row
        switch (uriMatcher.match(uri))
        {
            case LOGS_ID :
                String rowIDs = uri.getPathSegments().get(2);
                queryBuilder.appendWhere(LogTable.COLUMN_PHONENUMBER + "=" + rowIDs);

            break;

            case LOGS :



            break;

            default: throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        //Specifying which table to do operations on
        queryBuilder.setTables(LogTable.TABLE_NAME);

        //Executing the query
        Cursor cursor = queryBuilder.query(database,projection, selection, selectionArgs, groupBy, having, sortOrder);

        //Returns the cursor
        return cursor;
    }

    @Override
    public String getType(Uri uri) {


            //Not needed
            return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        int uriType = uriMatcher.match(uri);


        //

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;

        switch (uriType)
        {
            case LOGS_ID :
                id = database.insert(LogTable.TABLE_NAME, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {




        return 0;
    }

    private void checkColumns(String[] projection)
    {
        //Checks to see if the record already exists.
    }
}
