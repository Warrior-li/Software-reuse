import net.spy.memcached.MemcachedClient;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.sql.*;

public class Mission1 {
    public static void main(String args[]) throws IOException, InterruptedException {
        while(true){
            System.out.println("更新缓存");
            SearchMessage();
            Thread.sleep(60000);
        }
    }

    //连接数据库并且查询
    public static void SearchMessage(){
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
            String sql = "Select * from host_point order by Hhost asc";
            ResultSet resultSet = statement.executeQuery(sql);
            int point = 3;
            while(resultSet.next()&&point!=0){
                int id = resultSet.getInt("Hid");
                String host = resultSet.getString("Hhost");
                SetKeyAndValue(id,host);
                point = point - 1;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }


    //MemCached
    public static void SetKeyAndValue(int key,String value) throws IOException {
        try {
            MemcachedClient client = new MemcachedClient(new InetSocketAddress("121.199.36.239", 11211));
            client.set(String.valueOf(key), 60, value);
            System.out.println("insert success \n" + key + ":" + client.get(String.valueOf(key)));
            client.shutdown();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
