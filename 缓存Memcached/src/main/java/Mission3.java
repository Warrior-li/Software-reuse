import java.sql.*;

public class Mission3 {
    public static void main(String main[]){
        UpdateMysql(2,100);
    }

    public static void UpdateMysql(int id,int hot){
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
            String sql = "UPDATE host_point set Hhot = "+String.valueOf(hot)+" where Hid = "+String.valueOf(id);
            int resultSet = statement.executeUpdate(sql);
            if(resultSet>0){
                System.out.println("更新成功");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
