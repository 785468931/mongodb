package com.li.mongodb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bson.BsonDocument;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import com.li.dbbase.Config;
import com.li.mongodb.utils.TimeUtil;
import com.li.mongodb.vo.Log;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.Bytes;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.util.JSON;

@SuppressWarnings({ "rawtypes", "unused" })
public class BankBindLogMongoDBUtils {

	private MongoClient mongoClient = Config.mongoClient;
	private MongoDatabase db = Config.db;
	private MongoCollection collection = db.getCollection("bankBindLog");
	public static void main(String[] args) {

//		new BankBindLogMongoDBUtils().bankBindLogListQuery(0, 2);

		 new BankBindLogMongoDBUtils().bankBindLogInsert("13421747372", "1380099920000",
		 "0000", "success",
		 "<ErrorMsgContent><errorCode>0000</errorCode><errorMessage>交易成功</errorMessage></ErrorMsgContent>",
		 "<ErrorMsgContent><errorCode>0000</errorCode><errorMessage>交易成功</errorMessage></ErrorMsgContent>",
		 "success","success");
		
//		long total = new bankBindLogMongoDBUtils().bankBindLogListQuerySize();
//		System.out.println(total);
		
//		System.out.println(new bankBindLogMongoDBUtils().bankBindLogDelete("1380099920000"));
		
		
		// testListQuerySize();
		// testListQuery(0, 10);
		// testOneQuery();
		// testBatchInsert();
		// testListQuerySize();
	}

	public String bankBindLogListQuery(int page, int total) {
		if (page <= 0) {
			page = 1;
		}
		page = (page - 1) * total;

		StringBuilder strBui = new StringBuilder();

		// BasicDBObject keys = new BasicDBObject();
		// keys.put("cust_id", "{$exists: true}");
		// keys.put("amount", "{$exists: true}");
		// keys.put("status", "{$exists: true}");

		// BasicDBObject keys={"amount": {$exists: true}, "status": {$exists:
		// true}};
		long totalCount=(long)bankBindLogListQuerySize(); 
		
		if((totalCount%total)==0){
			totalCount = (totalCount/total==0?1:totalCount/total);
			}else{
				totalCount = (totalCount/total+1);
			}
		
		
		FindIterable iterable = collection.find().skip(page).limit(total);
		MongoCursor cursor = iterable.iterator();

		strBui.append("{");
		
		strBui.append("\"rows\":");
		strBui.append("[");
		boolean flag=true;
		while (cursor.hasNext()) {
			if(!flag){
				strBui.append(",");
			}
			flag=false;
			Document user = (Document) cursor.next();
//			System.out.println(user.toJson());
			strBui.append(user.toJson());
		}
		strBui.append("]");
		
		
		strBui.append(",\"total\":"+totalCount);
		strBui.append("}");
		 System.out.println(strBui.toString());
		cursor.close();
		return null;
	}

	public long bankBindLogListQuerySize() {
		return collection.count();
	}


	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param phoneNo 手机号
	 * @param orderNo 订单号
	 * @param sendContent 发送的内容
	 * @param retContent  返回的内容
	 */
	public void bankBindLogInsert(String phoneNo, String orderNo, String errorCode,
			String errorMessage, String sendContent, String retContent,String encryptSendContent,String encryptRetContent) {
		 Log log=new Log();
		Document doc = new Document();
		doc.put("orderNo", orderNo);
		doc.put("phoneNo", phoneNo);
		doc.put("errorCode", errorCode);
		doc.put("errorMessage", errorMessage);
		doc.put("createTime", TimeUtil.getTimeUni());
		doc.put("sendContent", sendContent);
		doc.put("retContent", retContent);
		doc.put("encryptSendContent", encryptSendContent);
		doc.put("encryptRetContent", encryptRetContent);
		// log.setOrderNo(orderNo);
		// log.setPhoneNo(phoneNo);
		// log.setErrorCode(errorCode);
		// log.setErrorMessage(errorMessage);
		// log.setSendContent(sendContent);
		// log.setRetContent(retContent);
		// log.setCreateTime(TimeUtil.getTimeUni());

		// Document doc=log;
		collection.insertOne(doc);

	}

	

	// test delete

	public long bankBindLogDelete(String orderNo) {
		BasicDBObject query = new BasicDBObject("orderNo", orderNo);
		DeleteResult dresule=collection.deleteOne(query);
		return dresule.getDeletedCount();
	}
	
	public String bankBindLogWhereQuery(int page, int total,String phoneNo) {
		if (page <= 0) {
			page = 1;
		}
		page = (page - 1) * total;

		StringBuilder strBui = new StringBuilder();

		 BasicDBObject query = new BasicDBObject();
		 query.put("phoneNo", phoneNo);
		long totalCount=(long)bankBindLogListQuerySize(); 
		
		if((totalCount%total)==0){
			totalCount = (totalCount/total==0?1:totalCount/total);
			}else{
				totalCount = (totalCount/total+1);
			}
		
		
		FindIterable iterable = collection.find(query).skip(page).limit(total);
		MongoCursor cursor = iterable.iterator();

		strBui.append("{");
		
		strBui.append("\"rows\":");
		strBui.append("[");
		boolean flag=true;
		while (cursor.hasNext()) {
			if(!flag){
				strBui.append(",");
			}
			flag=false;
			Document user = (Document) cursor.next();
//			System.out.println(user.toJson());
			strBui.append(user.toJson());
		}
		strBui.append("]");
		
		
		strBui.append(",\"total\":"+totalCount);
		strBui.append("}");
		 System.out.println(strBui.toString());
		cursor.close();
		return null;
	}
	

	private void bankBindOneQuery(String phoneNo) {
		StringBuilder strBui = new StringBuilder();
//		MongoCollection collection = db.getCollection("payLog");
		BasicDBObject query = new BasicDBObject("phoneNo", phoneNo);
		// BasicDBObject query = new BasicDBObject("file", "FileInputStream");
		// BasicDBObject returnField1 = new BasicDBObject("cust_id", 1);
		// BasicDBObject returnField2 = new BasicDBObject("status", 1);
		FindIterable iterable = collection.find(query);
		MongoCursor cursor = iterable.iterator();

		strBui.append("{");
		
		strBui.append("\"rows\":");
		strBui.append("[");
		boolean flag=true;
		
		while (cursor.hasNext()) {
			Document user = (Document) cursor.next();
			// System.out.println(user.get("file"));
			System.out.println(user.toJson());
		}
		cursor.close();
	}

}
