"use strict";

import {byId, setText, toon, verberg} from "./util.js";

const mandjeDatas= JSON.parse(sessionStorage.getItem("mandjeStorage"))
const mandjeBody = byId("mandjeBody")

let totaalPrijs = 0;
mandjeDatas.forEach(film => {
    const tr = mandjeBody.insertRow();
    tr.insertCell().textContent = film.titel;
    tr.insertCell().textContent = film.prijs;
    totaalPrijs += film.prijs;
});
const tr = mandjeBody.insertRow();
tr.insertCell().innerText = "Totaal:";
tr.insertCell().innerText = totaalPrijs;