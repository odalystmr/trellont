import { API_URLS, PAGES_URLS } from "./constants.js";
import {
  formDataToURLParameters,
  navigateTo,
  salirSinSesionIniciada,
} from "../utils.js";

export async function getProyectos() {
  const res = await fetch(API_URLS.proyectos());
  salirSinSesionIniciada(res.status);
  return await res.json();
}

export async function getProyecto(proyecto) {
  const res = await fetch(API_URLS.proyecto(proyecto));
  salirSinSesionIniciada(res.status);

  return await res.json();
}

export async function deleteProyecto(proyecto) {
  const res = await fetch(API_URLS.proyecto(proyecto), { method: "DELETE" });
  salirSinSesionIniciada(res.status);

  return await res.text();
}

export const crearProyectos = async (data) => {
  const res = await fetch(
    API_URLS.proyectos() + "?" + formDataToURLParameters(data),
    { method: "POST" }
  );
  salirSinSesionIniciada(res.status);

  return await res.text();
};

export const editarProyecto = async (proyectoId, data) => {
  const res = await fetch(
    API_URLS.proyecto(proyectoId) + "?" + formDataToURLParameters(data),
    { method: "PUT" }
  );
  salirSinSesionIniciada(res.status);
  return await res.json();
};
