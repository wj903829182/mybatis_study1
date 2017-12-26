package com.jack.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/*我们希望SqlSessionFactory对于一个数据库而言只有一个实例，所以我们采用单例模式
 * 这里构建单例是通过initSqlSessionFactory方法实现的。首先将构造函数私有化，其目的是避免使用者使用new的方式去创建多个对象。
 * 然后使用synchronized 对SqlSessionFactoryUtil类加锁，其目的是避免在多线程的环境中，多次初始化造成对象的不唯一。
 * 使用单例的好处在于可以重复使用这个唯一的对象，而对象在内存中读取和运行都比较快，同时节约内存。
 * 
 * 还实现了openSqlSession方法，利用构建好的SqlSessionFactory创建SqlSession
 * 
 * */
//SqlSessionFactory工具类，用来获取SqlSessionFactory
public class SqlSessionFactoryUtil {
	// SqlSessionFactory对象
	private static SqlSessionFactory sqlSessionFactory = null;
	// 类线程锁
	private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;

	// 私有话构造函数
	private SqlSessionFactoryUtil() {
	}

	// 构建SqlSessionFactory
	public static SqlSessionFactory initSqlSessionFactory() {
		//SetPlace.class.getClassLoader().getResourceAsStream("dura_dist.txt");
		// mybatis配置文件路径
		//String resource = "classpath:/mybatis-config.xml";
		//String resource = SqlSessionFactoryUtil.class.getResource("/").getPath()+"mybatis-config.xml";
		//System.out.println(SqlSessionFactoryUtil.class.getResource("/").getPath());

		//resource = resource.substring(1);
		// 输入流
		InputStream inputStream = null;
		inputStream = SqlSessionFactoryUtil.class.getClass().getResourceAsStream("/mybatis-config.xml");
		/*try {
			// 获取输入流
			inputStream = Resources.getResourceAsStream(resource);
			//inputStream = SqlSessionFactoryUtil.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Level.SEVERE, null, e);
		}*/
		synchronized (CLASS_LOCK) {
			if (sqlSessionFactory == null) {
				// 创建sqlSessionFactory
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			}
		}
		// 返回
		return sqlSessionFactory;

	}

	// 打开SqlSession
	public static SqlSession openSqlSession() {
		if (sqlSessionFactory == null) {
			initSqlSessionFactory();
		}
		return sqlSessionFactory.openSession();
	}
}
