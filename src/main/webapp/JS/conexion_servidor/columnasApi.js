import { API_URLS } from "./constants.js";
import { formDataToURLParameters } from "../utils.js";
import { salirSinSesionIniciada } from "../utils.js";

export async function crearColumna(data) {
  const res = await fetch(
    API_URLS.columnas() + "?" + formDataToURLParameters(data),
    { method: "POST" }
  );
  salirSinSesionIniciada(res.status);
  return await res.text();
}

export async function deleteColumna(columna) {
  const res = await fetch(API_URLS.columna(columna), { method: "DELETE" });
  salirSinSesionIniciada(res.status);
  return await res.text();
}
