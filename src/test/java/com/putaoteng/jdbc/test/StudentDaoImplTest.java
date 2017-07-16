package com.putaoteng.jdbc.test;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.putaoteng.jdbc.model.Student;
import com.putaoteng.jdbc.service.StudentDaoImpl;

public class StudentDaoImplTest {
	private StudentDaoImpl udi = null;

	//链接数据库测试,测试成功后作为每次测试前都执行的任务
	@Before
	public void setUp() {
		String url = "jdbc:mysql://localhost:3306/student?user=root&password=zx95271314"
				+ "&useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
	
		udi = new StudentDaoImpl(url);
	}

	//添加数据测试
	@Test
	public void testAddUser() {
		Student student = new Student();
		student.setId(1);
		student.setName("zhangsan");
		student.setQqNumber(123456789);
		student.setProfession("java");
		student.setJoinDate("2017年1月1日");
		student.setSchool("清华大学");
		student.setOnlineNumber("9527");
		student.setDailyLink("www.baidu.com");
		student.setDesire("牛鼻");
		student.setMsgSource("知乎");
		student.setBrother("大师兄");
		student.setCreateAt(20170715133333L);
		student.setUpdateAt(20170715133356L);
		
		Student student2 = new Student();
		student2.setId(2);
		student2.setName("lisi");
		student2.setQqNumber(123456789);
		student2.setProfession("qianduan");
		student2.setJoinDate("2017年1月1日");
		student2.setSchool("上海交通大学");
		student2.setOnlineNumber("9527");
		student2.setDailyLink("www.baidu.com");
		student2.setDesire("牛鼻");
		student2.setMsgSource("知乎");
		student2.setBrother("二师兄兄");
		student2.setCreateAt(20170715133333L);
		student2.setUpdateAt(20170715133356L);
		
		Student student3 = new Student();
		student3.setId(3);
		student3.setName("王五");
		student3.setQqNumber(123456789);
		student3.setProfession("qianduan");
		student3.setJoinDate("2017年8月1日");
		student3.setSchool("北京大学");
		student3.setOnlineNumber("9527");
		student3.setDailyLink("www.baidu.com");
		student3.setDesire("牛鼻");
		student3.setMsgSource("知乎");
		student3.setBrother("小师弟");
		student3.setCreateAt(20170715133333L);
		student3.setUpdateAt(20170715133356L);

		long result = 0;
		result = udi.addStudent(student);
		Assert.assertEquals(result, student.getId());

		result = udi.addStudent(student2);
		Assert.assertEquals(result, student2.getId());

		result = udi.addStudent(student3);
		Assert.assertEquals(result, student3.getId());
	}

	//删除数据测试
	@Test
	public void testDeleteUser() {
		for (int id = 1; id <= 2; id++) {
			boolean result = udi.deleteStudent(id);
			Assert.assertTrue(result);
		}
	}

	//更新数据测试
	@Test
	public void testUpdateUser() {
		Student student = udi.queryStudentById(1);
		student.setQqNumber(888888);
		student.setName("李二丫");
		boolean result = udi.updateStudent(student);
		Assert.assertTrue(result);
	}

	//计算表长度测试
	@Test
	public void testQueryTableLength() {
		String field = "*";

		// 断言该表长度为3
		int result = udi.countTableLength(field);
		Assert.assertEquals(result, 4);
	}

	//根据用户id查询单条数据测试
	@Test
	public void testqueryUserById() {
		for (int i = 1; i <= udi.countTableLength("*"); i++)
			System.out.println(udi.queryStudentById(i).toString());
		
		System.out.println(udi.queryStudentById(19).toString());
	}

	//查询表中所有数据测试
	@Test
	public void testqueryUserAll() {
		List<Student> list = udi.queryStudentAll();

		for (Student student : list) {
			System.out.println(student.toString());
		}
	}

	//根据姓名查询数据测试
	@Test
	public void testqueryUserByName() {
		String name = "王五";
		List<Student> list = udi.queryStudentByName(name);
		for (Student student : list) {
			System.out.println(student.toString());
		}
	}

	//清空表测试
	@Test
	public void testClearTable() {
		boolean result = false;
		result = udi.clearTable();
		Assert.assertTrue(result);
	}
}
