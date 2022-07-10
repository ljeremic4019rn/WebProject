package rs.raf.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.models.User;
import rs.raf.repositories.IRepos.UserRepository;

import javax.inject.Inject;
import java.util.Date;
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

    public List<User> usersByPage(Integer pageNum) {
        return userRepository.usersByPage(pageNum);
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


    public String login(String email, String password) {

        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUserByEmail(email);
        if(user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withClaim("lastname", user.getLastname())
                .withClaim("role", user.getRole().toString())
                .withClaim("status", user.getStatus().toString())
                .sign(algorithm);
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
