package org.danilkha.controllers.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.danilkha.dto.PostDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "feed-api", value = "/feed-api")
public class FeedApiServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {;
        resp.setStatus(HttpServletResponse.SC_OK);
        List<PostResponse> posts = new ArrayList<>();

        int page = Integer.parseInt(req.getParameter("page"));

        for (int i = 0; i < 10; i++) {
            posts.add(
                    new PostResponse(
                            UUID.randomUUID(),
                            "danil "+i,
                            "profile/<uuid>",
                            "/pictures/AUIJYZsoQaomHw==1699868967299ZGFuaWxraGEt0KHQvdC40LzQvtC6INGN0LrRgNCw0L3QsCAyMDIzLTAzLTI0IDIzMDE0NC5wbmc=.png\"",
                            "page %s 14.11.2023".formatted(page),
                            "/posts/post0/macos-monterey-wwdc-21-stock-dark-mode-5k-6016x6016-5585.jpg",
                            "text",
                            20,
                            i % 2 == 0,
                            "cars/bmw",
                            "BMW"

                    )
            );
        }

        PostListResponse response = new PostListResponse(
                page,
                posts
        );
        System.out.println();
        resp.getWriter().write(objectMapper.writeValueAsString(response));
    }
}
