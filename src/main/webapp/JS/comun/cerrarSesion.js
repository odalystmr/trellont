import { PAGES_URLS } from "../conexion_servidor/constants.js";
import { navigateTo } from "../utils.js";

const formCerrarSesion = document.getElementById("formCerrarSesion");
function borrarCookie(event) {
  event.preventDefault();

  document.cookie = 'token=; Path=/Proyecto2DAW; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  navigateTo(PAGES_URLS.inicio());
}

formCerrarSesion.addEventListener("submit", borrarCookie);
