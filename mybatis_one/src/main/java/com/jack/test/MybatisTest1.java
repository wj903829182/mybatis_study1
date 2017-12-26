package com.jack.test;

import com.jack.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * create by jack 2017/12/25
 */
public class MybatisTest1 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SqlSession sqlSession = null;
        sqlSession = SqlSessionFactoryUtil.openSqlSession();
        System.out.println("sqlSession = "+sqlSession);

    }
}
