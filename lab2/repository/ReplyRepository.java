package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Reply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends CrudRepository<Reply,Long> {
    List<Reply> findByPidOrderByCreateTime (Long pid);
    List<Reply> findByPidAndReplyer(Long pid,String replyer);
    List<Reply> findByPid (Long pid);
}
