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
			mongo = new MongoClient("localhost", 27017);// 保证MongoDB服务已经启动
			db = mongo.getDB("andyDB");// 获取到数据库
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
		document.put("name", "小郭");// 能直接插入汉字
		document.put("age", 24);
		table.insert(document);
	}

	// { "_id" : ObjectId("5376f770eb3dd4e361f07779"), "username" : "小张", "password" : "xiaozhang", "age" : "23" }
	public void testSave2() {
		DBCollection table = db.getCollection("person");
		BasicDBObject document = new BasicDBObject();//可以添加多个字段
		document.put("name", "小张");// 能直接插入汉字
		document.put("password", "xiaozhang");
		document.put("age", "23");// 多添加一个字段也是可以的，因为MongoDB保存的是对象；
		table.insert(document);
	}
	
	public void testSave3(){
		DBCollection table = db.getCollection("person");
		
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("name", "小李");
		maps.put("password", "xiaozhang");
		maps.put("age", 24);
		
		BasicDBObject document = new BasicDBObject(maps);//这样添加后，对象里的字段是无序的。
		table.insert(document);
	}

	public void testUpdate() {
		DBCollection table = db.getCollection("person");
		BasicDBObject query = new BasicDBObject();
		query.put("name", "小张");

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("age", 23);

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		table.update(query, updateObj);
	}

	// 命令操作：db.users.update({username:"andyguo"},{$set:{password:"hello"}})
	public void testUpdate2() {
		DBCollection table = db.getCollection("person");
		BasicDBObject query = new BasicDBObject("name", "小张");
		BasicDBObject newDocument = new BasicDBObject("age", 24);
		BasicDBObject updateObj = new BasicDBObject("$set", newDocument);
		table.update(query, updateObj);
	}
	//db.users.remove({name:"posly"})
	public void testDelete(){
		DBCollection table = db.getCollection("person");
		BasicDBObject query = new BasicDBObject("name", "小李");
		table.remove(query);
	}
	
    public void testFindAll(){
		DBCollection table = db.getCollection("person");
		DBCursor dbCursor = table.find();
		while(dbCursor.hasNext()){
			DBObject dbObject = dbCursor.next();
			//打印该对象的特定字段信息
			System.out.println("name:"+	dbObject.get("name")+",age:"+dbObject.get("age"));
			//打印该对象的所有信息
			System.out.println(dbObject);
		}
	}
	
}
