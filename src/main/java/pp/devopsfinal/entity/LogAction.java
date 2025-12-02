package pp.devopsfinal.entity;

import jakarta.persistence.*;
import lombok.*;
import pp.devopsfinal.enums.Action;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log_actions")
public class LogAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "action")
    @Enumerated(value = EnumType.STRING)
    private Action action;

    @Column(name = "created_at")
    private Instant createdAt;
}
