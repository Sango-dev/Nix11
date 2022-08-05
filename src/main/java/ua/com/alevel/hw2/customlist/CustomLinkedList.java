package ua.com.alevel.hw2.customlist;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.model.TechProduct;

import java.util.*;
import java.util.function.Consumer;

public class CustomLinkedList<T extends TechProduct> implements Iterable<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLinkedList.class);
    private Map<Integer, Integer> versMap;
    private Node head;
    private Node tail;

    @Setter
    @Getter
    private final class Node {
        private T product;
        private int version;
        private Date date;

        Node next;
        Node prev;

        public Node(T product, int version, Date date, Node next, Node prev) {
            this.product = product;
            this.version = version;
            this.date = date;
            this.next = next;
            this.prev = prev;
        }

    }

    private Node createNode(T product, int version) {
        return new Node(product, version, new Date(), null, null);
    }

    public CustomLinkedList() {
        versMap = new HashMap<>();
    }

    private boolean isEmpty() {
        return head == null && tail == null;
    }

    private void addToMap(int version) {
        if (versMap.containsKey(version)) {
            int cntVersion = versMap.get(version);
            versMap.put(version, cntVersion + 1);
        } else {
            versMap.put(version, 1);
        }
    }

    public void add(T product, int version) {
        Node node = createNode(product, version);
        if (isEmpty()) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        addToMap(node.getVersion());
        LOGGER.info(node.getProduct().getClass().getSimpleName() + " {} has been saved", node.getProduct().getId());
    }

    public Optional<T> findByVersion(int version) {
        if (versMap.containsKey(version)) {
            Node current = head;
            while (current != null) {
                if (current.getVersion() == version) {
                    return Optional.of(current.getProduct());
                }
                current = current.next;
            }
        }
        return Optional.empty();
    }

    public boolean removeByVersion(int version) {
        if (versMap.containsKey(version)) {

            Node current = head;

            while (current != null) {
                if (current.getVersion() == version) {
                    break;
                }
                current = current.next;
            }

            if (current == head) {
                head = head.next;
                if (head == null) {
                    tail = null;
                    versMap.remove(version);
                    return true;
                }
            } else {
                current.prev.next = current.next;
            }

            if (current == tail) {
                tail = tail.prev;
            } else {
                current.next.prev = current.prev;
            }

            int cntVersion = versMap.get(version);

            if (cntVersion != 1) {
                versMap.put(version, cntVersion - 1);
            } else {
                versMap.remove(version);
            }

            LOGGER.info(current.getProduct().getClass().getSimpleName() + " {} has been removed", current.getProduct().getId());
            return true;
        }
        return false;
    }

    public boolean updateProductByVersion(T product, int version) {
        if (versMap.containsKey(version)) {
            Node current = head;
            while (current != null) {
                if (current.getVersion() == version) {
                    current.setProduct(product);
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public int getVersionsAmount() {
        return versMap.size();
    }

    public Optional<Date> getDateOfFirstVersion() {
        if (tail != null) {
            return Optional.of(tail.getDate());
        }
        return Optional.empty();
    }

    public Optional<Date> getDateOfLastVersion() {
        if (head != null) {
            return Optional.of(head.getDate());
        }
        return Optional.empty();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<T> {
        private Node node;

        public CustomIterator() {
            node = head;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                Node temp = node;
                node = node.next;
                return temp.getProduct();
            }
            throw new NoSuchElementException();
        }
    }


}