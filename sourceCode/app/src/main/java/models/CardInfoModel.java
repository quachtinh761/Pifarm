package models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import function.DateHanding;
import function.IntergerHanding;
import function.StringHanding;
import objects.CardInfoObject;

/**
 * Created by Van Thi on 12/29/2016.
 */

public class CardInfoModel extends BaseModel{
    private String tableName = "CARDINFO";
    private static String[] listField = { "ID", "EarNumber", "DateCoordination",  "DateImport"};
    private List<String []> params = new LinkedList<>();
    private void makeparams(){
        String[] p = new String[2];
        p[0] = listField[0];
        p[1] = "TEXT(10) NOT NULL PRIMARY KEY";
        params.add(p);

        String[] p1 = new String[2];
        p1[0] = listField[1];
        p1[1] = "TEXT(10) NOT NULL";
        params.add(p1);

        String[] p2 = new String[2];
        p2[0] = listField[2];
        p2[1] = "TEXT(10)";
        params.add(p2);

        String[] p3 = new String[2];
        p3[0] = listField[3];
        p3[1] = "TEXT(10)";
        params.add(p3);
    }
    public CardInfoModel(Context context) {
        super(context);
        if (!this.isTableExist(tableName)){
            makeparams();
            this.createTable(tableName,params);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private Map<String, String> makeMap(CardInfoObject cardInfoObject){
        Map<String, String> map = new HashMap<String, String>();
        map.put(listField[0],cardInfoObject.getID());
        map.put(listField[1], cardInfoObject.getEarNumber());
        map.put(listField[2], DateHanding.getDateString(cardInfoObject.getCoordination()));
        map.put(listField[3], DateHanding.getDateString(cardInfoObject.getDateImport()));
        return map;
    }

    public void add(List<CardInfoObject> cardInfoObjects){
        for (CardInfoObject var : cardInfoObjects) add(var);
    }
    public void add(CardInfoObject cardInfoObject){
        this.insert(tableName, makeMap(cardInfoObject));
    }

    public void remove(List<CardInfoObject> cardInfoObjects){
        for (CardInfoObject var : cardInfoObjects) remove(var);
    }
    public void remove(CardInfoObject cardInfoObject){
        Map<String, String> map = new HashMap<String,String>();
        map.put(listField[0], cardInfoObject.getID());
        this.deleteRecord(tableName,map);
    }
    public void remove(String[] id){
        for (String str : id) remove(str);
    }
    public void remove(String id){
        Map<String, String> map = new HashMap<String,String>();
        map.put(listField[0],id);
        this.deleteRecord(tableName,map);
    }

    public void update(List<CardInfoObject> cardInfoObjects){
        for (CardInfoObject var : cardInfoObjects) update(var);
    }
    public void update(CardInfoObject cardInfoObject){
        remove(cardInfoObject.getID());
        insert(tableName, makeMap(cardInfoObject));
    }

    //num is index of listField
    public List<CardInfoObject> search(String[] needSearch,int num){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[num] + "= ?",needSearch,"","","");
        List<CardInfoObject> p = new LinkedList<CardInfoObject>();
        for (String[] var: buff) {
            p.add(new CardInfoObject(var[0], var[1], DateHanding.getDate(var[2]), DateHanding.getDate(var[3])));
        }
        return p;
    }
    public List<CardInfoObject> search(String needSearch,int num){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[num] + "= ?",new String[] {needSearch},"","","");
        List<CardInfoObject> p = new LinkedList<CardInfoObject>();
        for (String[] var: buff) {
            p.add(new CardInfoObject(var[0], var[1], DateHanding.getDate(var[2]), DateHanding.getDate(var[3])));
        }
        return p;
    }
    public List<CardInfoObject> search(String[] needSearch){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[0] + "= ?",needSearch,"","","");
        List<CardInfoObject> p = new LinkedList<CardInfoObject>();
        for (String[] var: buff) {
            p.add(new CardInfoObject(var[0], var[1], DateHanding.getDate(var[2]), DateHanding.getDate(var[3])));
        }
        return p;
    }
    public List<CardInfoObject> search(String needSearch){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[0] + "= ?",new String[]{needSearch},"","","");
        List<CardInfoObject> p = new LinkedList<CardInfoObject>();
        for (String[] var: buff) {
            p.add(new CardInfoObject(var[0], var[1], DateHanding.getDate(var[2]), DateHanding.getDate(var[3])));
        }
        return p;
    }

    public List<CardInfoObject> searchAll(){
        List<String []> buff = searchDataByConditions(tableName,listField,null,null,"","","");
        List<CardInfoObject> p = new LinkedList<CardInfoObject>();
        for (String[] var: buff) {
            p.add(new CardInfoObject(var[0], var[1], DateHanding.getDate(var[2]), DateHanding.getDate(var[3])));
        }
        return p;
    }
}