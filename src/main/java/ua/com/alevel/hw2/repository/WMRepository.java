package ua.com.alevel.hw2.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.model.product.WashingMachine;

import java.util.*;

@Singleton
public class WMRepository implements CrudRepository<WashingMachine> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WMRepository.class);
    private final List<WashingMachine> machines;

    private static WMRepository instance;

    @Autowired
    private WMRepository() {
        machines = new LinkedList<>();
    }

    public static WMRepository getInstance() {
        if (instance == null) {
            instance = new WMRepository();
        }
        return instance;
    }

    @Override
    public void save(WashingMachine machine) {
        if (machine == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null machine");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            checkDuplicates(machine);
            machines.add(machine);
            LOGGER.info(machine.getClass().getSimpleName() + " {} has been saved", machine.getId());
        }
    }

    @Override
    public void saveAll(List<WashingMachine> machines) {
        for (WashingMachine machine : machines) {
            save(machine);
        }
    }

    @Override
    public boolean update(WashingMachine machine) {
        final Optional<WashingMachine> result = findById(machine.getId());
        if (result.isEmpty()) {
            return false;
        }
        final WashingMachine originMachine = result.get();
        WMCopy.copy(originMachine, machine);
        return true;
    }

    @Override
    public boolean delete(String id) {
        return machines.removeIf(machine -> machine.getId().equals(id));
    }

    @Override
    public List<WashingMachine> getAll() {
        if (machines.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(machines);
    }

    @Override
    public Optional<WashingMachine> findById(String id) {
        return machines.stream()
                .filter(machine -> machine.getId().equals(id))
                .findAny();
    }

    private static class WMCopy {
        private static void copy(final WashingMachine to, final WashingMachine from) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
        }
    }

    private void checkDuplicates(WashingMachine machine) {
        for (WashingMachine w : machines) {
            if (machine.hashCode() == w.hashCode() && machine.equals(w)) {
                final IllegalArgumentException exception = new IllegalArgumentException("Duplicate machine: " +
                        machine.getId());
                LOGGER.error(exception.getMessage(), exception);
                throw exception;
            }
        }
    }
}
