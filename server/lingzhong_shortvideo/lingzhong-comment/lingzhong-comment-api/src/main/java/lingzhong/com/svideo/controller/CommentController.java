package lingzhong.com.svideo.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/lingzhong/svideo/comment")
public interface CommentController {

    @RequestMapping("test")
    public String test();
}
