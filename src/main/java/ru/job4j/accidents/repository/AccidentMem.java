package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class AccidentMem implements AccidentRepository {
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AccidentMem() {
        save(new Accident(0, "Accident 1", "Description 1", "Address 1"));
        save(new Accident(0, "Accident 2", "Description 2", "Address 2"));
        save(new Accident(0, "Accident 3", "Description 3", "Address 3"));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId.getAndIncrement());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
