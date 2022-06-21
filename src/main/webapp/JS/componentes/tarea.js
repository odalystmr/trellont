import { deleteTarea, getTarea } from "../conexion_servidor/tareasApi.js";

export const getTareaHTML = (tarea) => {
  const tareaDiv = document.createElement("div");
  tareaDiv.setAttribute("data-tarea", JSON.stringify(tarea));
  tareaDiv.setAttribute("draggable", true);
  tareaDiv.id = "tarea-" + tarea.id;
  tareaDiv.classList.add("card", "mx-auto", "my-2", "tarea");

  const btnBorrarTarea = document.createElement("button");
  btnBorrarTarea.id = "delete-" + tarea.id;
  btnBorrarTarea.type = "button";
  btnBorrarTarea.setAttribute("data-tarea-id", tarea.id);
  btnBorrarTarea.classList.add(
    "btn",
    "btn-danger",
    "ml-auto",
    "btn-borrar-tarea"
  );

  const btnAbrirModal = document.createElement("button");
  btnAbrirModal.setAttribute("data-bs-toggle", "modal");
  btnAbrirModal.setAttribute("data-bs-target", "#modal-detalle-tarea");
  btnAbrirModal.setAttribute("data-tarea-id", tarea.id);
  btnAbrirModal.type = "button";
  btnAbrirModal.classList.add("btn", "btn-info", "btn-abrir-modal");

  const e = document.createElement("i");
  e.innerText = "";
  e.classList.add("bi", "bi-arrows-angle-expand");

  btnAbrirModal.appendChild(e);

  const i = document.createElement("i");
  i.innerText = "";
  i.classList.add("bi", "bi-trash-fill");

  btnBorrarTarea.appendChild(i);

  const tareaP = document.createElement("p");
  tareaP.classList.add("card-title");
  tareaP.innerText = tarea.titulo;

  tareaDiv.appendChild(tareaP);
  tareaDiv.appendChild(btnAbrirModal);
  tareaDiv.appendChild(btnBorrarTarea);

  return tareaDiv;
};
