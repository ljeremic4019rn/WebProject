package rs.raf;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.repositories.IRepos.*;
import rs.raf.repositories.SQLRepos.*;
import rs.raf.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class MainApp extends ResourceConfig {

    public MainApp() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(SqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(SqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(SqlArticleRepository.class).to(ArticleRepository.class).in(Singleton.class);
                this.bind(SqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(SqlTagRepository.class).to(TagRepository.class).in(Singleton.class);
                this.bind(SqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);


                this.bindAsContract(CategoryService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(ArticleService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(CommentService.class);
            }
        };
        register(binder);

        packages("rs.raf.resources");
    }
}
