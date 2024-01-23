package com.api.ttoklip.domain.town.cart.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CartCreateRequest {

    @NotEmpty
    @Size(max = 50)
    public String title;

    @NotEmpty
    @Size(max = 500)
    public String content;

    public List<MultipartFile> images;

    private LocalDateTime deadline;

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
