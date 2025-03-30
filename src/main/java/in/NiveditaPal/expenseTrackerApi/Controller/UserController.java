package in.NiveditaPal.expenseTrackerApi.Controller;

import in.NiveditaPal.expenseTrackerApi.Model.User;
import in.NiveditaPal.expenseTrackerApi.Model.UserModel;
import in.NiveditaPal.expenseTrackerApi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> readUser(){
         return new ResponseEntity<User>(userService.readUser(),HttpStatus.OK);
    }
    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel user){
        User user1 = userService.updateUser(user);
        return new ResponseEntity<User>(user1,HttpStatus.OK);
    }
    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser(){
        userService.deleteUser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
