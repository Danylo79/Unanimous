package dev.dankom.unanimous.http;

import dev.dankom.security.hash.hashers.Sha256;
import dev.dankom.unanimous.UnanimousServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationRest {
    @PostMapping("/profile/login")
    public void login(String username, String pin, HttpServletResponse res) {
        try {
            if (UnanimousServer.getInstance().getClassManager().login(username, pin)) {
                res.addCookie(new Cookie("unanimous", new Sha256().hash(username + "-" + pin)));
                res.setStatus(200);
            } else {
                res.sendError(401);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
