package in.NiveditaPal.expenseTrackerApi.Service;

import in.NiveditaPal.expenseTrackerApi.Exceptions.ItemAlreadyExitsException;
import in.NiveditaPal.expenseTrackerApi.Exceptions.ResourceNotFoundException;
import in.NiveditaPal.expenseTrackerApi.Model.User;
import in.NiveditaPal.expenseTrackerApi.Model.UserModel;
import in.NiveditaPal.expenseTrackerApi.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserModel user) {
        if(userRepository.existsByEmail(user.getEmail())) throw new ItemAlreadyExitsException("User is already registered with the email: "+ user.getEmail());
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        user1.setPassword(bcryptEncoder.encode(user1.getPassword()));
        return userRepository.save(user1);
    }

    @Override
    public User readUser() {
        Long id = getLoggedInUser().getId();
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found for the id: "+id));
    }

    @Override
    public User updateUser(UserModel user) {
        User user1 = readUser();
        user1.setName(user.getName()!=null?user.getName():user1.getName());
        user1.setEmail(user.getEmail()!=null?user.getEmail():user1.getEmail());
        user1.setPassword(user.getPassword()!=null?bcryptEncoder.encode(user.getPassword()):user1.getPassword());
        user1.setAge(user.getAge()!=null?user.getAge():user1.getAge());
        return userRepository.save(user1);
    }

    @Override
    public void deleteUser() {
        User user = readUser();
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email:"+ email));
    }
}
