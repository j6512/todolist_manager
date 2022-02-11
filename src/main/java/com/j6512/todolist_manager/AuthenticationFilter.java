package com.j6512.todolist_manager;

import com.j6512.todolist_manager.controllers.AuthenticationController;
import com.j6512.todolist_manager.models.User;
import com.j6512.todolist_manager.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whiteList = Arrays.asList("/login", "/register");

    private static boolean isWhiteListed(String path) {
        for (String pathRoot : whiteList) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // don't require sign in for white listed pages
        if (isWhiteListed(request.getRequestURI())) {
            return true; // indicates that the request may proceed
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        // if the user is logged in
        if (user != null) {
            return true;
        }

        // the user isn't logged in
        response.sendRedirect("/login");
        return false;
    }
}
