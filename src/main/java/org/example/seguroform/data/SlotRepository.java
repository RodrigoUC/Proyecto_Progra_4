package org.example.seguroform.data;

import org.example.seguroform.logic.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SlotRepository extends CrudRepository<Slot, Integer> {
}
