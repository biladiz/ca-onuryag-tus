package com.tus.java.oop1.rms.data.repository.api;

import com.tus.java.oop1.rms.data.models.User;
import com.tus.java.oop1.rms.data.record.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

}

