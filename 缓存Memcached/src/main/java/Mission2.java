import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.*;

public class Mission2 {
    public static void main(String args[]){
        int wantToId = 1;
        String result = SearchCached(wantToId);
        if(result == null){
            System.out.println("Memcached中不存在,查询数据库");
            result = SearchMysql(wantToId);
            System.out.println("数据库中找到"+result);
        }
        else{
            System.out.println("Memcached中找到\n"+result);
        }
    }

    public static String SearchCached(int id){
        try{
            String Memcached_host = "";
            MemcachedClient client = new MemcachedClient(new InetSocketAddress(Memcached_host, 11211));
            String result = (String) client.get(String.valueOf(id));
            client.shutdown();
            return result;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String SearchMysql(int key){
        String db_host = "";
        String db_port = "";
        String db_name = "";
        String user="root";
        String password="123456";
        String url="jdbc:mysql://="+db_host+":"+db_port+"/"+db_name+"?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
        try {
            //1. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 获取连接

            Connection conn= DriverManager.getConnection(url,user,password);
            System.out.println("连接成功");

            Statement statement = conn.createStatement();
            String sql = "Select * from host_point where Hid = "+String.valueOf(key);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                return resultSet.getString("Hhost");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
