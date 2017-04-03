package controllers;

import jpa.Task;
import jpa.UserForm;
import models.User;
import models.TaskForm;
import services.TaskPersistenceService;
import views.html.index;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import views.html.userContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class Application extends Controller {

    @Inject
    private TaskPersistenceService taskPersist;
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public Result index() {
    	log.debug("index() endpoint requested.");
        return ok(index.render("Welcome!", play.data.Form.form(jpa.UserForm.class))); //new task form called UserForm insteand of TaskForm
    }

    /*public Result addTask() {
    	
        Form<TaskForm> form = Form.form(TaskForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render("Hogwarts", form));
        }

        Task task = new Task();
        task.setContents(form.get().getContents());
        taskPersist.saveTask(task);
        return redirect(routes.Application.index());
    }

    public Result getTasks() {
        List<Task> tasks = taskPersist.fetchAllTasks();
        return ok(play.libs.Json.toJson(tasks));
    }*/
    
    public Result userContent() {
    	log.debug("userContent() endpoint requested");
    	return ok(userContent.render("Hello, World: User Login", play.data.Form.form(jpa.UserForm.class)));
    }
}
