package cn.code.chameleon.proxy;

/**
 * 代理实体
 *
 * @author liumingyu
 * @create 2018-04-08 下午12:16
 */
public class Proxy {

    /** 地址 */
    private String host;

    /** 端口 */
    private Integer port;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    public Proxy(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public Proxy(String host, Integer port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proxy proxy = (Proxy) o;

        if (!host.equals(proxy.host)) return false;
        if (!port.equals(proxy.port)) return false;
        if (username != null ? !username.equals(proxy.username) : proxy.username != null) return false;
        return password != null ? password.equals(proxy.password) : proxy.password == null;
    }

    @Override
    public int hashCode() {
        int result = host.hashCode();
        result = 31 * result + port.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
