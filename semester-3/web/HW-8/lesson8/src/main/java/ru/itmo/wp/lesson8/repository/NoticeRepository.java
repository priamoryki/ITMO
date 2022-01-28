package ru.itmo.wp.lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.wp.lesson8.domain.Notice;

/**
 * @author Pavel Lymar
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> { }
