package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {

    @Autowired // DI
    private BoardPersistRepository boardPersistRepository;

    @Autowired // DI
    private EntityManager em;

    @Test
    public void updateById_test(){
        // given
        int id = 1;
        String title = "제목수정1";

        // when
        Board board = boardPersistRepository.findById(id);
        board.setTitle(title);
        em.flush(); // 눈으로 보려면
    }

    @Test
    public void deleteByIdV2_test(){
        // given
        int id = 1;

        // when
        boardPersistRepository.deleteByIdV2(id); // 트랜잭션이 종료될 때 쿼리가 날아간다.

        // 버퍼에 쥐고 있는 쿼리를 즉시 전송
        em.flush();
    }

    @Test
    public void deleteById_test(){
        // given
        int id = 1;

        // when
        boardPersistRepository.deleteById(id); // 트랜잭션이 종료될 때 쿼리가 날아간다.
    }

    @Test
    public void findById_test() {
        // given
        int id = 1;

        // when
        Board board = boardPersistRepository.findById(id);

        // then
        assertThat(board.getTitle()).isEqualTo("제목1");
        assertThat(board.getContent()).isEqualTo("내용1");
    }

    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardPersistRepository.findAll();

        // then
        System.out.println("findAll_test/size : " + boardList.size());
        System.out.println("findAll_test/username : " + boardList.get(2).getUsername());

        // org.assertj.core.api
        assertThat(boardList.size()).isEqualTo(4);
        assertThat(boardList.get(2).getUsername()).isEqualTo("ssar");
    }

    @Test
    public void save_test(){
        // given
        Board board = new Board("제목5", "내용5", "ssar");

        // when
        boardPersistRepository.save(board);
        System.out.println("save_test :" + board);

        // then
    }
}