import redis.clients.jedis.Jedis;

public class SetAndDelete {
    public static void main(String args[]){
        Jedis jedis =new Jedis("121.199.36.239");
        jedis.auth("123456");
        System.out.println("连接成功");
        jedis.set("upc","1801040403");
        System.out.println("key:upc,value:"+jedis.get("upc"));
    }
}
