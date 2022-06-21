export const getComentarioHTML = (comentario) => {
	const comentarioDiv = document.createElement('div');
	comentarioDiv.classList.add('card');

	const usuarioDiv = document.createElement('div');
	usuarioDiv.classList.add('text-muted', 'text-sm');
	usuarioDiv.innerText = comentario.usuario.nombre + ' comentó:';
	comentarioDiv.append(usuarioDiv);

	const p = document.createElement('p');
	p.classList.add('px-5');
	p.innerText = comentario.comentario;
	comentarioDiv.append(p);

	return comentarioDiv
};