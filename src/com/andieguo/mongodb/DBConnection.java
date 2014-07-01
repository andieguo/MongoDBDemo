package com.andieguo.mongodb;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DBConnection extends TestCase {

	private MongoClient mongo;
	private DB db ;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		try {
			mongo = new MongoClient("localhost", 27017);// ��֤MongoDB�����Ѿ�����
			db = mongo.getDB("andyDB");// ��ȡ�����ݿ�
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void testGetAllTables() {
		Set<String> tables = db.getCollectionNames();
		for (String coll : tables) {
			System.out.println(coll);
		}
	}

	public void testSave() {
		DBCollection table = db.getCollection("person");
		BasicDBObject document = new BasicDBObject();
		document.put("name", "С��");// ��ֱ�Ӳ��뺺��
		document.put("age", 24);
		table.insert(document);
	}

	// { "_id" : ObjectId("5376f770eb3dd4e361f07779"), "username" : "С��", "password" : "xiaozhang", "age" : "23" }
	public void testSave2() {
		DBCollection table = db.getCollection("person");
		BasicDBObject document = new BasicDBObject();//������Ӷ���ֶ�
		document.put("name", "С��");// ��ֱ�Ӳ��뺺��
		document.put("password", "xiaozhang");
		document.put("age", "23");// �����һ���ֶ�Ҳ�ǿ��Եģ���ΪMongoDB������Ƕ���
		table.insert(document);
	}
	
	public void testSave3(){
		DBCollection table = db.getCollection("person");
		
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("name", "С��");
		maps.put("password", "xiaozhang");
		maps.put("age", 24);
		
		BasicDBObject document = new BasicDBObject(maps);//������Ӻ󣬶�������ֶ�������ġ�
		table.insert(document);
	}

	public void testUpdate() {
		DBCollection table = db.getCollection("person");
		BasicDBObject query = new BasicDBObject();
		query.put("name", "С��");

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("age", 23);

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		table.update(query, updateObj);
	}

	// ���������db.users.update({username:"andyguo"},{$set:{password:"hello"}})
	public void testUpdate2() {
		DBCollection table = db.getCollection("person");
		BasicDBObject query = new BasicDBObject("name", "С��");
		BasicDBObject newDocument = new BasicDBObject("age", 24);
		BasicDBObject updateObj = new BasicDBObject("$set", newDocument);
		table.update(query, updateObj);
	}
	//db.users.remove({name:"posly"})
	public void testDelete(){
		DBCollection table = db.getCollection("person");
		BasicDBObject query = new BasicDBObject("name", "С��");
		table.remove(query);
	}
	
    public void testFindAll(){
		DBCollection table = db.getCollection("person");
		DBCursor dbCursor = table.find();
		while(dbCursor.hasNext()){
			DBObject dbObject = dbCursor.next();
			//��ӡ�ö�����ض��ֶ���Ϣ
			System.out.println("name:"+	dbObject.get("name")+",age:"+dbObject.get("age"));
			//��ӡ�ö����������Ϣ
			System.out.println(dbObject);
		}
	}
	
}
