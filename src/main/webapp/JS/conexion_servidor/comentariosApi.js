import { API_URLS } from "./constants.js";
import { salirSinSesionIniciada, formDataToURLParameters } from "../utils.js";

export async function crearComentario(data) {
  const res = await fetch(API_URLS.comentarios() + "?" + formDataToURLParameters(data), {method: 'POST'});
  salirSinSesionIniciada(res.status);
  return await res.json();
}
