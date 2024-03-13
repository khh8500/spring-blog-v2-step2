package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardPersistRepository {
    private final EntityManager em;

    @Transactional
    public void updateById(int id, BoardRequest.UpdateDTO reqDTO){
        Board board = findById(id); // 영속화된 객체 상태를 변경하고 트랜잭션을 변경하면 더티체킹
        board.update(reqDTO);
    } // 더티체킹

    @Transactional
    public void deleteByIdV2(int id){
        Board board = findById(id);
        em.remove(board); // PC에 객체 지우고, (트랜잭션 종료 시) 삭제 쿼리 전송
    } // commit, rollback

    @Transactional
    public void deleteById(int id){
        Query query = em.createQuery("delete from Board b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Board findById(int id){
        Board board = em.find(Board.class, id);
        return board;
    }

    public List<Board> findAll(){
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        return query.getResultList();
    }

    @Transactional
    public Board save(Board board) {
        // 1. 비영속 객체
        em.persist(board);
        // 2. board -> 영속 객체
        return board;
    }

}
