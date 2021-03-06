package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.resourceBean;

public class Dao {
		
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
	
		public void con() throws SQLException{//建立mysql连接
			String url="jdbc:mysql://127.0.0.1:3306/project";
			String usr="root";
			String psd="lzw0201";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(url,usr,psd);
				st=con.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void destroyQuery(){//断开mysql连接
			try {
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void destroyUpdate(){//断开mysql连接
			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public List<resourceBean> search(String title) throws SQLException{
			this.con();
			List<resourceBean> relist=new ArrayList<resourceBean>();
			String sql_search="select * from resourceinfo where title = '"+title+"'";
			rs=st.executeQuery(sql_search);
				while(rs.next()){
					resourceBean r=new resourceBean();
					r.setId(rs.getInt("id"));
					r.setTitle(rs.getString("title"));
					r.setSize(rs.getString("size"));
					r.setDate(rs.getString("date"));
					r.setContent(rs.getString("content"));
					r.setLocation(rs.getString("location"));
					r.setHot(rs.getInt("hot"));
					r.setUsr(rs.getString("uploadusr"));
					r.setName(rs.getString("name"));
					relist.add(r);
				}
			this.destroyQuery();
			return relist;
		}
		
		public void upload(String title,String content,String size,String location,String name) throws SQLException{
			this.con();
			String sql_upload="insert into resourceinfo values(null,'"
									+title+"','"+size+"',now(),'"+location+"','"+content+"',0,'lizhiwei','"+name+"')";
			st.executeUpdate(sql_upload);
			this.destroyUpdate();
		}
		
		public List<resourceBean> hotresource() throws SQLException{
			this.con();
			List<resourceBean> relist=new ArrayList<resourceBean>();
			String sql_hot="select * from resourceinfo order by hot DESC";
			rs=st.executeQuery(sql_hot);
			while(rs.next()){
				resourceBean r=new resourceBean();
				r.setId(rs.getInt("id"));
				r.setTitle(rs.getString("title"));
				r.setSize(rs.getString("size"));
				r.setDate(rs.getString("date"));
				r.setContent(rs.getString("content"));
				r.setLocation(rs.getString("location"));
				r.setHot(rs.getInt("hot"));
				r.setUsr(rs.getString("uploadusr"));
				r.setName(rs.getString("name"));
				relist.add(r);
			}
			this.destroyQuery();
			return relist;
		}
		
		public List<resourceBean> content(int id) throws SQLException{
			this.con();
			List<resourceBean> relist=new ArrayList<resourceBean>();
			String sql_search="select * from resourceinfo where id="+id+"";
			rs=st.executeQuery(sql_search);
				while(rs.next()){
					resourceBean r=new resourceBean();
					r.setId(rs.getInt("id"));
					r.setTitle(rs.getString("title"));
					r.setSize(rs.getString("size"));
					r.setDate(rs.getString("date"));
					r.setContent(rs.getString("content"));
					r.setLocation(rs.getString("location"));
					r.setHot(rs.getInt("hot"));
					r.setUsr(rs.getString("uploadusr"));
					r.setName(rs.getString("name"));
					relist.add(r);
				}
			this.destroyQuery();
			return relist;
		}
		
		public void addhot(int id) throws SQLException{
			this.con();
			String sql_hot="update resourceinfo set hot=hot+1 where id="+id+"";
			st.executeUpdate(sql_hot);
			this.destroyUpdate();
		}
		
		public String login(String usr,String psd) throws SQLException{
			this.con();
			String result=null;
			String sql_login="select psd from usr where id="+usr+"";
		    rs=st.executeQuery(sql_login);
		    while(rs.next()){
				if (rs.getString("psd").equals(psd)) {
					result = "success";
					System.out.println("a");
					break;
				} else {
					result = "psd_error";
					System.out.println("b");
					break;
				}
		    }
		    if(!rs.next()){
		    	result="id_error";
		    	System.out.println("c");
		    }
		    return result;
		}
}
