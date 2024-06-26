package com.heon.sns.service;


import com.heon.sns.exception.ErrorCode;
import com.heon.sns.exception.SnsApplicationException;
import com.heon.sns.fixture.PostEntityFixture;
import com.heon.sns.fixture.UserEntityFixture;
import com.heon.sns.model.entity.PostEntity;
import com.heon.sns.model.entity.UserEntity;
import com.heon.sns.repository.PostEntityRepository;
import com.heon.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostEntityRepository postEntityRepository;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 포스트작성이_성공한경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        assertThatCode(() -> postService.create(title, body, userName))
                .doesNotThrowAnyException();
    }

    @Test
    void 포스트작성시_요청한유저가_존재하지않는경우() {
        // Given
        String title = "title";
        String body = "body";
        String userName = "userName";

        // When
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> postService.create(title, body, userName))
                .isInstanceOf(SnsApplicationException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_FOUND);
    }

    @Test
    void 포스트수정이_성공한경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.save(any())).thenReturn(postEntity);

        assertThatCode(() -> postService.modify(title, body, userName, postId))
                .doesNotThrowAnyException();
    }

    @Test
    void 포스트수정시_포스트가_존재하지_않는경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.modify(title, body, userName, postId))
                .isInstanceOf(SnsApplicationException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.POST_NOT_FOUND);
    }

    @Test
    void 포스트수정시_권한이_없는_경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity writer = UserEntityFixture.get("otherUser", "password", 2);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        assertThatThrownBy(() -> postService.modify(title, body, userName, postId))
                .isInstanceOf(SnsApplicationException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_PERMISSION);
    }

    @Test
    void 포스트삭제에_성공한경우() {
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        assertThatCode(() -> postService.delete(userName, 1))
                .doesNotThrowAnyException();
    }

    @Test
    void 포스트삭제시_포스트가_존재하지_않는경우() {
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.delete(userName, 1))
                .isInstanceOf(SnsApplicationException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.POST_NOT_FOUND);
    }

    @Test
    void 포스트삭제시_권한이_없는_경우() {
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity writer = UserEntityFixture.get("otherUser", "password", 2);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        assertThatThrownBy(() -> postService.delete(userName, 1))
                .isInstanceOf(SnsApplicationException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_PERMISSION);
    }

    @Test
    void 피드목록요청에_성공한경우() {
        Pageable pageable = mock(Pageable.class);
        when(postEntityRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThatCode(() -> postService.list(pageable))
                .doesNotThrowAnyException();
    }

    @Test
    void 내_피드목록요청에_성공한경우() {
        Pageable pageable = mock(Pageable.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(userEntityRepository.findByUserName(any())).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findAllByUser(any(), eq(pageable))).thenReturn(Page.empty());

        assertThatCode(() -> postService.my("", pageable))
                .doesNotThrowAnyException();
    }

}
