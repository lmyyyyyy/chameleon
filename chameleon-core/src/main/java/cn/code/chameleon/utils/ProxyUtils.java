package cn.code.chameleon.utils;

import cn.code.chameleon.proxy.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Proxy Validater
 */
public class ProxyUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProxyUtils.class);

    public static boolean validateProxy(Proxy p) {
        Socket socket = null;
        try {
            socket = new Socket();
            InetSocketAddress endpointSocketAddr = new InetSocketAddress(p.getHost(), p.getPort());
            socket.connect(endpointSocketAddr, 3000);
            return true;
        } catch (IOException e) {
            logger.warn("FAIL - socket connect fail!  remote: " + p);
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.warn("Error occurred while closing socket of validating proxy", e);
                }
            }
        }

    }

}
