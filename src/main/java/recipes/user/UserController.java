package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    ResponseEntity registerUser(@Valid @RequestBody User user) {
        boolean registered = userService.registerUser(user);
        HttpStatus httpStatus = registered ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity(httpStatus);
    }
}
