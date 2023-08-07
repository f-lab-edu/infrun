package com.flab.infrun.order.domain;

import com.flab.infrun.lecture.domain.Lecture;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderItems {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<Lecture> lectures = new ArrayList<>();

    private OrderItems(final List<Lecture> lectures) {
        this.lectures = lectures;
    }
    
    public static OrderItems create(final List<Lecture> lectures) {
        return new OrderItems(lectures);
    }
}
