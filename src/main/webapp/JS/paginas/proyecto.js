import { getCookie, navigateTo } from "../utils.js";
import {
  getProyecto,
  editarProyecto,
} from "../conexion_servidor/proyectosApi.js";

import {PAGES_URLS} from '../conexion_servidor/constants.js';

import { getTarea, crearTarea, editarTarea, deleteTarea } from "../conexion_servidor/tareasApi.js";

import { crearComentario } from "../conexion_servidor/comentariosApi.js";
import { getComentarioHTML } from '../componentes/comentario.js';

import { crearColumna } from "../conexion_servidor/columnasApi.js";

import { getColumnaHTML } from "../componentes/columnas.js";

const token = getCookie("token");
if (!token) {
  navigateTo(PAGES_URLS.inicio());
}

const proyectoId = new URLSearchParams(window.location.search).get(
  "proyecto_id"
);

const elementoTituloProyecto = document.getElementById("titulo-proyecto");
const contenedorColumnas = document.getElementById("contenedor-columnas");

const ondragenterColumna = (event) => {
  event.preventDefault();

  if (!event.target.classList.contains("columna")) {
    return;
  }

  event.target.classList.remove("bg-light");
  event.target.classList.add("bg-secondary");
};

const ondragoverColumna = (event) => event.preventDefault();

const ondragleaveColumna = (event) => {
  event.preventDefault();

  if (!event.target.classList.contains("columna")) {
    return;
  }

  event.target.classList.add("bg-light");
  event.target.classList.remove("bg-secondary");
};

const ondropTarea = (event) => {
  event.preventDefault();

  if (!event.target.classList.contains("columna")) {
    return;
  }

  const tareaDiv = document.getElementById(
    event.dataTransfer.getData("id-tarea")
  );

  event.target.appendChild(tareaDiv);
  event.target.classList.add("bg-light");
  event.target.classList.remove("bg-secondary");
  tareaDiv.classList.remove("opacity-50");

  const tarea = JSON.parse(tareaDiv.getAttribute("data-tarea"));
  const columnaId = event.target.getAttribute("data-columna-id");

  const data = new FormData();

  data.append("id", tarea.id);
  data.append("columna", columnaId);

  editarTarea(tarea.id, data).then((res) =>
    getProyecto(proyectoId).then(pintaProyecto)
  );
};

function ondragstartTarea(event) {
  event.dataTransfer.setData("id-tarea", event.target.id);

  event.target.classList.add("opacity-50");
}

const pintarTareaEnModal = (tarea) => {
  document.getElementById("modal-tarea-titulo").innerText = tarea.titulo;
  document.getElementById("modal-tarea-description").innerText =
    tarea.descripcion;
  document.getElementById("modal-tarea-creador").innerText =
    tarea.creador.nombre;
  document.getElementById("modal-tarea-responsable").innerText =
    tarea.responsable.nombre;
    document.getElementById("input-crear-comentario-tarea-id").value = tarea.id;

	const comentariosContainer = document.getElementById("comentarios-container");

	comentariosContainer.innerHTML = '';

	for (const comentario of tarea.comentarios) {
		comentariosContainer.append(getComentarioHTML(comentario));
	}
};

const pintaProyecto = (proyecto) => {
  elementoTituloProyecto.innerText = proyecto.nombre;
  contenedorColumnas.innerHTML = "";

  for (const columna of proyecto.columnas) {
    const columnaElement = getColumnaHTML(
      columna.id,
      columna.nombre,
      columna.tareas
    );

    contenedorColumnas.append(columnaElement);

    columnaElement.addEventListener("dragenter", ondragenterColumna);
    columnaElement.addEventListener("dragover", ondragenterColumna);
    columnaElement.addEventListener("dragleave", ondragleaveColumna);
    columnaElement.addEventListener("drop", ondropTarea);

    columnaElement
      .querySelectorAll("div.tarea")
      ?.forEach((tarea) =>
        tarea.addEventListener("dragstart", ondragstartTarea)
      );

    columnaElement
      .querySelector('button[data-bs-target="#modal-crear-tarea"]')
      ?.addEventListener("click", (event) => {
        const inputColumnaId = document.getElementById("input-columna-id");
        inputColumnaId.setAttribute(
          "value",
          event.target.getAttribute("data-columna-id")
        );
      });

    const selectResponsable = document.getElementById(
      "lista-select-responsable"
    );

    selectResponsable.innerHTML =
      "<option selected value='" +
      proyecto.propietario.id +
      "'>" +
      proyecto.propietario.nombre +
      "</option>";

    for (const participante of proyecto.participantes) {
      const option = document.createElement("option");
      option.value = participante.id;
      option.text = participante.nombre;
      selectResponsable.append(option);
    }
  }

  const btnsBorrarTarea = document.querySelectorAll(".btn-borrar-tarea");
  btnsBorrarTarea.forEach((btn) =>
    btn.addEventListener("click", function (event) {
      const tareaId = event.target.getAttribute("data-tarea-id");
      deleteTarea(tareaId).then((res) => window.location.reload());
    })
  );

  const btnsAbrirModalTarea = document.querySelectorAll(".btn-abrir-modal");

  btnsAbrirModalTarea.forEach((btn) => { 
  		btn.addEventListener("click", function (event) {
  			const tareaId = event.target.getAttribute("data-tarea-id");
			getTarea(tareaId).then(pintarTareaEnModal);
		});
	});
};

getProyecto(proyectoId).then(pintaProyecto);

const formCrearColumna = document.getElementById("form-crear-columna");

const onsumbitFormCrearColumna = async (event) => {
  event.preventDefault();

  const data = new FormData(event.target);
  data.append("proyectoColumna", proyectoId);

  await crearColumna(data);

  getProyecto(proyectoId).then(pintaProyecto);
};

formCrearColumna.addEventListener("submit", onsumbitFormCrearColumna);

const formCrearTarea = document.getElementById("form-crear-tarea");

const onsumbitFormCrearTarea = async (event) => {
  event.preventDefault();

  const data = new FormData(event.target);
  data.append("proyecto_id", proyectoId);

  await crearTarea(data);

  getProyecto(proyectoId).then(pintaProyecto);
};

formCrearTarea.addEventListener("submit", onsumbitFormCrearTarea);

const formInvitarParticipante = document.getElementById(
  "form-invitar-participante"
);

const onformsubmitInvitarParticipante = (event) => {
  event.preventDefault();

  const data = new FormData(event.target);

  editarProyecto(proyectoId, data).then((proyecto) =>
    getProyecto(proyectoId).then(pintaProyecto)
  );
};

formInvitarParticipante.addEventListener(
  "submit",
  onformsubmitInvitarParticipante
);

const formCrearComentario = document.getElementById("form-crear-comentario");

const onsubmitFormCrearComentario = (event) => {
  event.preventDefault();
  const data = new FormData(event.target);
  crearComentario(data).then(pintarTareaEnModal);
};

formCrearComentario.addEventListener("submit", onsubmitFormCrearComentario);
