"use strict";

import {byId, setText, toon, verberg} from "./util.js";

async function verbergFilmEnFouten() {
    verberg("film");
    verberg("storing");
    verberg("nietGevonden");
}
verbergFilmEnFouten();

const filmDatas = JSON.parse(sessionStorage.getItem("films"));
await Promise.all(filmDatas.map(async (filmData) => {
    const idVanFilm = filmData.id;
   // console.log(idVanFilm);
    const response = await fetch(`films/${idVanFilm}`);
    if (response.ok) {
        const film = await response.json();
        toon("film");
        const h1Element = byId("filmTitel");
        h1Element.textContent = film.titel;
        const imgElement = byId("filmImage");
        imgElement.src = `./images/${film.id}.jpg`;
        imgElement.alt = film.titel;
        setText("prijs", film.prijs);
        setText("voorraad", film.voorraad);
        setText("gereserveerd", film.gereserveerd);
        setText("beschikbaar", film.beschikbaar);
    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}));




// "use strict";
//
// import { byId, setText, toon } from "./util.js";
//
// const filmListesi = byId('film');
//
// async function clickOpFoto(filmId) {
//     const response = await fetch(`films/${filmId}`);
//     if (!response.ok) {
//         toon("storing")
//     }
//     const film = await response.json();
//     updateFilmDatas(film);
// }
//
// function updateFilmDatas(film) {
//     const filmTitel = document.getElementById('filmTitel');
//     filmTitel.textContent = film.titel;
//
//     const filmImage = byId('filmImage');
//     filmImage.src = `./images/${film.id}.jpg`;
//     filmImage.alt = film.titel;
//
//     const filmBilgilerDiv = byId('filmDatas');
//     filmBilgilerDiv.innerHTML = '';
//
//     setText("prijs", film.prijs);
//     setText("voorraad", film.voorraad);
//     setText("gereserveerd", film.gereserveerd);
//     setText("beschikbaar", film.beschikbaar);
// }
//
// const filmDatas = JSON.parse(sessionStorage.getItem("films"));
//
// filmDatas.forEach(filmData => {
//     const filmLi = document.createElement("li");
//     console.log(filmData.id);
//     filmLi.textContent = filmData.titel;
//     filmLi.id = filmData.id;
//     filmListesi.appendChild(filmLi);
//     console.log(filmLi)
//
//     filmLi.addEventListener('click', () => clickOpFoto(filmData.id));
// });


// const filmDatas = JSON.parse(sessionStorage.getItem("films"));
// for (const filmData of filmDatas) {
//     setText("filmId", filmData.titel)
//     const response = await fetch(`films/${filmData.id}`);
//     if (response.ok) {
//         const film = await response.json();
//         console.log(film)
//         const filmDiv = document.getElementById('films');
//         filmDiv.innerHTML = '';
//         const imgFoto = document.createElement('img');
//         imgFoto.alt = film.titel;
//         console.log(film.id)
//         imgFoto.src = `./images/${film.id}.jpg`;
//         filmDiv.appendChild(imgFoto);
//     }


// console.log(filmDatas)
// filmDatas.forEach(filmData => {
//     console.log(filmData.id)
//     const filmLi = document.querySelector(`#films a[href="filmdetail.html?id=${filmData.id}"] img`);
//     console.log(filmLi)
//     if (filmLi) {
//         filmLi.addEventListener('click', () => fotoClick(filmData.id));
//     }
// });
//
// async function fotoClick(filmId) {
//     const response = await fetch(`films/${filmId}`);
//     if (!response.ok) {
//     toon("storing")
//         return;
//     }
//     const film = await response.json();
//     console.log(film)
//
//     const filmIdSpan = byId('filmId');
//     filmIdSpan.textContent = film.titel;
//
//     const imgFoto = document.createElement('img');
//     imgFoto.alt = film.titel;
//     imgFoto.src = `./images/${film.id}.jpg`;
//
//     const filmDiv = byId('films');
//     filmDiv.innerHTML = '';
//     filmDiv.appendChild(imgFoto);
// }




















