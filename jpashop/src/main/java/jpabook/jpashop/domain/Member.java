package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    // Embedded나 Embedable 둘 중 하나만 써도 되는데 가독성을 위해 둘 다 쓰기도 함
    @Embedded
    private Address address;

    // api에 노출 안 시킨다
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
