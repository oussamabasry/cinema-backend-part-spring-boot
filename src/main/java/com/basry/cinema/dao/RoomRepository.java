package com.basry.cinema.dao;

import com.basry.cinema.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoomRepository extends JpaRepository<Room, Long> {
}
