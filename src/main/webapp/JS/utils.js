import {PAGES_URLS} from './conexion_servidor/constants.js';

//La mayoria de cosas (todas quiza) de este archivo salen de internet

export const formDataToURLParameters = (data) =>
  new URLSearchParams(data).toString();

export const navigateTo = (url) => (window.location.href = url);

export const getCookie = (name) => {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(";").shift();
};

export const salirSinSesionIniciada = (status) => {
	if (status === 401) {
		navigateTo(PAGES_URLS.inicio());
	}
};
