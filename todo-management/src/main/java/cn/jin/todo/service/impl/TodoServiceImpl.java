package cn.jin.todo.service.impl;

import cn.jin.todo.dto.TodoDto;
import cn.jin.todo.entity.Todo;
import cn.jin.todo.exception.ResourceNotFoundException;
import cn.jin.todo.repository.TodoRepository;
import cn.jin.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepository;

	private final ModelMapper modelMapper;

	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		// convert TodoDto into Todo Jpa entity
		// Todo todo = new Todo();
		// todo.setTitle(todoDto.getTitle());
		// todo.setDescription(todoDto.getDescription());
		// todo.setCompleted(todoDto.isCompleted());

		Todo todo = modelMapper.map(todoDto, Todo.class);

		// save Todo Jpa entity
		Todo savedTodo = todoRepository.save(todo);

		// convert Jpa entity into TodoDto
		TodoDto savedTodoDto = new TodoDto();
		// savedTodoDto.setId(savedTodo.getId());
		// savedTodoDto.setTitle(savedTodo.getTitle());
		// savedTodoDto.setDescription(savedTodo.getDescription());
		// savedTodoDto.setCompleted(savedTodo.isCompleted());

		return modelMapper.map(savedTodo, TodoDto.class);
	}

	@Override
	public TodoDto getTodo(Long id) {
		Todo todo = todoRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public List<TodoDto> getAllTodos() {
		List<Todo> todos = todoRepository.findAll();
		return todos.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).toList();
	}

	@Override
	public TodoDto updateTodo(TodoDto todoDto, Long id) {
		Todo todo = todoRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setCompleted(todoDto.isCompleted());

		Todo updatedTodo = todoRepository.save(todo);

		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	@Override
	public void deleteTodo(Long id) {
		todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
		todoRepository.deleteById(id);
	}

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(true);
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto incompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(false);
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

}
