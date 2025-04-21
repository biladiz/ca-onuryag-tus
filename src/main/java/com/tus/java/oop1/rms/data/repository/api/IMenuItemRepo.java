package com.tus.java.oop1.rms.data.repository.api;

import com.tus.java.oop1.rms.data.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuItemRepo extends JpaRepository<MenuItem, Long>, CustomMenuItemRepo {
}