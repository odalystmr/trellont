export const BASE_URL = "http://localhost:8080/Proyecto2DAW";

export const PAGES_URLS = {
  //creo funciones para meter las urls
  //nombrefuncion: (parametros) => {loquehacelafuncion} -- sino pones llaves y es solo una linea funciona como return
  inicio: () => BASE_URL + "/inicio.html",
  proyectos: () => BASE_URL + "/proyectos.html",
  proyecto: (proyectoId) =>
    BASE_URL + "/proyecto.html?proyecto_id=" + proyectoId.toString(),
};

//lo mismo para servlets
export const API_URLS = {
  login: () => BASE_URL + "/login",
  signin: () => BASE_URL + "/signin",
  proyectos: () => BASE_URL + "/proyectos",
  proyecto: (proyectoId) => BASE_URL + "/proyectos/" + proyectoId.toString(),
  columnas: () => BASE_URL + "/columnas",
  columna: (columnaId) => BASE_URL + "/columnas/" + columnaId.toString(),
  tareas: () => BASE_URL + "/tareas",
  tarea: (tareaId) => BASE_URL + "/tareas/" + tareaId.toString(),
  comentarios: () => BASE_URL + "/comentarios",
};
