package com.example.alwaysinmem.utils;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.example.alwaysinmem.model.Grave;
import com.example.alwaysinmem.model.GraveUser;
import com.example.alwaysinmem.model.Human;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "grave_information.db";
	private static int DATABASE_VERSION =1;
	
	private Dao<Grave, Integer>	graveDao;
	private Dao<Human, Integer> humanDao;
	private Dao<GraveUser, Integer> graveUserDao;
	
	
	public DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDb, ConnectionSource connectionSource) {
		try{
			TableUtils.createTable(connectionSource, Grave.class);
			TableUtils.createTable(connectionSource, Human.class);
			TableUtils.createTable(connectionSource, GraveUser.class);
		}
		catch(SQLException e){
			Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer,
			int newVer) {
		try {
			
			TableUtils.dropTable(connectionSource, Grave.class, true);
			TableUtils.dropTable(connectionSource, Human.class, true);
			TableUtils.dropTable(connectionSource, GraveUser.class, true);
			onCreate(sqliteDatabase, connectionSource);
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
					+ newVer, e);
		}		
	}
	
	public Dao<Human, Integer> getHumanDao() throws SQLException{
		if(humanDao == null){
			humanDao = getDao(Human.class);
		}
		return humanDao;
	}
	
	public Dao<Grave, Integer> getGraveDao() throws SQLException {
		if(graveDao == null){
			graveDao = getDao(Grave.class);
		}
		return graveDao;
	}
	
	public Dao<GraveUser, Integer> getGraveUserDao() throws SQLException{
		if(graveUserDao == null){
			graveUserDao = getDao(GraveUser.class);
		}
		return graveUserDao;
	}

}
