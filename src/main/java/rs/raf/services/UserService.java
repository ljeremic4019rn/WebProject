package rs.raf.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import rs.raf.models.User;
import rs.raf.repositories.IRepos.UserRepository;

import javax.inject.Inject;
import java.util.List;

public class UserService {

    @Inject
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public User findUser(Integer id) {
        return userRepository.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> allUsers() {
        return userRepository.allUsers();
    }

    public User editUser(User user) {
        return userRepository.editUser(user);
    }

    public void deleteUser(Integer id){
        this.userRepository.deleteUser(id);
    }

    public void activateUser(Integer id){
        this.userRepository.activateUser(id);
    }

    public void deactivateUser(Integer id){
        this.userRepository.deactivateUser(id);
    }


    public boolean isAuthorized(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String email = jwt.getSubject();

        User user = this.userRepository.findUserByEmail(email);
        if (user == null) {
            return false;
        }

        return true;
    }
}
