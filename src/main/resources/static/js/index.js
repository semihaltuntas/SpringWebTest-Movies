"use strict";
import {byId, setText, toon} from "./util.js";

const response = await fetch(`genres`);
const genres = await response.json();

if (response.ok) {
    const ul = byId("genres");
    const li = document.createElement("li");
    for (let i = 0; i < genres.length; i++) {
        const genre = genres[i];
        const hyperlink = document.createElement("a");
        hyperlink.href = `#`;
        hyperlink.innerText = `${genre.naam}`;
        hyperlink.onclick = function () {
            const genreNaam = `${genre.naam}`;
            setText("genreId", genreNaam);
            findAllFilmsByGenre(genre.id);
        }
        li.appendChild(hyperlink);
        if (i < genres.length - 1) {
            const space = document.createTextNode("  ");
            li.appendChild(space);
        }
    }
    ul.appendChild(li);
} else {
    toon("storing");
}

const findAllFilmsByGenre = async (id) => {
    const response = await fetch(`films?genreId=${id}`);
    if (response.ok) {
        const films = await response.json()
        const filmDiv = byId('films');
        filmDiv.innerHTML = '';
        for (let film of films) {
            // console.log(film)
            const aTag = document.createElement('a');
            aTag.href = 'filmdetail.html?id=' + film.id;
            const imgFoto = document.createElement('img');
            imgFoto.alt = film.titel;
            imgFoto.src = `./images/${film.id}.jpg`;
            imgFoto.onclick = () => {
                sessionStorage.setItem("filmStorage", JSON.stringify(film))
            }
            aTag.appendChild(imgFoto)
            filmDiv.appendChild(aTag);
        }
    } else {
        toon("storing")
    }
}
