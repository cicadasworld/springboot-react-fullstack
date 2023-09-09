package cn.jin.todo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

	private Long id;

	private String title;

	private String description;

	private boolean completed;

}
