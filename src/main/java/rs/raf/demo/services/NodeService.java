package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.Node;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.NodeRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NodeService implements IService<Node, Long>{

    private NodeRepository nodeRepository;

    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Node save(Node node) {
        return nodeRepository.save(node);
    }

    @Override
    public Optional<Node> findById(Long id) {
        return nodeRepository.findById(id);
    }

    @Override
    public List<Node> findAll() {
        return nodeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        nodeRepository.deleteById(id);
    }

    public Optional<Node> findByIdAndUser (Long id, User user){
        return nodeRepository.findByIdAndCreatedBy(id, user);
    }

    public List<Node> search(User user, String name, List<String> status, Date dateFrom, Date dateTo){
        List<Node> nodes = nodeRepository.findAllByCreatedBy(user);
        if(name != null){
            for (Node n: nodes){
                if (n.getName().equals(name))
                    continue;
                nodes.remove(n);
            }
        }
        if(status != null){
            for (Node n: nodes){
                if (status.contains(n.getStatus()))
                    continue;
                nodes.remove(n);
            }
        }
        if(dateFrom != null && dateTo != null){
            for (Node n: nodes){
                // TODO
            }
        }

        return null;
    }

    public void deleteNode(Node node){
        node.setActive(false);
        save(node);
    }
}
