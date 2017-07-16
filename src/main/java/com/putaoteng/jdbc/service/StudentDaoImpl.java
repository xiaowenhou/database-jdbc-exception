package com.putaoteng.jdbc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.core.exceptions.ConnectionIsClosedException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.putaoteng.jdbc.dao.StudentDao;
import com.putaoteng.jdbc.model.Student;
import com.putaoteng.jdbc.utils.Log;
import com.putaoteng.jdbc.utils.LogLevel;

public class StudentDaoImpl implements StudentDao {
	private PreparedStatement prepareStatement = null;
	private Connection conn = null;
	private ResultSet rs = null;
	private String url;

	public StudentDaoImpl(String url) {
		this.url = url;
	}

	// 创建数据库链接
	private void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			System.out.println("创建数据库驱动对象失败");
			// 发生错误,就记录一条日志到错误日志文件中
			Log.loggerCreate(LogLevel.ERROR, "创建数据库驱动对象失败");
			e1.printStackTrace();
		}

		try {
			this.conn = DriverManager.getConnection(url);
		} catch (SQLSyntaxErrorException e) {
			System.out.println("链接数据库失败,请检查你的url输入是否正确");
			System.out.println();
			Log.loggerCreate(LogLevel.ERROR, "链接数据库失败");
		} catch (CommunicationsException e) {
			System.out.println("链接数据库失败........");
			System.out.println();
			Log.loggerCreate(LogLevel.ERROR, "链接数据库失败");
		} catch (ConnectionIsClosedException e) {
			System.out.println("数据库服务关闭,请重启数据库服务...");
			System.out.println();
			Log.loggerCreate(LogLevel.ERROR, "链接数据库失败,数据库服务关闭");
		} catch (SQLNonTransientConnectionException e) {
			System.out.println("数据库无链接异常...");
			System.out.println();
			Log.loggerCreate(LogLevel.ERROR, "链接数据库失败,数据库服务关闭");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 增加数据
	public long addStudent(Student user) {
		// 每一次调用之前自动创建链接
		createConnection();

		String sql = "INSERT INTO student (id, name, qq_number, profession, "
				+ "join_date, school, online_number, daily_link, desire, "
				+ "msg_source, brother, create_at, update_at) " + "values(?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ? ,? ,?)";
		long result = 0;
		if (this.conn != null) {
			try {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setLong(1, user.getId());
				prepareStatement.setString(2, user.getName());
				prepareStatement.setInt(3, user.getQqNumber());
				prepareStatement.setString(4, user.getProfession());
				prepareStatement.setString(5, user.getJoinDate());
				prepareStatement.setString(6, user.getSchool());
				prepareStatement.setString(7, user.getOnlineNumber());
				prepareStatement.setString(8, user.getDailyLink());
				prepareStatement.setString(9, user.getDesire());
				prepareStatement.setString(10, user.getMsgSource());
				prepareStatement.setString(11, user.getBrother());
				prepareStatement.setLong(12, user.getCreateAt());
				prepareStatement.setLong(13, user.getUpdateAt());

				prepareStatement.execute();

				result = user.getId();
			} catch (SQLSyntaxErrorException e) {
				System.out.println("增加数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				Log.loggerCreate(LogLevel.ERROR, "增加数据失败");
				result = 0;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return result;
	}

	// 删除数据
	public boolean deleteStudent(long id) {
		createConnection();

		String sql = "DELETE FROM student WHERE id=?";
		boolean result = false;
		if (this.conn != null) {
			try {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setLong(1, id);
				prepareStatement.execute();
				result = true;
			} catch (SQLSyntaxErrorException e) {
				System.out.println("删除数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				Log.loggerCreate(LogLevel.ERROR, "删除数据失败");
				result = false;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return result;
	}

	// 更新数据
	public boolean updateStudent(Student student) {
		createConnection();

		String sql = "UPDATE student SET id=?, name=?, qq_number=?, profession=?, join_date=?, "+
					"school=?, online_number=?, daily_link=?, desire=?, msg_source=?, brother=?, " +
							"create_at=?, update_at=? where id=?";
		boolean result = false;
		if (this.conn != null) {
			try {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setLong(1, student.getId());
				prepareStatement.setString(2, student.getName());
				prepareStatement.setInt(3, student.getQqNumber());
				prepareStatement.setString(4, student.getProfession());
				prepareStatement.setString(5, student.getJoinDate());
				prepareStatement.setString(6, student.getSchool());
				prepareStatement.setString(7, student.getOnlineNumber());
				prepareStatement.setString(8, student.getDailyLink());
				prepareStatement.setString(9, student.getDesire());
				prepareStatement.setString(10, student.getMsgSource());
				prepareStatement.setString(11, student.getBrother());
				prepareStatement.setLong(12, student.getCreateAt());
				prepareStatement.setLong(13, student.getUpdateAt());
				prepareStatement.setLong(14, student.getId());

				prepareStatement.execute();
				result = true;
			} catch (SQLSyntaxErrorException e) {
				System.out.println("更新数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				Log.loggerCreate(LogLevel.ERROR, "更新数据失败");
				result = false;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return result;
	}

	// 查询列表中的数据总数
	public int countTableLength(String field) {
		createConnection();

		int result = 0;
		String sql = "SELECT COUNT(?) FROM student";
		if (this.conn != null) {

			try {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setString(1, field);
				rs = prepareStatement.executeQuery();
				while (rs.next()) {
					result = rs.getInt(1);
				}
			} catch (SQLSyntaxErrorException e) {
				System.out.println("查询数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				// 出现异常,就记录一条error级别的日志在错误日志文件中
				Log.loggerCreate(LogLevel.ERROR, "查询数据失败");
				result = 0;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return result;
	}

	// 通过id查询单条数据
	public Student queryStudentById(long id) {
		createConnection();

		String sql = "SELECT * FROM student WHERE id=?";
		Student student = new Student();
		if (this.conn != null) {
			try {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setLong(1, id);
				rs = prepareStatement.executeQuery();
				while (rs.next()) {
					student.setId(rs.getLong(1));
					student.setName(rs.getString(2));
					student.setQqNumber(rs.getInt(3));
					student.setProfession(rs.getString(4));
					student.setJoinDate(rs.getString(5));
					student.setSchool(rs.getString(6));
					student.setOnlineNumber(rs.getString(7));
					student.setDailyLink(rs.getString(8));
					student.setDesire(rs.getString(9));
					student.setMsgSource(rs.getString(10));
					student.setBrother(rs.getString(11));
					student.setCreateAt(rs.getLong(12));
					student.setUpdateAt(rs.getLong(13));
					
					return student;
				}
			} catch (SQLSyntaxErrorException e) {
				System.out.println("查询数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				Log.loggerCreate(LogLevel.ERROR, "查询数据失败");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return student;
	}

	// 查询表中所有数据,结果返回在一个List中
	public List<Student> queryStudentAll() {
		createConnection();

		List<Student> list = new ArrayList<Student>();
		String sql = "SELECT * FROM student";
		Student student = null;

		if (this.conn != null) {
			try {
				prepareStatement = conn.prepareStatement(sql);
				rs = prepareStatement.executeQuery();
				while (rs.next()) {
					student = new Student();
					student.setId(rs.getLong(1));
					student.setName(rs.getString(2));
					student.setQqNumber(rs.getInt(3));
					student.setProfession(rs.getString(4));
					student.setJoinDate(rs.getString(5));
					student.setSchool(rs.getString(6));
					student.setOnlineNumber(rs.getString(7));
					student.setDailyLink(rs.getString(8));
					student.setDesire(rs.getString(9));
					student.setMsgSource(rs.getString(10));
					student.setBrother(rs.getString(11));
					student.setCreateAt(rs.getLong(12));
					student.setUpdateAt(rs.getLong(13));
					
					list.add(student);
				}
				return list;
			} catch (SQLSyntaxErrorException e) {
				System.out.println("查询数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				Log.loggerCreate(LogLevel.ERROR, "查询数据失败");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return list;
	}

	// 根据姓名查询,查询结果放入List中
	public List<Student> queryStudentByName(String name) {
		createConnection();

		List<Student> list = new ArrayList<Student>();
		String sql = "SELECT * FROM student WHERE name=?";
		Student student = null;

		if (this.conn != null) {
			try {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setString(1, name);
				rs = prepareStatement.executeQuery();
				while (rs.next()) {
					student = new Student();
					student.setId(rs.getLong(1));
					student.setName(rs.getString(2));
					student.setQqNumber(rs.getInt(3));
					student.setProfession(rs.getString(4));
					student.setJoinDate(rs.getString(5));
					student.setSchool(rs.getString(6));
					student.setOnlineNumber(rs.getString(7));
					student.setDailyLink(rs.getString(8));
					student.setDesire(rs.getString(9));
					student.setMsgSource(rs.getString(10));
					student.setBrother(rs.getString(11));
					student.setCreateAt(rs.getLong(12));
					student.setUpdateAt(rs.getLong(13));
					list.add(student);
				}
				return list;
			} catch (SQLSyntaxErrorException e) {
				System.out.println("查询数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				Log.loggerCreate(LogLevel.ERROR, "查询数据失败");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return list;
	}

	// 清空整个表中数据
	public boolean clearTable() {
		createConnection();

		boolean result = false;
		String sql = "TRUNCATE TABLE student";
		if (conn != null) {
			try {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.execute();
				result = true;
			} catch (SQLSyntaxErrorException e) {
				System.out.println("清空数据失败,请检查你的SQL语句");
				System.out.println("原因:" + e.getMessage().toString());
				Log.loggerCreate(LogLevel.ERROR, "清空数据失败");
				result = false;
			} catch (SQLException e) {
				e.printStackTrace();
				result = false;
			} finally {
				closeConnection(prepareStatement, conn, rs);
			}
		}
		return result;
	}

	// 关闭链接
	public void closeConnection(PreparedStatement prepareStatement, Connection conn, ResultSet rs) {
		try {
			if (prepareStatement != null)
				prepareStatement.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
