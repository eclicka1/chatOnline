package chatonline.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import chatonline.utility.AskFrd;
import chatonline.utility.Info;
import chatonline.utility.InfoWithPhoto;
import chatonline.utility.User;

/**
 * @ClassName: Access
 * @Description:access to database
 * @author Li
 * @date 2012-4-22 ÉÏÎç11:01:14
 * 
 */
public class Access {
	public static synchronized boolean register(User auser, String apswd) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		String sqlstr;
		int bid = 0;
		try {
			sqlstr = "select max(id)+1 as maxid from chatuser";
			stmt = con.prepareStatement(sqlstr);
			reSet = stmt.executeQuery();
			if (reSet.next()) {
				bid = reSet.getInt("maxid");
				auser.iId=bid;
			}
			DBMSTool.closeStatement(stmt);

			sqlstr = "insert into chatuser values(?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(sqlstr);
			stmt.setInt(1, bid);
			stmt.setString(2, auser.iName);
			stmt.setString(3, apswd);
			stmt.setString(4, auser.iAddress);
			stmt.setString(5, df.format(auser.iBirthday));
			stmt.setString(6, auser.iSignature);
			stmt.setInt(7, auser.iSex);
			stmt.setInt(8, auser.iState);
			stmt.setString(9, df.format(auser.iRegisterDay));

			if (1 == stmt.executeUpdate()) {
				DBMSTool.closeStatement(stmt);
				sqlstr = "insert into frdgroup values(?,'friend')";
				stmt = con.prepareStatement(sqlstr);
				stmt.setInt(1, bid);
				if (stmt.executeUpdate() == 1)
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeResultSet(reSet);
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
		return false;
	}

	public static boolean login(int aid, String apswd, int astate) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		String sqlstr = "select pswd from chatuser where id=" + aid;
		try {
			stmt = con.prepareStatement(sqlstr);
			reSet = stmt.executeQuery();
			if (reSet.next()) {
				if (apswd.equals(reSet.getString(1))) {
					DBMSTool.closeStatement(stmt);

					sqlstr = "update chatuser set state=? where id=?";
					stmt = con.prepareStatement(sqlstr);
					stmt.setInt(1, astate);
					stmt.setInt(2, aid);
					stmt.executeUpdate();
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeResultSet(reSet);
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
		return false;
	}

	public static void getFrdList(int aid, Map<String, List<User>> amap) {
		List<User> blist;
		User buser;
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		String sqlstr = "select frdid,name,remark,signature,state,ingroup "
				+ "from chatuser,frdlist " + "where frdid=id and myid=" + aid;
		try {
			stmt = con.prepareStatement(sqlstr);
			reSet = stmt.executeQuery();
			while (reSet.next()) {
				String bgroup = reSet.getString("ingroup");
				if (!amap.containsKey(bgroup)) {
					blist = new LinkedList<User>();
					amap.put(bgroup, blist);
				}
				buser = new User(11, "lhl", "lihailin", "author", User.ONLINE);
				buser = new User(reSet.getInt("frdid"),
						reSet.getString("name"), reSet.getString("remark"),
						reSet.getString("signature"), reSet.getInt("state"));
				amap.get(bgroup).add(buser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeResultSet(reSet);
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void getNotes(int aid, List<Info> alist,
			List<InfoWithPhoto> alist2) {
		Info binfo;
		InfoWithPhoto binfo2;
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		String sqlstr = "select fromid,toid,sendtime,content, photonum "
				+ "from info,msgleft "
				+ "where infoid=id and toid=? order by sendtime";
		try {
			stmt = con.prepareStatement(sqlstr);
			stmt.setInt(1, aid);
			reSet = stmt.executeQuery();
			while (reSet.next()) {
				if (0 < reSet.getInt("photonum")) {
					binfo2=new InfoWithPhoto(reSet.getInt("fromid"),
							reSet.getInt("toid"),
							reSet.getDate("sendtime"));
					binfo2.initFromTxtLine(reSet.getString("content"));
					binfo2.readPhotoFile();
					alist2.add(binfo2);
				} else {
					binfo = new Info(reSet.getInt("fromid"),
							reSet.getInt("toid"), reSet.getDate("sendtime"),
							reSet.getString("content"));
					alist.add(binfo);
				}
			}
			DBMSTool.closeStatement(stmt);

			sqlstr = "delete from msgleft where touserid=?";
			stmt = con.prepareStatement(sqlstr);
			stmt.setInt(1, aid);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeResultSet(reSet);
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void getAskFrd(int aid, List<AskFrd> alist) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		String sqlstr = "select * from askfrd where toid=?";
		try {
			stmt = con.prepareStatement(sqlstr);
			stmt.setInt(1, aid);
			reSet = stmt.executeQuery();
			while (reSet.next()) {
				alist.add(new AskFrd(reSet.getInt("fromid"), reSet
						.getInt("toid"), reSet.getString("msg"), reSet
						.getDate("asktime")));
			}
			DBMSTool.closeStatement(stmt);

			sqlstr = "delete from askfrd where toid=?";
			stmt = con.prepareStatement(sqlstr);
			stmt.setInt(1, aid);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeResultSet(reSet);
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void findFrd(List<User> alist, User auser) {
		User buser;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		StringBuilder bbld = new StringBuilder();
		bbld.append("select * from chatuser where 1=1");
		if (auser.iId != -1) {
			bbld.append(" and id=" + auser.iId);
		} else {
			if (auser.iName != null) {
				bbld.append(" and name=" + auser.iName);
			}
			if (auser.iAddress != null) {
				bbld.append(" and address=" + auser.iAddress);
			}
			if (auser.iBirthday != null) {
				bbld.append(" and birthday=" + df.format(auser.iBirthday));
			}
			if (auser.iSignature != null) {
				bbld.append(" and signature=" + auser.iSignature);
			}
			if (auser.iSex != -1) {
				bbld.append(" and sex=" + auser.iSex);
			}
			if (auser.iState != -1) {
				bbld.append(" and state=" + auser.iState);
			}
			if (auser.iRegisterDay != null) {
				bbld.append(" and registerday" + df.format(auser.iRegisterDay));
			}
		}
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		try {
			stmt = con.prepareStatement(bbld.toString());
			reSet = stmt.executeQuery();
			while (reSet.next()) {
				buser = new User(reSet.getInt("id"), reSet.getString("name"),
						reSet.getString("name"), reSet.getString("address"),
						reSet.getDate("birthday"),
						reSet.getString("signature"), reSet.getInt("sex"),
						reSet.getInt("state"), reSet.getDate("registerday"));
				alist.add(buser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeResultSet(reSet);
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void addAskForFrd(AskFrd aask) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "insert into askfrd(fromid,toid,msg,asktime) values(?,?,?,?)";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setInt(1, aask.ifromid);
			stmt.setInt(2, aask.itoid);
			stmt.setString(3, aask.imsg);
			stmt.setString(4, df.format(aask.idate));
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void addFrd(int afrom, int ato) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "insert into frdlist values(?,?,'friend','none',1)";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setInt(1, afrom);
			stmt.setInt(2, ato);
			stmt.addBatch();
			stmt.setInt(1, ato);
			stmt.setInt(2, afrom);
			stmt.addBatch();
			stmt.executeBatch();
			stmt.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void delFrd(int aid, int bid) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "delete from frdlist where myid=? and frdid=?";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setInt(1, aid);
			stmt.setInt(2, bid);
			stmt.addBatch();
			stmt.setInt(1, bid);
			stmt.setInt(2, aid);
			stmt.addBatch();
			stmt.executeBatch();
			stmt.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void renameFrd(int aone, int afrd, String aname) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "update frdlist set remark=? where myid=? and frdid=?";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setString(1, aname);
			stmt.setInt(2, aone);
			stmt.setInt(3, afrd);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public synchronized static void addInfo(Info ainfo, boolean aIsSent) {
		int bid;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		String bSqlStr = "insert into "
				+ "info(fromid,toid,content,sendtime,photonum) "
				+ "values(?,?,?,?,?)";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setInt(1, ainfo.iFromId);
			stmt.setInt(2, ainfo.iToId);
			stmt.setString(3, ainfo.iInfo);
			stmt.setString(4, df.format(ainfo.iDate));
			stmt.setInt(5, 0);
			stmt.executeUpdate();

			if (!aIsSent) {
				DBMSTool.closeStatement(stmt);
				bSqlStr = "select max(id) as maxid from info";
				stmt = con.prepareStatement(bSqlStr);
				reSet = stmt.executeQuery();
				if (reSet.next()) {
					bid = reSet.getInt("maxid");
					DBMSTool.closeStatement(stmt);

					bSqlStr = "insert into msgleft values(?,?)";
					stmt = con.prepareStatement(bSqlStr);
					stmt.setInt(1, bid);
					stmt.setInt(2, ainfo.iToId);
					stmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public synchronized static void addInfoWithPhoto(InfoWithPhoto ainfo,
			boolean aIsSent) {
		int bid;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		ResultSet reSet = null;
		String bSqlStr = "insert into "
				+ "info(fromid,toid,content,sendtime,photonum) "
				+ "values(?,?,?,?,?)";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setInt(1, ainfo.iFromId);
			stmt.setInt(2, ainfo.iToId);
			stmt.setString(3, ainfo.toTxtLine());
			stmt.setString(4, df.format(ainfo.idate));
			stmt.setInt(5, ainfo.getPhotoSize());
			stmt.executeUpdate();

			if (!aIsSent) {
				DBMSTool.closeStatement(stmt);
				bSqlStr = "select max(id) as maxid from info";
				stmt = con.prepareStatement(bSqlStr);
				reSet = stmt.executeQuery();
				if (reSet.next()) {
					bid = reSet.getInt("maxid");
					DBMSTool.closeStatement(stmt);

					bSqlStr = "insert into msgleft values(?,?)";
					stmt = con.prepareStatement(bSqlStr);
					stmt.setInt(1, bid);
					stmt.setInt(2, ainfo.iToId);
					stmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void addGroup(int aid, String aname) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "insert into  frdgroup values(?,?)";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setInt(1, aid);
			stmt.setString(2, aname);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void delGroup(int aid, String aname) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "update frdlist set ingroup='friend' where ingroup=? and myid=?";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setString(1, aname);
			stmt.setInt(2, aid);
			stmt.executeUpdate();
			DBMSTool.closeStatement(stmt);

			bSqlStr = "delete from frdgroup where userid=? and name=?";
			stmt = con.prepareStatement(bSqlStr);
			stmt.setInt(1, aid);
			stmt.setString(2, aname);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void renameGroup(int aid, String aold, String anew) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "update frdgroup set name=? where userid=? and name=?";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setString(1, anew);
			stmt.setInt(2, aid);
			stmt.setString(3, aold);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void moveToGroup(int aid, int afrd, String agroup) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String bSqlStr = "update frdlist set ingroup=? where myid=? and frdid=?";
		try {
			stmt = con.prepareStatement(bSqlStr);
			stmt.setString(1, agroup);
			stmt.setInt(2, aid);
			stmt.setInt(3, afrd);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBMSTool.closeStatement(stmt);
			DBMSTool.closeConnection(con);
		}
	}

	public static void setUserState(int aid, int astate) {
		Connection con = DBMSTool.getConnection();
		PreparedStatement stmt = null;
		String sqlstr;

		sqlstr = "update chatuser set state=? where id=?";
		try {
			stmt = con.prepareStatement(sqlstr);
			stmt.setInt(1, astate);
			stmt.setInt(2, aid);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
