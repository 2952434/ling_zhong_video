package lingzhong.com.svideo.controller.impl;

import lingzhong.com.svideo.controller.CommentController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentControllerImpl implements CommentController {

    @Override
    public String test() {
        return "test";
    }
}
