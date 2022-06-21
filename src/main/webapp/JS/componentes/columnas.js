import { deleteColumna } from "../conexion_servidor/columnasApi.js";
import { getTareaHTML } from "./tarea.js";

export const getColumnaHTML = (columnaId, nombre, tareas) => {
  const divColumna = document.createElement("div");
  divColumna.setAttribute("data-columna-id", columnaId);
  divColumna.classList.add("col", "bg-light", "border", "m-1", "columna");
  divColumna.id = "columna-" + columnaId;

  const divPadre = document.createElement("div");
  const divTituloColumna = document.createElement("div");
  divTituloColumna.classList.add("row");

  const colVacia = document.createElement("p");
  colVacia.classList.add("col-4");

  const pNombre = document.createElement("h3");
  pNombre.classList.add("col-6");
  pNombre.innerText = nombre;

  const btnBorrarColumna = document.createElement("button");
  btnBorrarColumna.id = "delete-" + columnaId;
  btnBorrarColumna.type = "button";
  btnBorrarColumna.classList.add("btn", "btn-danger", "col-2");

  const btnCrearTarea = document.createElement("button");
  btnCrearTarea.type = "button";
  btnCrearTarea.classList.add("btn", "btn-primary", "w-100");
  btnCrearTarea.setAttribute("data-bs-toggle", "modal");
  btnCrearTarea.setAttribute("data-bs-target", "#modal-crear-tarea");
  btnCrearTarea.setAttribute("data-columna-id", columnaId);
  btnCrearTarea.innerText = "Crear tarea";

  const i = document.createElement("i");
  i.innerText = "";
  i.classList.add("bi", "bi-trash-fill");

  btnBorrarColumna.appendChild(i);

  divTituloColumna.appendChild(pNombre);
  divTituloColumna.appendChild(colVacia);
  divTituloColumna.appendChild(btnBorrarColumna);
  divPadre.appendChild(divTituloColumna);
  divColumna.appendChild(divPadre);
  divColumna.appendChild(btnCrearTarea);

  for (const tarea of tareas) {
    const tareaDiv = getTareaHTML(tarea);
    divColumna.appendChild(tareaDiv);
  }

  btnBorrarColumna.addEventListener("click", function (event) {
    deleteColumna(columnaId).then((res) => window.location.reload());
  });

  return divColumna;
};
