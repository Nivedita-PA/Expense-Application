package in.NiveditaPal.expenseTrackerApi.Service;

import in.NiveditaPal.expenseTrackerApi.Model.User;
import in.NiveditaPal.expenseTrackerApi.Model.UserModel;


public interface UserService {
    User createUser(UserModel user);
    User readUser();
    User updateUser(UserModel user);
    void deleteUser();
    User getLoggedInUser();
}
