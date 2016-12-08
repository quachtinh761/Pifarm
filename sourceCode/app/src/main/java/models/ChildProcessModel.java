package models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import function.IntergerHanding;
import function.StringHanding;
import objects.ChildProcessObject;
import objects.ParentProcessObject;

/**
 * Created by nguyenvanthi on 13/11/2016.
 */

public class ChildProcessModel extends BaseModel{
    private String tableName = "CHILDPROCESS";
    private static String[] listField = {"ID","nDayAfterBorn","lsVaccine"};
    //                                     0 ,  1                   2
    private List<String []> params = new LinkedList<>();
    private void makeparams(){
        String[] p = new String[2];
        p[0] = listField[0];
        p[1] = "TEXT(10) NOT NULL PRIMARY KEY";
        params.add(p);

        String[] p1 = new String[2];
        p1[0] = listField[1];
        p1[1] = "INTEGER";
        params.add(p1);

        String[] p2 = new String[2];
        p2[0] = listField[2];
        p2[1] = "TEXT(1500)";
        params.add(p2);
    }

    public ChildProcessModel(Context context) {
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
    /*
    * @param params
     * List < String[] > params = ArrayList();
     * params.put("fieldName1","filedType1(primary key if needed)");
     * params.put("fieldName2","filedType2 (not null)");
     */

    private Map<String, String> makeMap(ChildProcessObject childProcessObject){
        Map<String, String> map = new HashMap<String, String>();
        map.put(listField[0],childProcessObject.getID());
        map.put(listField[1], String.valueOf(childProcessObject.getnDayAfterBorn()));
        List<String> buff = new ArrayList<String>();
        for (Map.Entry<String, Integer> var : childProcessObject.getLsVaccine().entrySet()){
            buff.add(var.getKey());
            buff.add(String.valueOf(var.getValue()));
        }
        String p = StringHanding.getStr(buff);
        map.put(listField[2], p);

        return map;
    }

    public void add(List<ChildProcessObject> childProcessObjects){
        for (ChildProcessObject var : childProcessObjects) add(var);
    }
    public void add(ChildProcessObject childProcessObject){
        insert(tableName, makeMap(childProcessObject));
    }

    public void remove(List<ChildProcessObject> childProcessObject){
        for (ChildProcessObject var : childProcessObject)remove(var);
    }
    public void remove(String[] id){
        for (String str : id) remove(str);
    }
    public void remove(String id){
        Map<String, String> map = new HashMap<String,String>();
        map.put(listField[0],id);
        this.deleteRecord(tableName,map);
    }
    public void remove(ChildProcessObject childProcessObject){
        Map<String, String> map = new HashMap<String,String>();
        map.put(listField[0], childProcessObject.getID());
        this.deleteRecord(tableName,map);
    }

    public void update(List<ChildProcessObject> childProcessObject){
        for (ChildProcessObject child: childProcessObject) update(child);
    }
    public void update(ChildProcessObject childProcessObject){
        remove(childProcessObject.getID());
        insert(tableName, makeMap(childProcessObject));
    }

    private Map<String,Integer> makeMap(String data){
        String[] temp = StringHanding.getArrayStr(data);
        Map<String,Integer> ret = new HashMap<String, Integer>();
        for (int i = 0; i < temp.length; i++){
            ret.put(temp[i], IntergerHanding.getInterger(temp[i+1]));
            i++;
        }
        return ret;
    }
    //num is index of listField
    public List<ChildProcessObject> search(String[] needSearch,int num){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[num] + "= ?",needSearch,"","","");
        List<ChildProcessObject> p = new LinkedList<ChildProcessObject>();
        for (String[] var: buff) {
            p.add(new ChildProcessObject(var[0], IntergerHanding.getInterger(var[1]),makeMap(var[2])));
        }
        return p;
    }
    public List<ChildProcessObject> search(String needSearch,int num){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[num] + "= ?",new String[]{needSearch},"","","");
        List<ChildProcessObject> p = new LinkedList<ChildProcessObject>();
        for (String[] var: buff) {
            p.add(new ChildProcessObject(var[0], IntergerHanding.getInterger(var[1]),makeMap(var[2])));
        }
        return p;
    }
    public List<ChildProcessObject> search(String[] needSearch){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[0] + "= ?",needSearch,"","","");
        List<ChildProcessObject> p = new LinkedList<ChildProcessObject>();
        for (String[] var: buff) {
            p.add(new ChildProcessObject(var[0], IntergerHanding.getInterger(var[1]),makeMap(var[2])));
        }
        return p;
    }
    public List<ChildProcessObject> search(String needSearch){
        List<String []> buff = searchDataByConditions(tableName,listField,listField[0] + "= ?",new String[]{needSearch},"","","");
        List<ChildProcessObject> p = new LinkedList<ChildProcessObject>();
        for (String[] var: buff) {
            p.add(new ChildProcessObject(var[0], IntergerHanding.getInterger(var[1]),makeMap(var[2])));
        }
        return p;
    }

    public List<ChildProcessObject> searchAll(){
        List<String []> buff = searchDataByConditions(tableName,listField,null,null,"","","");
        List<ChildProcessObject> p = new LinkedList<ChildProcessObject>();
        for (String[] var: buff) {
            p.add(new ChildProcessObject(var[0], IntergerHanding.getInterger(var[1]),makeMap(var[2])));
        }
        return p;
    }
}
