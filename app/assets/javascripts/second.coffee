$ ->
  $.get "/tasks", (data) ->
    $.each data, (second, task) ->
      $("#tasks").append $("<li>").text task.contents