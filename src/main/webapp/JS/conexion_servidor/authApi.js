import { API_URLS } from "./constants.js";
import { formDataToURLParameters } from "../utils.js";

export async function login(data) {
  const opciones = {
    method: "POST",
  };

  const res = await fetch(
    API_URLS.login() + "?" + formDataToURLParameters(data),
    opciones
  );

  return await res.text();
}

export async function signin(data) {
  const opciones = {
    method: "POST",
  };

  const res = await fetch(
    API_URLS.signin() + "?" + formDataToURLParameters(data),
    opciones
  );

  return await res.text();
}
