import { getCookie, navigateTo } from "../utils.js";
import { PAGES_URLS } from "../conexion_servidor/constants.js";
import {
  deleteProyecto,
  getProyectos,
  crearProyectos,
} from "../conexion_servidor/proyectosApi.js";

const token = getCookie("token");
if (!token) {
  navigateTo(PAGES_URLS.inicio());
}

const pintarListaProyectos = (proyectos) => {
  const tabla = document.getElementById("lista-proyectos");

  //esto se recorre el array
  for (const proyecto of proyectos) {
    //meto en la tabla los datos
    const tr = document.createElement("tr");

    const tdEditar = document.createElement("td");
    const tdBorrar = document.createElement("td");

    const buttonCollapse = document.createElement("button");
    buttonCollapse.setAttribute("data-bs-toggle", "collapse");
    buttonCollapse.setAttribute("data-bs-target", "#collapse-" + proyecto.id);
    buttonCollapse.type = "button";
    buttonCollapse.classList.add("btn", "btn-primary");

    const e = document.createElement("i");
    e.innerText = "";
    e.classList.add("bi", "bi-plus-circle-fill");
    buttonCollapse.appendChild(e);

    const tdNombre = document.createElement("td");
    tdNombre.innerText = " " + proyecto.nombre;

    const tdDescripcion = document.createElement("td");
    tdDescripcion.classList.add("collapse");
    tdDescripcion.id = "collapse-" + proyecto.id;
    tdDescripcion.innerText =
      proyecto.descripcion?.trim() || "No hay descripcion";
    tdDescripcion.setAttribute("colspan", "3");

    const btnEditarProyecto = document.createElement("a");
    btnEditarProyecto.href = PAGES_URLS.proyecto(proyecto.id);
    btnEditarProyecto.classList.add("btn", "btn-primary");
    btnEditarProyecto.innerText = "Entrar";

    const btnBorrarProyecto = document.createElement("button");
    btnBorrarProyecto.id = "delete-" + proyecto.id;
    btnBorrarProyecto.type = "button";
    btnBorrarProyecto.classList.add("btn", "btn-danger");

    const i = document.createElement("i");
    i.innerText = "";
    i.classList.add("bi", "bi-trash-fill");
    btnBorrarProyecto.appendChild(i);

    const tabla = document.getElementById("lista-proyectos");

    tdBorrar.appendChild(btnBorrarProyecto);
    tdEditar.appendChild(btnEditarProyecto);
    const trCol = document.createElement("tr");

    trCol.appendChild(tdDescripcion);
    tdNombre.prepend(buttonCollapse);

    tr.appendChild(tdNombre);
    tr.appendChild(tdEditar);
    tr.appendChild(tdBorrar);
    tabla.appendChild(tr);
    tabla.appendChild(trCol);

    btnBorrarProyecto.addEventListener("click", function (event) {
      deleteProyecto(proyecto.id).then((res) => window.location.reload());
    });
  }
};

// Si llegamos aqui es porque tenemos sesion iniciada
//llamar funcion proyectos y guardo el resultado (array) en una variable
getProyectos().then(pintarListaProyectos);

const formCrearProyecto = document.getElementById("form-crear-proyectos");

const onsubmitFormCrearProyecto = (event) => {
  event.preventDefault();
  const data = new FormData(event.target);
  crearProyectos(data).then((res) => window.location.reload());
};

formCrearProyecto.addEventListener("submit", onsubmitFormCrearProyecto);
