package Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	List<ChatMessage> findByOrderByTimestampAsc();
}
