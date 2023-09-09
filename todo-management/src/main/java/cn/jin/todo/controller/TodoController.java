package cn.jin.todo.controller;

import cn.jin.todo.dto.TodoDto;
import cn.jin.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {

	private final TodoService todoService;

	// Build Add Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {
		TodoDto savedTodo = todoService.addTodo(todoDto);
		return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
	}

	// Build Get Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping("{id}")
	public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId) {
		TodoDto todoDto = todoService.getTodo(todoId);
		return new ResponseEntity<>(todoDto, HttpStatus.OK);
	}

	// Build Get All Todos REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping
	public ResponseEntity<List<TodoDto>> getAllTodos() {
		List<TodoDto> todos = todoService.getAllTodos();
		// return new ResponseEntity<>(todos, HttpStatus.OK);
		return ResponseEntity.ok(todos);
	}

	// Build Update Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId) {
		TodoDto updatedTodo = todoService.updateTodo(todoDto, todoId);
		return ResponseEntity.ok(updatedTodo);
	}

	// Build Delete Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId) {
		todoService.deleteTodo(todoId);
		return ResponseEntity.ok("Todo deleted successfully!");
	}

	// Build Complete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PatchMapping("{id}/complete")
	public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId) {
		TodoDto todoDto = todoService.completeTodo(todoId);
		return ResponseEntity.ok(todoDto);
	}

	// Build In-complete Todo REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PatchMapping("{id}/in-complete")
	public ResponseEntity<TodoDto> incompleteTodo(@PathVariable("id") Long todoId) {
		TodoDto todoDto = todoService.incompleteTodo(todoId);
		return ResponseEntity.ok(todoDto);
	}

}