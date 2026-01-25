package top.toobee.spring.service;

import java.net.InetAddress;
import org.springframework.stereotype.Service;

@Service
public final class BanIpService {

    public void banLogin(InetAddress ip, String username) {}

    // return 0 if not banned, otherwise the remaining ban time in seconds
    public int banTime(InetAddress ip, String username) {
        return 0;
    }
}
