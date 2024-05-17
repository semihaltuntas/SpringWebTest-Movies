"use strict";

import {byId, setText, toon, verberg} from "./util.js";

async function verbergFilmEnFouten() {
    verberg("film");
    verberg("storing");
    verberg("nietGevonden");
}
verbergFilmEnFouten();

const queryString = window.location.search;
console.log(queryString);
const urlParams = new URLSearchParams(queryString);
console.log(urlParams)
const idVanFilm = urlParams.get('id')
console.log(idVanFilm)


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

        const buttonMandje = byId("mandje");
        if (film.beschikbaar === 0){
            buttonMandje.disabled = true;
        }
        buttonMandje.onclick  = function (){
            voegFilmMandjeToe(film)
        }
    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
}
const voegFilmMandjeToe = (film) => {
    let mandjeDataFromStorage = JSON.parse(sessionStorage.getItem("mandjeStorage")) || [];
    if (!mandjeDataFromStorage.some(obj => obj.id === film.id)) {
        mandjeDataFromStorage.push(film);
        sessionStorage.setItem("mandjeStorage", JSON.stringify(mandjeDataFromStorage));
    }else{
        alert("Deze film al bestaat in Jouw Mandje!");
    }
    window.location.href = "mandje.html";
};





















