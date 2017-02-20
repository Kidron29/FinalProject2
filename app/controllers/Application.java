package controllers;

import jpa.Task;
import models.TaskForm;

import services.TaskPersistenceService;

import views.html.index;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Application extends Controller {

    @Inject
    private TaskPersistenceService taskPersist;

    public Result index() {
        return ok(index.render("hello, world", Form.form(TaskForm.class)));
    }

    public Result addTask() {
        Form<TaskForm> form = Form.form(TaskForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render("hello, world", form));
        }

        Task task = new Task();
        task.setContents(form.get().getContents());
        taskPersist.saveTask(task);
        return redirect(routes.Application.index());
    }

    public Result getTasks() {
        List<Task> tasks = taskPersist.fetchAllTasks();
        return ok(play.libs.Json.toJson(tasks));
    }
}
