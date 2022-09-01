package imorochi;

import imorochi.Entities.Post;
import imorochi.Utils.Requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static imorochi.Contansts.PostEndpoints.*;

public class PostsTest {

    @Test
    public void getPostsTest(){
        Response response = Requests.get(GET_POSTS);
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }

    @Test
    public void getPostWithIdTest(){
        Response response = Requests.getWithId(GET_POST,"1");
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("userId", Matchers.equalTo(1));
        response.then().assertThat().body("id", Matchers.equalTo(1));
        response.then().assertThat().body("title", Matchers.equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
        response.then().assertThat().body("body", Matchers.equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    public void createPostTest() throws JsonProcessingException {
        Post post = new Post();
        post.setUserId(1);
        post.setId(101);
        post.setTitle("Title");
        post.setBody("Description");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(post);

        Response response = Requests.post(POST_POST, payload);
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("userId", Matchers.equalTo(post.getUserId()));
        response.then().assertThat().body("id", Matchers.equalTo(post.getId()));
        response.then().assertThat().body("title", Matchers.equalTo(post.getTitle()));
        response.then().assertThat().body("body", Matchers.equalTo(post.getBody()));
    }

    @Test
    public void putPostTest() throws JsonProcessingException {
        Post post = new Post();
        post.setUserId(1);
        post.setId(1);
        post.setTitle("Title modified");
        post.setBody("Description modified");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(post);

        Response response = Requests.put(PUT_POST, "1", payload);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("userId", Matchers.equalTo(post.getUserId()));
        response.then().assertThat().body("id", Matchers.equalTo(post.getId()));
        response.then().assertThat().body("title", Matchers.equalTo(post.getTitle()));
        response.then().assertThat().body("body", Matchers.equalTo(post.getBody()));
    }

    @Test
    public void deletePostTest(){
        Response response = Requests.delete(DELETE_POST, "1");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("isEmpty()", Matchers.is(true));
    }

}
