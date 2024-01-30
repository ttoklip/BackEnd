package com.api.ttoklip.domain.common.comment;

import com.api.ttoklip.domain.common.base.BaseTimeEntity;
import com.api.ttoklip.domain.common.comment.editor.CommentEditor;
import com.api.ttoklip.domain.question.post.domain.Question;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    public CommentEditor.CommentEditorBuilder toEditor(){
        return CommentEditor.builder()
                .comment(content);
    }

    public void edit(final CommentEditor commentEditor) {
        this.content = commentEditor.getContent();
    }
}
