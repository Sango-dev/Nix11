package ua.com.alevel.hw2.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.model.Mouse;

import java.util.*;

public class MouseRepository implements CrudRepository<Mouse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MouseRepository.class);
    private final List<Mouse> mice;

    private static MouseRepository instance;

    private MouseRepository() {
        mice= new LinkedList<>();
    }

    public static MouseRepository getInstance() {
        if (instance == null) {
            instance = new MouseRepository();
        }
        return instance;
    }

    @Override
    public void save(Mouse mouse) {
        if (mouse == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null mouse");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            checkDuplicates(mouse);
            mice.add(mouse);
            LOGGER.info(mouse.getClass().getSimpleName() + " {} has been saved", mouse.getId());
        }
    }

    @Override
    public void saveAll(List<Mouse> mice) {
        for (Mouse mouse : mice) {
            save(mouse);
        }
    }

    @Override
    public boolean update(Mouse mouse) {
        final Optional<Mouse> result = findById(mouse.getId());
        if (result.isEmpty()) {
            return false;
        }
        final Mouse originMouse = result.get();
        MouseRepository.MouseCopy.copy(originMouse, mouse);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Mouse> iterator = mice.iterator();
        while (iterator.hasNext()) {
            final Mouse mouse = iterator.next();
            if (mouse.getId().equals(id)) {
                LOGGER.info(mouse.getClass().getSimpleName() + " {} has been removed", mouse.getId());
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Mouse> getAll() {
        if (mice.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(mice);
    }

    @Override
    public Optional<Mouse> findById(String id) {
        Mouse result = null;
        for (Mouse mouse : mice) {
            if (mouse.getId().equals(id)) {
                result = mouse;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class MouseCopy {
        private static void copy(final Mouse to, final Mouse from) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
        }
    }

    private void checkDuplicates(Mouse mouse) {
        for (Mouse m : mice) {
            if (mouse.hashCode() == m.hashCode() && mouse.equals(m)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate mouse: " +
                        mouse.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }
}
