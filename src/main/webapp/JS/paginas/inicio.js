import { login, signin } from "../conexion_servidor/authApi.js";
import { PAGES_URLS } from "../conexion_servidor/constants.js";
import { navigateTo, getCookie } from "../utils.js";

//Si ya tienes sesion iniciada que haces aqui tira a la pag de proyectos
// hola angel que seguro que estas comprobando que he hecho esto :)
const token = getCookie("token");
if (token) {
  navigateTo(PAGES_URLS.proyectos());
}

const formLogin = document.querySelector("form#form-login");
const formSignin = document.querySelector("form#form-signin");

const onFormLoginSubmit = (event) => {
  event.preventDefault(); //previene el comportamiento por defecto del navegador == aqui queremos que no haga cosas el submit

  const data = new FormData(event.target);

  login(data).then((res) => {
	if(res.includes('<html')) {
		//Con esto se comprueba si la respuesta contiene HTML
		// Si la respuesta contiene html es porque el servidor ha dado un error
	    navigateTo(PAGES_URLS.inicio());
	}
    //guardar galletas || res es un string ya
    document.cookie = "token=" + res + "; Path=/Proyecto2DAW;";
    //go to: proyectos (cambia la url)
    navigateTo(PAGES_URLS.proyectos());
  });
};

const onFormSigninSubmit = (event) => {
  event.preventDefault(); // previene el delfin

  const data = new FormData(event.target);

  //haces esto y si todo guay entra en el then
  signin(data);
};

formLogin.addEventListener("submit", onFormLoginSubmit);
formSignin.addEventListener("submit", onFormSigninSubmit);
