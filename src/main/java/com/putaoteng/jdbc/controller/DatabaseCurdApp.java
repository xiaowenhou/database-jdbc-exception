package com.putaoteng.jdbc.controller;

import java.util.ArrayList;
import java.util.List;
import com.putaoteng.jdbc.model.Student;
import com.putaoteng.jdbc.service.StudentDaoImpl;
import com.putaoteng.jdbc.utils.Log;
import com.putaoteng.jdbc.utils.LogLevel;

public class DatabaseCurdApp {

	public static void main(String[] args) {
		String url = "jdbc:mysql://120.77.169.243:3306/student?user=root&password=ZhangXin9527@1314"
				+ "&useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";

		for (int i=0; i<1000; i++) {
			System.out.println("----------------------------第" + (i+1) + "次操作-------------------------");
			StudentDaoImpl useDatabase = new StudentDaoImpl(url);
			List<Student> list = new ArrayList<Student>();
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

			// 插入数据操作
			useDatabase.addStudent(student);
			Log.loggerCreate(LogLevel.INFO, "插入一条数据");
			useDatabase.addStudent(student2);
			Log.loggerCreate(LogLevel.INFO, "插入一条数据");
			useDatabase.addStudent(student3);
			Log.loggerCreate(LogLevel.INFO, "插入一条数据");
			

			// 显示数据库中所有数据
			System.out.println("---------------------插入三条数据");
			list = useDatabase.queryStudentAll();
			for (Student user : list) {
				System.out.println(user.toString());
			}

			// 删除数据操作
			System.out.println("---------------------删除一条数据");
			useDatabase.deleteStudent(3);
			Log.loggerCreate(LogLevel.INFO, "删除一条数据");
			list = useDatabase.queryStudentAll();
			for (Student user : list) {
				System.out.println(user.toString());
			}

			// 通过id查询一条数据
			System.out.println("---------------------根据ID查询一条数据");
			Student student5 = useDatabase.queryStudentById(2);
			Log.loggerCreate(LogLevel.INFO, "查询一条数据");
			System.out.println(student5.toString());

			// 更新一条数据
			System.out.println("---------------------更新一条数据");
			student5.setName("小强");
			student5.setName("0000000000");
			useDatabase.updateStudent(student5);
			Log.loggerCreate(LogLevel.INFO, "更新一条数据");
			list = useDatabase.queryStudentAll();
			for (Student user : list) {
				System.out.println(user.toString());
			}

			// 通过姓名查询数据集
			System.out.println("---------------------根据姓名查询数据集");
			list = useDatabase.queryStudentByName("zhangsan");
			for (Student user : list) {
				System.out.println(user);
			}

			// 查看表的总长度
			System.out.println("---------------------计算表长度");
			int count = useDatabase.countTableLength("*");
			System.out.println("表总长为: " + count);

			// 清空表中所有数据
			System.out.println("----------------------清空表");
			useDatabase.clearTable();
			list = useDatabase.queryStudentAll();
			for (Student user : list) {
				System.out.println(user.toString());
			}
		}
	}
}
