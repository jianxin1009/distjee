/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.distjee.common.dao.mapper.test.able;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import net.distjee.common.dao.mapper.mapper.MybatisHelper;
import net.distjee.common.dao.mapper.mapper.UserInfoAbleMapper;
import net.distjee.common.dao.mapper.model.UserInfoAble;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试增删改查
 *
 * @author liuzh
 */
public class TestBasicAble {

    /**
     * 新增
     */
    @Test
    public void testInsert() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserInfoAbleMapper mapper = sqlSession.getMapper(UserInfoAbleMapper.class);
            UserInfoAble userInfo = new UserInfoAble();
            userInfo.setUsername("abel533");
            userInfo.setPassword("123456");
            userInfo.setUsertype("2");
            userInfo.setEmail("abel533@gmail.com");//insert=false

            Assert.assertEquals(1, mapper.insert(userInfo));

            Assert.assertNotNull(userInfo.getId());
            Assert.assertEquals(6, (int) userInfo.getId());

            userInfo = mapper.selectByPrimaryKey(userInfo.getId());
            //email没有插入
            Assert.assertNull(userInfo.getEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * 根据主键全更新
     */
    @Test
    public void testUpdateByPrimaryKey() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserInfoAbleMapper mapper = sqlSession.getMapper(UserInfoAbleMapper.class);
            UserInfoAble userInfo = mapper.selectByPrimaryKey(2);
            Assert.assertNotNull(userInfo);
            userInfo.setUsertype(null);
            userInfo.setEmail("abel533@gmail.com");
            userInfo.setAddress("这个地址不会更新进去");//update=false
            //不会更新username
            Assert.assertEquals(1, mapper.updateByPrimaryKey(userInfo));

            userInfo = mapper.selectByPrimaryKey(userInfo.getId());
            Assert.assertNull(userInfo.getUsertype());
            Assert.assertNotEquals("这个地址不会更新进去", userInfo.getAddress());
            Assert.assertEquals("abel533@gmail.com", userInfo.getEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * 根据主键更新非null
     */
    @Test
    public void testUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserInfoAbleMapper mapper = sqlSession.getMapper(UserInfoAbleMapper.class);
            UserInfoAble userInfo = mapper.selectByPrimaryKey(1);
            Assert.assertNotNull(userInfo);
            userInfo.setUsertype(null);
            userInfo.setPassword(null);
            userInfo.setAddress("这个地址不会更新进去");
            //不会更新username
            Assert.assertEquals(1, mapper.updateByPrimaryKeySelective(userInfo));

            userInfo = mapper.selectByPrimaryKey(1);
            Assert.assertEquals("1", userInfo.getUsertype());
            Assert.assertEquals("12345678", userInfo.getPassword());
            Assert.assertNotEquals("这个地址不会更新进去", userInfo.getAddress());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * 根据主键更新非null
     */
    @Test
    public void testUpdateBatchByPrimaryKeySelective() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserInfoAbleMapper mapper = sqlSession.getMapper(UserInfoAbleMapper.class);
            UserInfoAble userInfo0 = mapper.selectByPrimaryKey(1);
            UserInfoAble userInfo1 = mapper.selectByPrimaryKey(2);
            UserInfoAble userInfo2 = mapper.selectByPrimaryKey(3);
            UserInfoAble userInfo3 = mapper.selectByPrimaryKey(4);
            Assert.assertNotNull(userInfo0);
            Assert.assertNotNull(userInfo1);
            Assert.assertNotNull(userInfo2);
            Assert.assertNotNull(userInfo3);
            userInfo0.setUsername("用户名0");
            userInfo1.setUsername("用户名1");
            userInfo2.setUsername("用户名2");
            userInfo3.setUsername("用户名3");
            List<UserInfoAble> userInfoAbles = new ArrayList<UserInfoAble>();
            userInfoAbles.add(userInfo0);
            userInfoAbles.add(userInfo1);
            userInfoAbles.add(userInfo2);
            userInfoAbles.add(userInfo3);
            Assert.assertEquals(1, mapper.updateBatchByPrimaryKeySelective(userInfoAbles));

            userInfo0 = mapper.selectByPrimaryKey(1);
            userInfo1 = mapper.selectByPrimaryKey(2);
            userInfo2 = mapper.selectByPrimaryKey(3);
            userInfo3 = mapper.selectByPrimaryKey(4);
            Assert.assertNotEquals("用户名0", userInfo0.getUsername());
            Assert.assertNotEquals("用户名1", userInfo1.getUsername());
            Assert.assertNotEquals("用户名2", userInfo2.getUsername());
            Assert.assertNotEquals("用户名3", userInfo3.getUsername());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * 测试按主键批量删除
     */
    @Test
    public void testDeleteByPrimaryKeys(){
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserInfoAbleMapper mapper = sqlSession.getMapper(UserInfoAbleMapper.class);
            Assert.assertEquals(4, mapper.deleteByPrimaryKeys(1,2,3,4));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }



}
