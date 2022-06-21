import { API_URLS } from "./constants.js";
import { formDataToURLParameters } from "../utils.js";
import { salirSinSesionIniciada } from "../utils.js";

export const crearTarea = async (data) => {
  const res = await fetch(
    API_URLS.tareas() + "?" + formDataToURLParameters(data),
    { method: "POST" }
  );
  salirSinSesionIniciada(res.status);
  return await res.text();
};

export async function deleteTarea(tarea) {
  const res = await fetch(API_URLS.tarea(tarea), { method: "DELETE" });
  salirSinSesionIniciada(res.status);
  return await res.text();
}

export async function editarTarea(tarea, data) {
  const res = await fetch(
    API_URLS.tarea(tarea) + "?" + formDataToURLParameters(data),
    { method: "PUT" }
  );
  salirSinSesionIniciada(res.status);
  return await res.text();
}

export async function getTarea(tarea) {
  const res = await fetch(API_URLS.tarea(tarea), { method: "GET" });
  salirSinSesionIniciada(res.status);
  return await res.json();
}
