"use strict";
import {byId, setText, toon} from "./util.js";

const filmDatas = JSON.parse(sessionStorage.getItem("films"));














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
//         imgFoto.src = `./images/${film.id}.jpg`;
//         filmDiv.appendChild(imgFoto);
//     }
// }




